package ru.mipt.bit.platformer.events;

import ru.mipt.bit.platformer.Graphics;

public class TankRemovedListener implements EventListener{
    private final Graphics graphics;

    public TankRemovedListener(Graphics graphics) {
        this.graphics = graphics;
    }

    @Override
    public void update() {
        graphics.removeTank();
    }
}
