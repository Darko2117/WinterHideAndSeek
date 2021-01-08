package com.daki.main.Objects;

import com.daki.main.Objects.Enums.EventRole;
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
