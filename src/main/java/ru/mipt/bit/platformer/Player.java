package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;
import java.util.HashSet;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class Player implements Movable {
    /* Domain (entity) */

    // player current position coordinates on level 10x8 grid (e.g. x=0, y=1)
    private GridPoint2 coordinates;
    // which tile the player want to go next
    private GridPoint2 destinationCoordinates;
    private float movementProgress = 1f;
    private float rotation;

    private int healthPoints = 10;

    Player(GridPoint2 initialCoordinates, float rotation) {
        this.destinationCoordinates = initialCoordinates;
        this.coordinates = new GridPoint2(this.destinationCoordinates);
        this.rotation = rotation;
    }

    @Override
    public boolean isMoving() {
        return isEqual(this.movementProgress, 1f);
    }

    @Override
    public void move(Direction direction, ArrayList<Tree> trees, ArrayList<Player> otherTanks, HashSet<GridPoint2> levelBorders) {
        /* Application (use-case) */

        HashSet<GridPoint2> treesCoordinates = new HashSet<>();
        for (Tree tree : trees) {
            treesCoordinates.add(tree.getCoordinates());
        }
        HashSet<GridPoint2> tanksCoordinates = new HashSet<>();
        HashSet<GridPoint2> tanksDestinationCoordinates = new HashSet<>();
        for (Player tank : otherTanks) {
            tanksCoordinates.add(tank.getCoordinates());
            tanksDestinationCoordinates.add(tank.getDestinationCoordinates());
        }

        if (isMoving()) {
            // check potential player destination for collision with obstacles
            GridPoint2 newCoordinates = new GridPoint2(coordinates).add(direction.getMovementVector());
            if ( (!treesCoordinates.contains(newCoordinates)) && (!tanksCoordinates.contains(newCoordinates))
            && (!levelBorders.contains(newCoordinates)) && (!tanksDestinationCoordinates.contains(newCoordinates)) ) {
                destinationCoordinates = newCoordinates;
                movementProgress = 0f;
            }
            rotation = direction.getRotation();
        }
    }

    @Override
    public void continueMovement(float deltaTime, float speed) {
        movementProgress = continueProgress(movementProgress, deltaTime, speed);
        if (isMoving()) {
            // record that the player has reached his/her destination
            coordinates = destinationCoordinates;
        }
    }

    public void decreaseHealthPoints(int damage) {
        /* Application (use-case) */
        healthPoints -= damage;
    }

    public int getHealthPoints() {
        /* Application (use-case) */
        return healthPoints;
    }

    @Override
    public float getRotation() { return this.rotation; }

    @Override
    public float getMovementProgress() {
        return this.movementProgress;
    }

    @Override
    public GridPoint2 getCoordinates() {
        return this.coordinates;
    }

    @Override
    public GridPoint2 getDestinationCoordinates() {
        return this.destinationCoordinates;
    }
}
