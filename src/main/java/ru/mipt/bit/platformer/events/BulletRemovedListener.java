package ru.mipt.bit.platformer.events;

import ru.mipt.bit.platformer.Graphics;

public class BulletRemovedListener implements EventListener {
    private final Graphics graphics;

    public BulletRemovedListener(Graphics graphics) {
        this.graphics = graphics;
    }

    @Override
    public void update() {
        graphics.removeBullet();
    }
}
