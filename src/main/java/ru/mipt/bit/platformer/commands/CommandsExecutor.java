package ru.mipt.bit.platformer.commands;

import java.util.ArrayDeque;

public class CommandsExecutor {
    private ArrayDeque<Command> commands = new ArrayDeque<>();

    public void addCommand(Command command) {
        commands.add(command);
    }

    public void addCommandQueue(ArrayDeque<Command> commands) {
        while (!commands.isEmpty()) {
            this.commands.add(commands.pop());
        }
    }

    public void executeCommands() {
       while (!commands.isEmpty()) {
           commands.pop().execute();
       }
    }
}
