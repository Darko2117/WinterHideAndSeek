package com.daki.main.christmas;

import com.daki.main.WinterHideAndSeek;
import com.daki.main.event.events.EventEndEvent;
import com.daki.main.event.manager.EventManager;
import com.daki.main.objects.Enums.EventRole;
import com.daki.main.objects.EventTimer;
import com.daki.main.objects.Participant;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TimerRunnable extends Thread {

    EventTimer eventTimer;
    private boolean cancelled;

    public TimerRunnable(EventTimer eventTimer){
        this.eventTimer = eventTimer;
        cancelled = false;
    }

    public void run(){
        int lastX = -1;
        while (!cancelled){
            int remainingTime = eventTimer.getRemainingTime();
            int x = remainingTime / 900; // 900 sec is 15 min
            int y = remainingTime % 900;
            if (lastX != x && -3 < y && 3 > y){ // If this isn't the same x as last x and y is between -3 and 3
                lastX = x;

                if (x > 4){
                    int z = x % 4;
                    if (z * 4 == x){
                        if (z == 1){
                            sendMessage(z + " hours remaining!");
                        } else {
                            sendMessage("1 hour remaining!");
                        }
                    }
                } else if (x > 0) {
                    sendMessage(x * 15 + " minutes remaining!");
                }

            } else if (x == 0){
                x = remainingTime / 60; //60 sec is a min
                y = remainingTime % 60;
                if (lastX != x && -3 < y && 3 > y) {
                    lastX = x;
                    switch (x){
                        case 10:
                            sendMessage("10 minutes remaining!");
                            break;
                        case 5:
                            sendMessage("5 minutes remaining!");
                            break;
                        case 2:
                            sendMessage("2 minutes remaining!");
                            break;
                        case 1:
                            sendMessage("1 minute remaining!");
                            break;
                    }
                } else if (x == 0){
                    switch (y){
                        case 30:
                            sendMessage("30 seconds remaining");
                            break;
                        case 15:
                            sendMessage("15 seconds remaining");
                            break;
                        case 10:
                            sendMessage("10 seconds remaining");
                            break;
                        case 5:
                            sendMessage("5 seconds remaining");
                            break;
                        case 4:
                            sendMessage("4 seconds remaining");
                            break;
                        case 3:
                            sendMessage("3 seconds remaining");
                            break;
                        case 2:
                            sendMessage("2 seconds remaining");
                            break;
                        case 1:
                            sendMessage("1 seconds remaining");
                            break;
                        case 0:
                            if (!EventManager.getExistingEvent().getRunning()) {
                                WinterHideAndSeek.getInstance().getLogger().warning("Timer attempted to close the event while it wasn't running");
                                return;
                            }
                            StringBuilder message = new StringBuilder("The winners are: ");
                            for (Participant p : EventManager.getExistingEvent().getParticipants()){
                                if (p.getEventRole().equals(EventRole.Hider)) {
                                    String playerName = p.getPlayer().getName();
                                    message.append(playerName).append(", ");
                                    Bukkit.getScheduler().runTask(WinterHideAndSeek.getInstance(), () -> {
                                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + playerName + " permission settemp cmi.kit.christmaswinner true 7d");
                                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "spawn " + playerName + " -s");
                                    });
                                }
                            }
                            String messageToSend = message.toString().substring(0, message.length()-2) + "!";
                            Bukkit.broadcastMessage(messageToSend);
                            for (Player p : Bukkit.getOnlinePlayers()){
                                if (p.hasPermission("winterhideandseek.admin")){
                                    Bukkit.getScheduler().runTask(WinterHideAndSeek.getInstance(), () -> {
                                        Bukkit.getPluginManager().callEvent(new EventEndEvent());
                                    });
                                    break;
                                }
                            }
                            cancelled = true;
                            break;
                    }
                }
            }

            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    private void sendMessage(String title){
        for (Participant p : EventManager.getExistingEvent().getParticipants()){
            p.getPlayer().sendTitle(title, "", 20, 60 , 20);
        }
    }

    public void stopTimer(){
        cancelled = true;
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("Closing object");
        super.finalize();
    }
}
