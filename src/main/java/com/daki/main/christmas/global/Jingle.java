package com.daki.main.christmas.global;

import com.daki.main.WinterHideAndSeek;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Jingle {

    public static void sounds() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 0.75F);
            new BukkitRunnable() {
                public void run() {
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 0.87F);
                    new BukkitRunnable() {
                        public void run() {
                            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 1F);
                        }
                    }.runTaskLater(WinterHideAndSeek.getInstance(), 3);
                }
            }.runTaskLater(WinterHideAndSeek.getInstance(), 3);
        }
    }

}
