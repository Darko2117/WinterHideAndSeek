package com.daki.main.christmas.seeker;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class SeekersTabComplete implements TabCompleter {
    private static final String[] COMMANDS = { "add", "remove" };

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            List<String> completions = new ArrayList<>();
            for (String string : COMMANDS) {
                if (string.startsWith(args[0])) {
                    completions.add(string);
                }
            }
            return completions;
        } else if (args.length == 2 && args[0].equals("remove")) {
            List<String> completions = new ArrayList<>();
            for (Integer i = 0; i < GV.seekers.size(); i++) {
                if (GV.seekers.get(i).getName().startsWith(args[1])) {
                    completions.add(GV.seekers.get(i).getName());
                }
            }
            return completions;
        } else if (args.length == 2) {
            List<String> completions = new ArrayList<>();
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.getName().startsWith(args[1])) {
                    completions.add(player.getName());
                }
            }
            return completions;
        }

        return null;
    }
}
