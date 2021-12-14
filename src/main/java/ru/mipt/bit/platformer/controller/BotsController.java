package ru.mipt.bit.platformer.controller;

import ru.mipt.bit.platformer.Direction;
import ru.mipt.bit.platformer.Level;
import ru.mipt.bit.platformer.Tank;
import ru.mipt.bit.platformer.commands.*;

import java.util.Random;

public class BotsController implements Controller{
    private final Level level;
    private final CommandsExecutor commandsExecutor;

    public BotsController(CommandsExecutor commandsExecutor, Level level) {
        this.level = level;
        this.commandsExecutor = commandsExecutor;
    }

    @Override
    public void generateCommands() {
        for (Tank tank : level.getOtherTanks()) {
            final int variant = new Random().nextInt(Direction.values().length + 1);

            switch (variant) {
                case 0:
                    commandsExecutor.addCommand(new ShootCommand(tank, level));
                    break;
                case 1:
                    commandsExecutor.addCommand(new MoveUpCommand(tank, level));
                    break;
                case 2:
                    commandsExecutor.addCommand(new MoveLeftCommand(tank, level));
                    break;
                case 3:
                    commandsExecutor.addCommand(new MoveDownCommand(tank, level));
                    break;
                case 4:
                    commandsExecutor.addCommand(new MoveRightCommand(tank, level));
                    break;
            }
        }
    }
}
