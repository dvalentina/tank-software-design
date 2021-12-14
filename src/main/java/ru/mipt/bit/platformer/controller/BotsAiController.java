package ru.mipt.bit.platformer.controller;

import ru.mipt.bit.platformer.GameAiAdapter;
import ru.mipt.bit.platformer.Level;
import ru.mipt.bit.platformer.commands.CommandsExecutor;

public class BotsAiController implements Controller{
    private final GameAiAdapter gameAiAdapter = new GameAiAdapter();

    private CommandsExecutor commandsExecutor;
    private Level level;

    public BotsAiController(CommandsExecutor commandsExecutor, Level level) {
        this.level = level;
        this.commandsExecutor = commandsExecutor;
    }

    @Override
    public void generateCommands() {
        commandsExecutor.addCommandQueue(gameAiAdapter.generateOtherTanksCommands(level));
    }
}
