package ru.mipt.bit.platformer;

import ru.mipt.bit.platformer.events.EventManager;

public interface LevelGenerator {
    Level generateLevel(EventManager events);
}
