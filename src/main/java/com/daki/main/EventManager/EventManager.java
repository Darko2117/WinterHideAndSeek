package com.daki.main.EventManager;

import com.daki.main.Objects.Event;

public class EventManager {

    public static Event existingEvent;

    public static Event getExistingEvent() {
        return existingEvent;
    }

    public static void setExistingEvent(Event existingEvent) {
        EventManager.existingEvent = existingEvent;
    }

}
