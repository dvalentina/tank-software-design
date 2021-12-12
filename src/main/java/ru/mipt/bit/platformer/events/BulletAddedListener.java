package ru.mipt.bit.platformer.events;


import ru.mipt.bit.platformer.Bullet;
import ru.mipt.bit.platformer.Graphics;

public class BulletAddedListener implements EventListener {
    private final Graphics graphics;

    public BulletAddedListener(Graphics graphics) {
        this.graphics = graphics;
    }

    @Override
    public void update() {
        graphics.addBullet();
    }
}
