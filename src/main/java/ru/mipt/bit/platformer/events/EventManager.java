package ru.mipt.bit.platformer.events;

import java.util.ArrayList;
import java.util.HashMap;

public class EventManager {
    private final HashMap<EventTypes, ArrayList<EventListener>> listeners = new HashMap<>();

    public void subscribe(EventTypes eventType, EventListener listener) {
        listeners.computeIfAbsent(eventType, k -> new ArrayList<>()).add(listener);
    }

    public void unsubscribe(EventTypes eventType, EventListener listener) {
        listeners.get(eventType).remove(listener);
    }

    public void notify(EventTypes eventType) {
        for (EventListener listener: listeners.get(eventType)) {
            listener.update();
        }
    }
}
