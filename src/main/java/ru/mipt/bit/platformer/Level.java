package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.events.EventManager;
import ru.mipt.bit.platformer.events.EventTypes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Level {
    public final EventManager events;

    private Tank player;
    private List<Tree> trees;
    private ArrayList<Tank> otherTanks;
    private ArrayList<Bullet> bullets = new ArrayList<>();

    private int height;
    private int width;
    private HashSet<GridPoint2> borders = new HashSet<>();

    Level(Tank player, ArrayList<Tree> trees, int height, int width, EventManager events) {
        this(player, trees, null, height, width, events);
    }

    Level(Tank player, ArrayList<Tree> trees, ArrayList<Tank> otherTanks, int height, int width, EventManager events) {
        this.player = player;
        this.trees = trees;
        this.otherTanks = otherTanks;
        this.height = height;
        this.width = width;
        this.events = events;
        createBordersCoordinates();
    }

    public void addBullet(Bullet bullet) {
        bullets.add(bullet);
        events.notify(EventTypes.BULLET_ADDED);
    }

    public void removeBullet(Bullet bullet) {
        bullets.remove(bullet);
        events.notify(EventTypes.BULLET_REMOVED);
    }

    public Tank getPlayer() { return player; }

    public List<Tree> getTreeObstacles() { return trees; }

    public ArrayList<Tank> getOtherTanks() {
        return otherTanks;
    }

    public ArrayList<Bullet> getBullets() { return bullets; }

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
        HashSet<GridPoint2> treesCoordinates = new HashSet<>();
        for (Tree tree : trees) {
            treesCoordinates.add(tree.getCoordinates());
        }
        HashSet<GridPoint2> tanksCoordinates = new HashSet<>();
        HashSet<GridPoint2> tanksDestinationCoordinates = new HashSet<>();
        for (Tank tank : otherTanks) {
            tanksCoordinates.add(tank.getCoordinates());
            tanksDestinationCoordinates.add(tank.getDestinationCoordinates());
        }
        tanksCoordinates.add(player.getCoordinates());
        tanksDestinationCoordinates.add(player.getDestinationCoordinates());

        return (treesCoordinates.contains(coordinates)) || (tanksCoordinates.contains(coordinates))
                || (borders.contains(coordinates)) || (tanksDestinationCoordinates.contains(coordinates));
    }

    public Tank checkHasTank(GridPoint2 coordinates) {
        HashMap<GridPoint2, Tank> tanksCoordinates = new HashMap<>();
        HashMap<GridPoint2, Tank> tanksDestinationCoordinates = new HashMap<>();
        for (Tank tank : otherTanks) {
            tanksCoordinates.put(tank.getCoordinates(), tank);
            tanksDestinationCoordinates.put(tank.getDestinationCoordinates(), tank);
        }
        tanksCoordinates.put(player.getCoordinates(), player);
        tanksDestinationCoordinates.put(player.getDestinationCoordinates(), player);
        if (tanksCoordinates.containsKey(coordinates)) {
            return tanksCoordinates.get(coordinates);
        } else if (tanksDestinationCoordinates.containsKey(coordinates)) {
            return tanksDestinationCoordinates.get(coordinates);
        }
        return null;
    }

    public void checkTanksHealthPoints() {
        final boolean isRemoved = otherTanks.removeIf(tank -> tank.getHealthPoints() < 0);
        if (isRemoved) {
            events.notify(EventTypes.TANK_REMOVED);
        }
    }
}
