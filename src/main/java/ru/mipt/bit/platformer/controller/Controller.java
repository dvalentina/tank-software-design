package ru.mipt.bit.platformer.controller;

import ru.mipt.bit.platformer.commands.Command;

import java.util.ArrayDeque;

public interface Controller {
    void generateCommands();
}
