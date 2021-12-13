package ru.mipt.bit.platformer.commands;

import ru.mipt.bit.platformer.Bullet;
import ru.mipt.bit.platformer.Level;

public class MoveBulletCommand implements Command {
    private final Bullet bullet;
    private final Level level;

    public MoveBulletCommand(Bullet bullet, Level level) {
        this.bullet = bullet;
        this.level = level;
    }

    @Override
    public void execute() {
        bullet.moveFurther(level);
    }
}