package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;
import java.util.HashSet;

public class Level {
    private Player player;
    private ArrayList<Tree> trees;
    private ArrayList<Player> otherTanks;

    private int height;
    private int width;
    private HashSet<GridPoint2> borders = new HashSet<>();

    Level(Player player, ArrayList<Tree> trees, int height, int width) {
        this(player, trees, null, height, width);
    }

    Level(Player player, ArrayList<Tree> trees, ArrayList<Player> otherTanks, int height, int width) {
        this.player = player;
        this.trees = trees;
        this.otherTanks = otherTanks;
        this.height = height;
        this.width = width;
        createBordersCoordinates();
    }

    public Player getPlayer() { return player; }

    public ArrayList<Tree> getTreeObstacles() { return trees; }

    public ArrayList<Player> getOtherTanks() {
        return otherTanks;
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

    public boolean checkHasObstacle(GridPoint2 coordinates) {
        HashSet<GridPoint2> treesCoordinates = new HashSet<GridPoint2>();
        for (Tree tree : trees) {
            treesCoordinates.add(tree.getCoordinates());
        }
        HashSet<GridPoint2> tanksCoordinates = new HashSet<GridPoint2>();
        HashSet<GridPoint2> tanksDestinationCoordinates = new HashSet<>();
        for (Player tank : otherTanks) {
            tanksCoordinates.add(tank.getCoordinates());
            tanksDestinationCoordinates.add(tank.getDestinationCoordinates());
        }
        tanksCoordinates.add(player.getCoordinates());
        tanksDestinationCoordinates.add(player.getDestinationCoordinates());

        return (treesCoordinates.contains(coordinates)) || (tanksCoordinates.contains(coordinates))
                || (borders.contains(coordinates)) || (tanksDestinationCoordinates.contains(coordinates));
    }
}
