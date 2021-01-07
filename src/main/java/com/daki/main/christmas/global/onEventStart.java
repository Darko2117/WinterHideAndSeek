package com.daki.main.christmas.global;

import com.daki.main.christmas.seeker.items.SeekerItems;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class onEventStart implements Listener {

    @EventHandler
    public void onStart(EventStartEvent e) {

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission("christmas.seeker")) {
                player.getInventory().clear();
                for (Integer i = 0; i < 16; i++) {
                    player.getInventory().addItem(SeekerItems.Snowball());
                }
                player.getInventory().addItem(SeekerItems.Speed());
            }
        }
    }

}
