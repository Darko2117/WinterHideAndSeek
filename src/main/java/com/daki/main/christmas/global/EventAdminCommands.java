package com.daki.main.christmas.global;

import com.daki.main.Event.EventEndEvent;
import com.daki.main.Event.EventManager;
import com.daki.main.Event.EventReloadEvent;
import com.daki.main.Event.EventStartEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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

                Bukkit.getPluginManager().callEvent(new EventStartEvent());

                break;

            case "end":

                if (!EventManager.getExistingEvent().getRunning()) {
                    sender.sendMessage(ChatColor.RED + "The event is not running!");
                    return true;
                }

                Bukkit.getPluginManager().callEvent(new EventEndEvent());

                break;

            case "reload":

                if (!EventManager.getExistingEvent().getRunning()) {
                    sender.sendMessage(ChatColor.RED + "The event is not running!");
                    return true;
                }

                Bukkit.getPluginManager().callEvent(new EventReloadEvent());

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

                Sounds.playSounds();
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (player.hasPermission("christmas.seeker")) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                                "ewarp christmas2020start " + player.getName());
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
