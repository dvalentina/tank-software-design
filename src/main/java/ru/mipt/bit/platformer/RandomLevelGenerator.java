package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;
import java.util.HashSet;

public class RandomLevelGenerator implements LevelGenerator{
    private GridPoint2 playerCoordinates;
    private HashSet<GridPoint2> treesCoordinates = new HashSet<>();

    @Override
    public Level generateLevel() {
        generateTreesCoordinates();
        ArrayList<Tree> trees = new ArrayList<>();
        for (GridPoint2 coordinates : treesCoordinates) {
            trees.add(new Tree(coordinates));
        }

        generatePlayerCoordinates();
        Player player = new Player(playerCoordinates, 0f);

        return new Level(player, trees);
    }

    private void generateTreesCoordinates() {
        int numberOfTrees = (int)(Math.random()*20);
        while (treesCoordinates.size() != numberOfTrees) {
            treesCoordinates.add(generateRandomPosition());
        };
    }

    private void generatePlayerCoordinates() {
        GridPoint2 position = generateRandomPosition();
        while (treesCoordinates.contains(position)) {
            position = generateRandomPosition();
        };
        playerCoordinates = position;
    }

    private GridPoint2 generateRandomPosition() {
        int levelWidth = 10;
        int levelHeight = 8;

        int x = (int)(Math.random()*levelWidth);
        int y = (int)(Math.random()*levelHeight);

        return new GridPoint2(x, y);
    }
}
