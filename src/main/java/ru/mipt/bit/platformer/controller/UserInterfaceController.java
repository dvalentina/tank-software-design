package ru.mipt.bit.platformer.controller;

import ru.mipt.bit.platformer.Graphics;
import ru.mipt.bit.platformer.InputHandler;
import ru.mipt.bit.platformer.commands.CommandsExecutor;

public class UserInterfaceController implements Controller{
    private CommandsExecutor commandsExecutor;
    private Graphics graphics;

    public UserInterfaceController(CommandsExecutor commandsExecutor, Graphics graphics) {
        this.commandsExecutor = commandsExecutor;
        this.graphics = graphics;
    }

    @Override
    public void generateCommands() {
        commandsExecutor.addCommand(InputHandler.handleOtherInput(graphics));
    }
}
