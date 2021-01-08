package com.daki.main.event.listeners;

import com.daki.main.WinterHideAndSeek;
import com.daki.main.Sounds;
import com.daki.main.christmas.seeker.items.SeekerItems;
import com.daki.main.event.events.EventStartEvent;
import com.daki.main.event.manager.EventManager;
import com.daki.main.objects.Enums.EventRole;
import com.daki.main.objects.Participant;
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

        for (Participant participant : EventManager.getExistingEvent().getParticipants()) {
            if (participant.getEventRole().equals(EventRole.Seeker)) {

                participant.getPlayer().getInventory().clear();
                participant.getPlayer().getInventory().addItem(SeekerItems.Snowballs());
                participant.getPlayer().getInventory().addItem(SeekerItems.Speed());

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
