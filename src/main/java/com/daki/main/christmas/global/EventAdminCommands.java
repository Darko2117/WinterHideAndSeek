package com.daki.main.christmas.global;

import com.daki.main.EventManager.EventManager;
import com.daki.main.WinterHideAndSeek;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class EventAdminCommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (args.length < 1) return true;

        switch (args[0]) {

            case "start":

                if (EventManager.getExistingEvent().getRunning()) {
                    sender.sendMessage(ChatColor.RED + "Event is already running!");
                    return true;
                }

                EventStartEvent event = new EventStartEvent();
                Bukkit.getPluginManager().callEvent(event);
                Jingle.sounds();
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.sendTitle(ChatColor.DARK_GREEN + "C" + ChatColor.DARK_RED + "H" + ChatColor.DARK_GREEN
                                    + "R" + ChatColor.DARK_RED + "I" + ChatColor.DARK_GREEN + "S" + ChatColor.DARK_RED + "T"
                                    + ChatColor.DARK_GREEN + "M" + ChatColor.DARK_RED + "A" + ChatColor.DARK_GREEN + "S",
                            ChatColor.WHITE + " HIDE AND SEEK EVENT IS STARTING", 20, 100, 20);
                    new BukkitRunnable() {
                        public void run() {
                            player.resetTitle();
                            player.sendTitle(ChatColor.WHITE + "FIND A SPOT TO HIDE IN",
                                    "SEEKERS WILL BE RELEASED IN 5 MINUTES", 20, 100, 20);
                        }
                    }.runTaskLater(WinterHideAndSeek.getInstance(), 120);
                }
                Bukkit.broadcast(ChatColor.GREEN + "Event started!", "christmas.admin");
                GV.EventRunning = true;

                break;

            case "end":

                if (GV.EventRunning) {
                    EventEndEvent event = new EventEndEvent();
                    Bukkit.getPluginManager().callEvent(event);
                    Jingle.sounds();
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.sendTitle(ChatColor.WHITE + "This round is now over!", "", 20, 100, 20);
                    }
                    Bukkit.broadcast(ChatColor.RED + "Event shut down!", "christmas.admin");
                    GV.EventRunning = false;
                } else {
                    sender.sendMessage(ChatColor.RED + "The event is not running.");
                }
                break;
            case "reload":
                if (GV.EventRunning) {
                    EventReloadEvent event = new EventReloadEvent();
                    Bukkit.getPluginManager().callEvent(event);
                    Bukkit.broadcast(ChatColor.GREEN + "Event reloaded!", "christmas.admin");
                } else {
                    sender.sendMessage(ChatColor.RED + "The event is not running.");
                }

                break;

            case "remaining":

                Integer hiders = 0, seekers = 0;
                sender.sendMessage(ChatColor.GOLD + "Hiders:");
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (player.hasPermission("christmas.hider")) {
                        sender.sendMessage(ChatColor.YELLOW + player.getName());
                        hiders++;
                    }
                }
                sender.sendMessage(ChatColor.GOLD + "Seekers:");
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (player.hasPermission("christmas.seeker")) {
                        sender.sendMessage(ChatColor.YELLOW + player.getName());
                        seekers++;
                    }
                }

                sender.sendMessage(ChatColor.GOLD + "There is " + hiders + " hiders and " + seekers + " seekers.");

                break;

            case "release":

                Jingle.sounds();
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (player.hasPermission("christmas.seeker")) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                                "ewarp christmas2019start " + player.getName());
                    }
                }
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.sendTitle("SEEKERS RELEASED", "GOOD LUCK", 10, 60, 10);
                }

                break;

        }

        return false;

    }

}
