package ru.mipt.bit.platformer.commands;

import java.util.ArrayDeque;

public class CommandsExecutor {
    private ArrayDeque<Command> commands = new ArrayDeque<>();

    public void addCommand(Command command) {
        commands.add(command);
    }

    public void executeCommands() {
       while (!commands.isEmpty()) {
           commands.pop().execute();
       }
    }
}
