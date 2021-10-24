package ru.mipt.bit.platformer;

import java.util.ArrayList;

public class Level {
    private Player player;
    private ArrayList<Tree> treeObstacles;

    Level(Player player, ArrayList<Tree> trees) {
        this.player = player;
        this.treeObstacles = trees;
    }

    public Player getPlayer() { return player; }

    public ArrayList<Tree> getTreeObstacles() { return treeObstacles; }
}
