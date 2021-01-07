package com.daki.main.christmas.hider;

import java.util.ArrayList;
import java.util.List;

import com.daki.main.WinterHideAndSeek;
import com.daki.main.christmas.global.Jingle;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class HidersEffects implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender.hasPermission("christmas.admin")) {
            if (args.length != 0) {
                if (args[0].equals("glow")) {
                    if (args[1].equals("off")) {
                        glowOff();
                    } else
                        glow(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
                    Jingle.sounds();
                } else if (args[0].equals("chicken")) {
                    chicken(Integer.parseInt(args[1]));
                    Jingle.sounds();
                } else if (args[0].equals("fireworks")) {
                    fireworks(Integer.parseInt(args[1]));
                    Jingle.sounds();
                } else if (args[0].equals("freeze")) {
                    freeze(Integer.parseInt(args[1]));
                    Jingle.sounds();
                }
            }

            return false;
        } else {
            sender.sendMessage(ChatColor.DARK_RED + "You don't have the permission to do this.");
        }
        return false;
    }

    public void glow(Integer everyXseconds, Integer forYseconds) {

        GV.glowRunning = true;
        Bukkit.broadcastMessage(ChatColor.GOLD + ChatColor.BOLD.toString() + "Hiders have begun to glow! Every "
                + everyXseconds.toString() + " seconds a new hider will glow for " + forYseconds + " seconds!");
        List<Player> hiders = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission("christmas.hider")) {
                hiders.add(player);
            }
        }

        for (Integer i = 0; i < hiders.size(); i++) {
            Player player = (Player) hiders.get(i);
            new BukkitRunnable() {
                public void run() {
                    player.setGlowing(true);
                    new BukkitRunnable() {
                        public void run() {
                            player.setGlowing(false);
                        }
                    }.runTaskLater(WinterHideAndSeek.getInstance(), forYseconds * 20);
                }
            }.runTaskLater(WinterHideAndSeek.getInstance(), everyXseconds * 20 + i * everyXseconds * 20);
        }
    }

    public void glowOff() {

        GV.glowRunning = false;
        Bukkit.broadcast(ChatColor.RED + "Glow turned off.", "christmas.admin");

    }

    public void chicken(Integer duration) {
        Bukkit.broadcastMessage(ChatColor.GOLD + ChatColor.BOLD.toString() + "The hiders have begun clucking for "
                + duration.toString() + " seconds!");
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            if (player.hasPermission("christmas.hider")) {
                for (Integer i = 0; i < duration; i++) {
                    Bukkit.getScheduler().scheduleSyncDelayedTask(WinterHideAndSeek.getInstance(), new Runnable() {
                        public void run() {
                            Bukkit.getWorld(player.getWorld().getName()).playSound(player.getLocation(),
                                    Sound.ENTITY_CHICKEN_AMBIENT, 1.0F, 1.0F);
                        }
                    }, i * 20);

                }
            }
        }
    }

    public void fireworks(Integer amount) {

        Bukkit.broadcastMessage(ChatColor.DARK_RED + "F" + ChatColor.DARK_GREEN + "I" + ChatColor.DARK_RED + "R"
                + ChatColor.DARK_GREEN + "E" + ChatColor.DARK_RED + "W" + ChatColor.DARK_GREEN + "O"
                + ChatColor.DARK_RED + "R" + ChatColor.DARK_GREEN + "K" + ChatColor.DARK_RED + "S" + ChatColor.GOLD
                + ChatColor.BOLD.toString() + " just went off above the hiders!");
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            if (player.hasPermission("christmas.hider")) {
                for (Integer i = 0; i < amount; i++) {
                    Bukkit.getScheduler().scheduleSyncDelayedTask(WinterHideAndSeek.getInstance(), new Runnable() {
                        public void run() {
                            Firework fw = (Firework) Bukkit.getWorld(player.getWorld().getName())
                                    .spawnEntity(player.getLocation(), EntityType.FIREWORK);
                            FireworkMeta fireworkMeta = fw.getFireworkMeta();
                            FireworkEffect.Builder builder = FireworkEffect.builder();
                            builder.withColor(Color.GREEN);
                            builder.withColor(Color.RED);
                            builder.withFlicker();
                            builder.withFade(Color.GREEN);
                            builder.withFade(Color.RED);
                            builder.with(FireworkEffect.Type.STAR);
                            builder.trail(true);
                            FireworkEffect effect = builder.build();
                            fireworkMeta.addEffect(effect);
                            fireworkMeta.setPower(0);
                            fw.setFireworkMeta(fireworkMeta);

                        }
                    }, i * 2);
                }
            }
        }
    }

    public void levitate(Integer duration) {
        Bukkit.getServer().broadcastMessage(ChatColor.GOLD + ChatColor.BOLD.toString() + "levitate");
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            if (player.hasPermission("christmas.hider")) {
                PotionEffect pot = new PotionEffect(PotionEffectType.LEVITATION, duration * 20, 0);
                player.addPotionEffect(pot);
            }
        }
    }

    public void freeze(Integer duration) {
        Bukkit.broadcastMessage(ChatColor.GOLD + ChatColor.BOLD.toString() + "The hiders are now frozen in place for "
                + duration.toString() + " seconds!");
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            if (player.hasPermission("christmas.hider")) {
                PotionEffect pot = new PotionEffect(PotionEffectType.SLOW, duration * 20, 1000);
                player.addPotionEffect(pot);
            }
        }
    }

    public void nametag(String string) {
        if (string.equals("off")) {
            Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
            Team team = board.registerNewTeam("Hiders");
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.hasPermission("christmas.hider")) {
                    team.addEntry(player.getName());
                    player.setScoreboard(board);
                }
            }
            team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.NEVER);
        } else if (string.equals("on")) {
            Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
            Team team = board.registerNewTeam("Hiders");
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.hasPermission("christmas.hider")) {
                    team.addEntry(player.getName());
                    player.setScoreboard(board);
                }
            }
            team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);
        }

    }
}
