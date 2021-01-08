package com.daki.main.Event;

import com.daki.main.christmas.global.Sounds;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class EventEndEventListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEventEnd(EventEndEvent event) {

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission("winterhideandseek.seeker")) {
                player.getInventory().clear();
            }
        }

        Sounds.playSounds();

        for (Player player : Bukkit.getOnlinePlayers()) {

            player.sendTitle(ChatColor.WHITE + "This round is now over!", "", 20, 100, 20);

        }

        Bukkit.broadcast(ChatColor.RED + "Event shut down!", "winterhideandseek.admin");

        EventManager.getExistingEvent().setRunning(false);

    }

}
