package com.daki.main.commands.event;

import com.daki.main.WinterHideAndSeek;
import com.daki.main.event.events.EventEndEvent;
import com.daki.main.event.manager.EventManager;
import com.daki.main.event.events.EventReloadEvent;
import com.daki.main.event.events.EventStartEvent;
import com.daki.main.objects.Enums.EventRole;
import com.daki.main.objects.EventTimer;
import com.daki.main.objects.Participant;
import com.daki.main.Sounds;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
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

            int duration;
            if (args.length != 2){
                sender.sendMessage(ChatColor.RED + "Please specify an event duration in seconds!");
                return true;
            } else {
                try {
                    duration = Integer.parseInt(args[1]);
                } catch (Exception e) {
                    //lazy but im in a hurry
                    sender.sendMessage(ChatColor.RED + "Please specify an event duration in seconds (numbers only)!");
                    return true;
                }

                Bukkit.getPluginManager().callEvent(new EventStartEvent(duration));

            }

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
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission settemp cmi.kit.christmasseeker true 7d");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "cmi warp seekerswaitwarp " + player.getName() + " -s");
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
                    continue;

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

        } else if ("addstartpoint".equals(args[0])) {
            FileConfiguration config = WinterHideAndSeek.getInstance().getConfig();
            List<String> startpoints;

            if (!config.contains("startpoints")) {
                startpoints = new ArrayList<>();
            } else {
                startpoints = config.getStringList("startpoints");
            }
            Location location = sender.getServer().getPlayer(sender.getName()).getLocation();
            String loc = location.getWorld().getName() + ", " + (Math.round(location.getX()) + .5) + ", " +
                    (Math.round(location.getY()) + .5) + ", " + (Math.round(location.getZ()) + .5);

            startpoints.add(loc);
            config.set("startpoints", startpoints);
            WinterHideAndSeek.getInstance().saveConfig();

            sender.sendMessage(ChatColor.GREEN + "Added " + loc + " as a start point for hiders.");
        } else if ("deleteallstartpoints".equals(args[0])) {
            FileConfiguration configFile = WinterHideAndSeek.getInstance().getConfig();
            List<String> startpointsList = new ArrayList<>();
            configFile.set("startpoints", startpointsList);
            WinterHideAndSeek.getInstance().saveConfig();
        } else if ("remainingtime".equals(args[0])){
            EventTimer timer = EventManager.getExistingEvent().getTimer();
            if (timer == null){
                sender.sendMessage(ChatColor.RED + "No timer started yet!");
                return true;
            }
            int remainingTime = timer.getRemainingTime();
            String message;
            if (remainingTime == 0){
                message = remainingTime + " seconds left in the event.";
            } else if (remainingTime == 1){
                message = remainingTime + " second left in the event.";
            } else if (remainingTime < 60){
                message = remainingTime + " seconds left in the event.";
            } else {
                int minutes = remainingTime / 60;
                int seconds = remainingTime % 60;
                message = minutes + " minute";
                if (minutes > 1){
                    message += "s";
                }
                if (seconds != 0){
                    message += " and " + seconds + " second";
                    if (seconds > 1){
                        message += "s";
                    }
                }
                message += " left in the event.";
            }
            sender.sendMessage(ChatColor.AQUA + message);
        }

        return true;

    }

}