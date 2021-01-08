package com.daki.main.event.listeners;

import com.daki.main.WinterHideAndSeek;
import com.daki.main.Sounds;
import com.daki.main.christmas.seeker.items.SeekerItems;
import com.daki.main.event.events.EventStartEvent;
import com.daki.main.event.manager.EventManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

public class EventStartEventListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEventStart(EventStartEvent event) {

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission("winterhideandseek.seeker")) {

                player.getInventory().clear();
                player.getInventory().addItem(SeekerItems.Snowballs());
                player.getInventory().addItem(SeekerItems.Speed());

            }
        }

        Sounds.playSounds();

        for (Player player : Bukkit.getOnlinePlayers()) {

            player.sendTitle(ChatColor.WHITE + "WINTER", ChatColor.WHITE + "HIDE AND SEEK EVENT IS STARTING", 20, 100, 20);

            new BukkitRunnable() {
                public void run() {
                    player.sendTitle(ChatColor.WHITE + "FIND A SPOT TO HIDE IN", "SEEKERS WILL BE RELEASED IN 5 MINUTES", 20, 100, 20);
                }

            }.runTaskLater(WinterHideAndSeek.getInstance(), 120);

        }

        Bukkit.broadcast(ChatColor.GREEN + "Event started!", "winterhideandseek.admin");

        EventManager.getExistingEvent().setRunning(true);

    }

}
