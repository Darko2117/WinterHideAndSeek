package com.daki.main.event.manager;

import com.daki.main.objects.Event;

public class EventManager {

    public static Event existingEvent;

    public static Event getExistingEvent() {
        return existingEvent;
    }

    public static void setExistingEvent(Event existingEvent) {
        EventManager.existingEvent = existingEvent;
    }

}
