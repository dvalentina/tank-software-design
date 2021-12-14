package ru.mipt.bit.platformer.controller;

import ru.mipt.bit.platformer.Bullet;
import ru.mipt.bit.platformer.Level;
import ru.mipt.bit.platformer.commands.CommandsExecutor;
import ru.mipt.bit.platformer.commands.MoveBulletCommand;

public class BulletsController implements Controller{
    private CommandsExecutor commandsExecutor;
    private Level level;

    public BulletsController(CommandsExecutor commandsExecutor, Level level) {
        this.commandsExecutor = commandsExecutor;
        this.level = level;
    }

    @Override
    public void generateCommands() {
        for (Bullet bullet : level.getBullets()) {
            commandsExecutor.addCommand(new MoveBulletCommand(bullet, level));
        }
    }
}
