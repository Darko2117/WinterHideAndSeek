package com.daki.main.christmas.hider;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class onPlayerDisconnect implements Listener {

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        if (e.getPlayer().hasPermission("christmas.hider")) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                    "lp user " + e.getPlayer().getName() + " permission unset christmas.hider");
        }
    }
}
