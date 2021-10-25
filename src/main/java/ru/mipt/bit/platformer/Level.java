package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;
import java.util.HashSet;

public class Level {
    private Player player;
    private ArrayList<Tree> treeObstacles;
    private ArrayList<Player> otherTanks;

    private int height;
    private int width;
    private HashSet<GridPoint2> borders = new HashSet<>();

    Level(Player player, ArrayList<Tree> trees, int height, int width) {
        this(player, trees, null, height, width);
    }

    Level(Player player, ArrayList<Tree> trees, ArrayList<Player> otherTanks, int height, int width) {
        this.player = player;
        this.treeObstacles = trees;
        this.otherTanks = otherTanks;
        this.height = height;
        this.width = width;
        createBordersCoordinates();
    }

    public Player getPlayer() { return player; }

    public ArrayList<Tree> getTreeObstacles() { return treeObstacles; }

    public ArrayList<Player> getOtherTanks() {
        return otherTanks;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public HashSet<GridPoint2> getBorders() {
        return borders;
    }

    private void createBordersCoordinates() {
        for (int x = -1; x < width + 1; x++) {
            borders.add(new GridPoint2(x, -1));
            borders.add(new GridPoint2(x, height));
        }
        for (int y = -1; y < height + 1; y++) {
            borders.add(new GridPoint2(-1, y));
            borders.add(new GridPoint2(width, y));
        }
    }
}
