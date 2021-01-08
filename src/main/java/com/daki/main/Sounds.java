package com.daki.main;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Sounds {

    public static void playSounds() {

        for (Player player : Bukkit.getOnlinePlayers()) {

            new BukkitRunnable() {
                @Override
                public void run() {
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 0.75F);
                }
            }.runTaskLater(WinterHideAndSeek.getInstance(), 0);

            new BukkitRunnable() {
                public void run() {
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 0.87F);
                }
            }.runTaskLater(WinterHideAndSeek.getInstance(), 3);

            new BukkitRunnable() {
                public void run() {
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 1F);
                }
            }.runTaskLater(WinterHideAndSeek.getInstance(), 6);

        }

    }

}
