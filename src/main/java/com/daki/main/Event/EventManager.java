package com.daki.main.Event;

import com.daki.main.Objects.Event;

public class EventManager {

    public static Event existingEvent = null;

    public static Event getExistingEvent() {
        return existingEvent;
    }

    public static void setExistingEvent(Event existingEvent) {
        EventManager.existingEvent = existingEvent;
    }

}
