package com.daki.main.christmas.seeker;

import com.daki.main.christmas.seeker.items.SeekerItems;
import com.daki.main.event.manager.EventManager;
import com.daki.main.objects.Enums.EventRole;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;

public class onSnowballThrow implements Listener {

    @EventHandler
    public void onThrow(ProjectileLaunchEvent e) {
        Player player = (Player) e.getEntity().getShooter();
        if (e.getEntity() instanceof Snowball) {
            if (EventManager.getExistingEvent().getParticipantFromPlayerName(player.getName()).getEventRole().equals(EventRole.Seeker)) {
                if (player.getInventory().getItemInMainHand().getAmount() < 16) {
                    ItemStack snowball = SeekerItems.Snowballs();
                    snowball.setAmount(1);
                    player.getInventory().addItem(snowball);
                }
            }
        }
    }

}
