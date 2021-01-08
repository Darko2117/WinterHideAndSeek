package com.daki.main.christmas.seeker;

import com.daki.main.WinterHideAndSeek;
import com.daki.main.christmas.seeker.items.SeekerItems;
import com.daki.main.event.manager.EventManager;
import com.daki.main.objects.Enums.EventRole;
import com.daki.main.objects.Event;
import com.daki.main.objects.Participant;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class onAbilityUse implements Listener {

    @EventHandler
    public void onSpeedUse(PlayerInteractEvent e) {

        Player player = (Player) e.getPlayer();
        if (player.getInventory().getItemInMainHand().equals(SeekerItems.Speed())) {
            if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if (EventManager.getExistingEvent().getParticipantFromPlayerName(player.getName()).getEventRole().equals(EventRole.Seeker)) {
                    player.getInventory().remove(SeekerItems.Speed());
                    player.getInventory().addItem(SeekerItems.SpeedCooldown());
                    PotionEffect speed = new PotionEffect(PotionEffectType.SPEED, 200, 1);
                    player.addPotionEffect(speed);
                    new BukkitRunnable() {
                        public void run() {
                            player.getInventory().remove(SeekerItems.SpeedCooldown());
                            player.getInventory().addItem(SeekerItems.Speed());
                            player.sendMessage(ChatColor.GREEN + "Sugar Rush is ready to be used again!");
                        }
                    }.runTaskLater(WinterHideAndSeek.getInstance(), 600);
                }
            }
        } else if (player.getInventory().getItemInMainHand().equals(SeekerItems.SpeedCooldown())) {
            player.sendMessage(ChatColor.RED + "Sugar Rush is on a cooldown!");
        }
    }

}
