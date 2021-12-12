package ru.mipt.bit.platformer;

import ru.mipt.bit.platformer.commands.Command;

import java.util.ArrayDeque;
import java.util.ArrayList;

public interface Game {
    ArrayDeque<Command> generateOtherTanksCommands(Level level);
}
