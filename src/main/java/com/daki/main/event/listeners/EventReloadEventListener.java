package com.daki.main.event.listeners;

import com.daki.main.christmas.seeker.items.SeekerItems;
import com.daki.main.event.events.EventReloadEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class EventReloadEventListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEventReload(EventReloadEvent event) {

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission("winterhideandseek.seeker")) {

                player.getInventory().clear();
                player.getInventory().addItem(SeekerItems.Snowballs());
                player.getInventory().addItem(SeekerItems.Speed());

            }
        }

        Bukkit.broadcast(ChatColor.GREEN + "Event reloaded!", "winterhideandseek.admin");

    }

}
