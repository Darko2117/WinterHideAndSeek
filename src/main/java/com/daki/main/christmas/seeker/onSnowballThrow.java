package com.daki.main.christmas.seeker;

import com.daki.main.christmas.seeker.items.SeekerItems;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;

public class onSnowballThrow implements Listener {

    @EventHandler
    public void onThrow(ProjectileLaunchEvent e) {
        Player player = (Player) e.getEntity().getShooter();
        if (e.getEntity() instanceof Snowball) {
            if (player.hasPermission("christmas.seeker")) {
                if (player.getInventory().getItemInMainHand().getAmount() < 16) {
                    player.getInventory().addItem(SeekerItems.Snowballs());
                }
            }
        }
    }

}
