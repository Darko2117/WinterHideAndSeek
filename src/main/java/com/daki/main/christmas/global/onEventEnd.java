package com.daki.main.christmas.global;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class onEventEnd implements Listener {

    @EventHandler
    public void onStart(EventEndEvent e) {

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission("christmas.seeker")) {
                player.getInventory().clear();
            }
        }

    }

}
