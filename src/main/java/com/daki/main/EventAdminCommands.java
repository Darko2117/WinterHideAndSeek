package com.daki.main;

import com.daki.main.Event.EventEndEvent;
import com.daki.main.Event.EventManager;
import com.daki.main.Event.EventReloadEvent;
import com.daki.main.Event.EventStartEvent;
import com.daki.main.Objects.Enums.EventRole;
import com.daki.main.Objects.Participant;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class EventAdminCommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (args.length < 1) return true;

        if ("start".equals(args[0])) {

            if (EventManager.getExistingEvent().getRunning()) {
                sender.sendMessage(ChatColor.RED + "Event is already running!");
                return true;
            }

            Bukkit.getPluginManager().callEvent(new EventStartEvent());

        } else if ("end".equals(args[0])) {

            if (!EventManager.getExistingEvent().getRunning()) {
                sender.sendMessage(ChatColor.RED + "The event is not running!");
                return true;
            }

            Bukkit.getPluginManager().callEvent(new EventEndEvent());

        } else if ("reload".equals(args[0])) {

            if (!EventManager.getExistingEvent().getRunning()) {
                sender.sendMessage(ChatColor.RED + "The event is not running!");
                return true;
            }

            Bukkit.getPluginManager().callEvent(new EventReloadEvent());

        } else if ("hiders".equals(args[0])) {

            if (args.length < 3) return true;

            if (args[1].equals("add")) {

                Player player = Bukkit.getPlayer(args[2]);

                Participant participant = new Participant(player, EventRole.Hider);

                EventManager.getExistingEvent().addParticipant(participant);

            } else if (args[1].equals("remove")) {

                Player player = Bukkit.getPlayer(args[2]);

                Participant participant = new Participant(player, EventRole.Hider);

                EventManager.getExistingEvent().removeParticipant(participant);

            }

        } else if ("seekers".equals(args[0])) {

            if (args.length < 3) return true;

            if (args[1].equals("add")) {

                Player player = Bukkit.getPlayer(args[2]);

                Participant participant = new Participant(player, EventRole.Seeker);

                EventManager.getExistingEvent().addParticipant(participant);

            } else if (args[1].equals("remove")) {

                Player player = Bukkit.getPlayer(args[2]);

                Participant participant = new Participant(player, EventRole.Seeker);

                EventManager.getExistingEvent().removeParticipant(participant);

            }

        } else if ("remaining".equals(args[0])) {

            List<String> hiders = new ArrayList<>();
            List<String> seekers = new ArrayList<>();
            List<String> notParticipating = new ArrayList<>();

            for (Player player : Bukkit.getOnlinePlayers()) {

                Participant participant = EventManager.existingEvent.getParticipantFromPlayerName(player.getName());

                if (participant == null) {
                    notParticipating.add(player.getName());
                    return true;

                }
                if (participant.getEventRole().equals(EventRole.Hider)) {
                    hiders.add(participant.getPlayer().getName());
                } else if (participant.getEventRole().equals(EventRole.Seeker)) {
                    seekers.add(participant.getPlayer().getName());
                }

            }

            String message = "";

            message = message.concat("Hiders (" + hiders.size() + "): ");
            for (Integer i = 0; i < hiders.size(); i++) {
                message = message.concat(hiders.get(i));
                if (i != hiders.size() - 1) {
                    message = message.concat(", ");
                }
            }

            message = message.concat("\nSeekers (" + seekers.size() + "): ");
            for (Integer i = 0; i < seekers.size(); i++) {
                message = message.concat(seekers.get(i));
                if (i != seekers.size() - 1) {
                    message = message.concat(", ");
                }
            }

            message = message.concat("\nNot participating (" + notParticipating.size() + "): ");
            for (Integer i = 0; i < notParticipating.size(); i++) {
                message = message.concat(notParticipating.get(i));
                if (i != notParticipating.size() - 1) {
                    message = message.concat(", ");
                }
            }

            sender.sendMessage(message);

        } else if ("release".equals(args[0])) {

            Sounds.playSounds();

            for (Participant participant : EventManager.getExistingEvent().getParticipants()) {
                if (participant.getEventRole().equals(EventRole.Seeker)) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "cmi warp SeekersStartWarp " + participant.getPlayer().getName() + " -s");
                }
            }

            for (Player player : Bukkit.getOnlinePlayers()) {
                player.sendTitle("SEEKERS RELEASED", "GOOD LUCK", 10, 60, 10);
            }
        }

        return false;

    }

}