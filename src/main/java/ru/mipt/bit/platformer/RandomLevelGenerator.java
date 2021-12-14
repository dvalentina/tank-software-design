package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.events.EventManager;

import java.util.ArrayList;
import java.util.HashSet;

public class RandomLevelGenerator implements LevelGenerator{
    final int levelWidth = 10;
    final int levelHeight = 8;

    @Override
    public Level generateLevel(EventManager events) {
        HashSet<GridPoint2> treesCoordinates = generateTreesCoordinates();
        HashSet<GridPoint2> tanksCoordinates = generateTanksCoordinates(treesCoordinates);
        GridPoint2 playerCoordinates = generatePlayerCoordinates(treesCoordinates, tanksCoordinates);

        ArrayList<Tree> trees = new ArrayList<>();
        for (GridPoint2 coordinates : treesCoordinates) {
            trees.add(new Tree(coordinates));
        }
        ArrayList<Tank> tanks = new ArrayList<>();
        for (GridPoint2 coordinates : tanksCoordinates) {
            tanks.add(new Tank(coordinates, 0f));
        }
        Tank player = new Tank(playerCoordinates, 0f);

        return new Level(player, trees, tanks, levelHeight, levelWidth, events);
    }

    private HashSet<GridPoint2> generateTreesCoordinates() {
        HashSet<GridPoint2> treesCoordinates = new HashSet<>();
        int numberOfTrees = 7 + (int)(Math.random()*7);
        while (treesCoordinates.size() != numberOfTrees) {
            treesCoordinates.add(generateRandomPosition());
        }
        return treesCoordinates;
    }

    private GridPoint2 generatePlayerCoordinates(HashSet<GridPoint2> treesCoordinates, HashSet<GridPoint2> tanksCoordinates) {
        GridPoint2 position = generateRandomPosition();
        while (treesCoordinates.contains(position) || tanksCoordinates.contains(position)) {
            position = generateRandomPosition();
        }
        return position;
    }

    private HashSet<GridPoint2> generateTanksCoordinates(HashSet<GridPoint2> treesCoordinates) {
        HashSet<GridPoint2> tanksCoordinates = new HashSet<>();
        int numberOfTanks = 1 + (int)(Math.random()*5);
        while (tanksCoordinates.size() != numberOfTanks) {
            GridPoint2 position = generateRandomPosition();
            while (treesCoordinates.contains(position)) {
                position = generateRandomPosition();
            }
            tanksCoordinates.add(position);
        }
        return tanksCoordinates;
    }

    private GridPoint2 generateRandomPosition() {
        int x = (int)(Math.random()*levelWidth);
        int y = (int)(Math.random()*levelHeight);

        return new GridPoint2(x, y);
    }
}
