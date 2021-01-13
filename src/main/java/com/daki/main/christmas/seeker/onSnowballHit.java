package com.daki.main.christmas.seeker;

import com.daki.main.WinterHideAndSeek;
import com.daki.main.event.manager.EventManager;
import com.daki.main.objects.Enums.EventRole;
import com.daki.main.objects.Event;
import com.daki.main.objects.Participant;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class onSnowballHit implements Listener {

    @EventHandler
    public void onSnowballHitHider(ProjectileHitEvent e) {
        if (!EventManager.getExistingEvent().getRunning()){
            return;
        }
        if (e.getEntity().getType().equals(EntityType.SNOWBALL)) {
            if (e.getHitEntity() instanceof Player) {
                Player reciever = (Player) e.getHitEntity();
                Participant participantFromName = EventManager.getExistingEvent().getParticipantFromPlayerName(reciever.getName());
                if (participantFromName!=null && participantFromName.getEventRole().equals(EventRole.Hider)) {
                    reciever.setHealth(0);
                    Player sender = (Player) e.getEntity().getShooter();
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                            "lp user " + reciever.getName() + " permission unset christmas.hider");
                    if (!GV.SnowballChatCooldown.containsKey(reciever)) {

                        Integer hiders = 0;
                        Event existingEvent = EventManager.getExistingEvent();
                        existingEvent.removeParticipant(existingEvent.getParticipantFromPlayerName(reciever.getName()));

                        for (Participant participant : existingEvent.getParticipants()){
                            if (participant.getEventRole().equals(EventRole.Hider)){
                                hiders++;
                            }
                        }
                        if (hiders == 1) {
                            Bukkit.getServer().broadcastMessage(ChatColor.RED + sender.getName() + " has found "
                                    + reciever.getName() + ". " + hiders + " hider remaining.");
                            GV.SnowballChatCooldown.put(reciever, true);
                        } else if (hiders != 1) {
                            Bukkit.getServer().broadcastMessage(ChatColor.RED + sender.getName() + " has found "
                                    + reciever.getName() + ". " + hiders + " hiders remaining.");
                            GV.SnowballChatCooldown.put(reciever, true);
                        }

                        new BukkitRunnable() {
                            public void run() {
                                GV.SnowballChatCooldown.remove(reciever);
                            }
                        }.runTaskLater(WinterHideAndSeek.getInstance(), 100);
                    }
                }
            }
        }
    }
}
