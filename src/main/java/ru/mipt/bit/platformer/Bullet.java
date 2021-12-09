package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;
import java.util.HashSet;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class Bullet implements Movable {
    /* Domain (entity) */

    private GridPoint2 coordinates;
    private GridPoint2 destinationCoordinates;
    private float movementProgress = 1f;
    private float rotation;

    Bullet(GridPoint2 initialCoordinates, float rotation) {
        this.destinationCoordinates = initialCoordinates;
        this.coordinates = new GridPoint2(this.destinationCoordinates);
        this.rotation = rotation;
    }

    @Override
    public boolean isMoving() {
        return isEqual(this.movementProgress, 1f);
    }

    @Override
    public void move(Direction direction, ArrayList<Tree> trees, ArrayList<Player> tanks, HashSet<GridPoint2> levelBorders) {
        HashSet<GridPoint2> treesCoordinates = new HashSet<>();
        for (Tree tree : trees) {
            treesCoordinates.add(tree.getCoordinates());
        }
        HashSet<GridPoint2> tanksCoordinates = new HashSet<>();
        HashSet<GridPoint2> tanksDestinationCoordinates = new HashSet<>();
        for (Player tank : tanks) {
            tanksCoordinates.add(tank.getCoordinates());
            tanksDestinationCoordinates.add(tank.getDestinationCoordinates());
        }

        if (isMoving()) {
            GridPoint2 newCoordinates = new GridPoint2(coordinates).add(direction.getMovementVector());
            if ( (!treesCoordinates.contains(newCoordinates)) && (!levelBorders.contains(newCoordinates)) ) {
                destinationCoordinates = newCoordinates;
                movementProgress = 0f;
            }
            if ((tanksCoordinates.contains(newCoordinates)) && (tanksDestinationCoordinates.contains(newCoordinates))) {
                // deal damage
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
