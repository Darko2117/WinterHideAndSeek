package com.daki.main.christmas.global;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EventEndEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

}
