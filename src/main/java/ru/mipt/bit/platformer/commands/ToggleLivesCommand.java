package ru.mipt.bit.platformer.commands;

import ru.mipt.bit.platformer.Graphics;

public class ToggleLivesCommand implements Command{
    private final Graphics graphics;

    public ToggleLivesCommand(Graphics graphics) {
        this.graphics = graphics;
    }

    @Override
    public void execute() {
        graphics.toggleDrawingLives();
    }
}
