package ru.mipt.bit.platformer.commands;

import ru.mipt.bit.platformer.Direction;
import ru.mipt.bit.platformer.Level;
import ru.mipt.bit.platformer.Movable;

public class MoveRightCommand implements Command {
    private final Movable movable;
    private final Level level;

    public MoveRightCommand(Movable movable, Level level) {
        this.movable = movable;
        this.level = level;
    }

    @Override
    public void execute() {
        movable.move(Direction.RIGHT, level);
    }
}