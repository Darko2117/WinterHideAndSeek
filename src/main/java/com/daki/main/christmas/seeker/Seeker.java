package com.daki.main.christmas.seeker;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Seeker implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cmdsender, Command cmd, String label, String[] args) {

        if (cmdsender.hasPermission("christmas.admin")) {
            try {
                Player sender = (Player) cmdsender;
                Player player = (Player) Bukkit.getPlayer(args[1]);

                if (args[0].equals("add")) {
                    add(sender, player);
                } else if (args[0].equals("remove")) {
                    remove(sender, player);
                }

            } catch (Exception ex) {
            }
            return false;
        } else {
            cmdsender.sendMessage(ChatColor.RED + "You don't have the permission to do this.");
        }
        return false;
    }

    public void add(Player sender, Player player) {
        if (sender.hasPermission("christmas.admin")) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                    "lp user " + player.getName() + " permission set christmas.seeker");
            GV.seekers.add(player);
        }
    }

    public void remove(Player sender, Player player) {
        if (sender.hasPermission("christmas.admin")) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                    "lp user " + player.getName() + " permission unset christmas.seeker");
            GV.seekers.remove(player);
        }
    }

}
