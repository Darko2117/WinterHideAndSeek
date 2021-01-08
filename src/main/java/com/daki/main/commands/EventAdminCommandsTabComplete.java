package com.daki.main.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class EventAdminCommandsTabComplete implements TabCompleter {
    private static final String[] COMMANDS = { "start", "end", "reload", "hiders", "seekers", "remaining", "release" };

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
        }
        return null;
    }
}
