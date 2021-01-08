package com.daki.main.objects;

import com.daki.main.objects.Enums.EventRole;
import org.bukkit.entity.Player;

public class Participant {

    Player player;
    EventRole eventRole;

    public Participant(Player player, EventRole eventRole) {

        this.player = player;
        this.eventRole = eventRole;

    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public EventRole getEventRole() {
        return eventRole;
    }

    public void setEventRole(EventRole eventRole) {
        this.eventRole = eventRole;
    }

}
