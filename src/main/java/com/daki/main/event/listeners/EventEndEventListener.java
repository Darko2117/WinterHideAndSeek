package com.daki.main.event.listeners;

import com.daki.main.Sounds;
import com.daki.main.event.events.EventEndEvent;
import com.daki.main.event.manager.EventManager;
import com.daki.main.objects.Enums.EventRole;
import com.daki.main.objects.Participant;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class EventEndEventListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEventEnd(EventEndEvent event) {

        for (Participant participant : EventManager.getExistingEvent().getParticipants()) {
            if (participant.getEventRole().equals(EventRole.Seeker)) {
                participant.getPlayer().getInventory().clear();
            }
        }

        Sounds.playSounds();

        for (Player player : Bukkit.getOnlinePlayers()) {

            player.sendTitle(ChatColor.WHITE + "This round is now over!", "", 20, 100, 20);

        }

        Bukkit.broadcast(ChatColor.RED + "Event shut down!", "winterhideandseek.admin");

        EventManager.getExistingEvent().clearParticipants();
        EventManager.getExistingEvent().setRunning(false);

    }

}
