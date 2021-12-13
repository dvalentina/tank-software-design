package ru.mipt.bit.platformer.commands;

import ru.mipt.bit.platformer.Level;
import ru.mipt.bit.platformer.Player;

public class ShootCommand implements Command {
    private final Player player;
    private final Level level;

    public ShootCommand(Player player, Level level) {
        this.player = player;
        this.level = level;
    }

    @Override
    public void execute() {
        player.shoot(level);
    }
}
