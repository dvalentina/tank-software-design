package ru.mipt.bit.platformer.commands;

import ru.mipt.bit.platformer.Direction;
import ru.mipt.bit.platformer.Level;
import ru.mipt.bit.platformer.Movable;

public class MoveUpCommand implements Command {
    private final Movable movable;
    private final Level level;

    public MoveUpCommand(Movable movable, Level level) {
        this.movable = movable;
        this.level = level;
    }

    @Override
    public void execute() {
        movable.move(Direction.UP, level);
    }
}