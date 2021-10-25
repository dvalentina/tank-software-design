package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;
import java.util.HashSet;

public class RandomLevelGenerator implements LevelGenerator{
    private GridPoint2 playerCoordinates;
    private HashSet<GridPoint2> treesCoordinates = new HashSet<>();
    private HashSet<GridPoint2> tanksCoordinates = new HashSet<>();

    final int levelWidth = 10;
    final int levelHeight = 8;

    @Override
    public Level generateLevel() {
        generateTreesCoordinates();
        generateTanksCoordinates();
        generatePlayerCoordinates();

        ArrayList<Tree> trees = new ArrayList<>();
        for (GridPoint2 coordinates : treesCoordinates) {
            trees.add(new Tree(coordinates));
        }
        ArrayList<Player> tanks = new ArrayList<>();
        for (GridPoint2 coordinates : tanksCoordinates) {
            tanks.add(new Player(coordinates, 0f));
        }
        Player player = new Player(playerCoordinates, 0f);

        return new Level(player, trees, tanks, levelHeight, levelWidth);
    }

    private void generateTreesCoordinates() {
        int numberOfTrees = 7 + (int)(Math.random()*7);
        while (treesCoordinates.size() != numberOfTrees) {
            treesCoordinates.add(generateRandomPosition());
        };
    }

    private void generatePlayerCoordinates() {
        GridPoint2 position = generateRandomPosition();
        while (treesCoordinates.contains(position) || tanksCoordinates.contains(position)) {
            position = generateRandomPosition();
        };
        playerCoordinates = position;
    }

    private void generateTanksCoordinates() {
        int numberOfTanks = 1 + (int)(Math.random()*5);
        while (tanksCoordinates.size() != numberOfTanks) {
            GridPoint2 position = generateRandomPosition();
            while (treesCoordinates.contains(position)) {
                position = generateRandomPosition();
            };
            tanksCoordinates.add(position);
        };
    }

    private GridPoint2 generateRandomPosition() {
        int x = (int)(Math.random()*levelWidth);
        int y = (int)(Math.random()*levelHeight);

        return new GridPoint2(x, y);
    }
}
