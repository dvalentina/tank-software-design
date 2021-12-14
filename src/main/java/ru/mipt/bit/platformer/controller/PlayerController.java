package ru.mipt.bit.platformer.controller;

import ru.mipt.bit.platformer.InputHandler;
import ru.mipt.bit.platformer.Level;
import ru.mipt.bit.platformer.commands.CommandsExecutor;


public class PlayerController implements Controller{
    private CommandsExecutor commandsExecutor;
    private Level level;

    public PlayerController(CommandsExecutor commandsExecutor, Level level) {
        this.commandsExecutor = commandsExecutor;
        this.level = level;
    }

    @Override
    public void generateCommands() {
        commandsExecutor.addCommand(InputHandler.handlePlayerInput(level.getPlayer(), level));
    }
}
