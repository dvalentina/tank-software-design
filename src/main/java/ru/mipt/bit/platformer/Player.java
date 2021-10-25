package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;
import java.util.HashSet;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class Player {
    // player current position coordinates on level 10x8 grid (e.g. x=0, y=1)
    private GridPoint2 coordinates;
    // which tile the player want to go next
    private GridPoint2 destinationCoordinates;

    private float movementProgress = 1f;

    private float rotation;

    Player(GridPoint2 initialCoordinates, float rotation) {
        this.destinationCoordinates = initialCoordinates;
        this.coordinates = new GridPoint2(this.destinationCoordinates);
        this.rotation = rotation;
    }

    public boolean isMoving() {
        return isEqual(this.movementProgress, 1f);
    }

    public void move(Direction direction, ArrayList<Tree> trees, ArrayList<Player> otherTanks, HashSet<GridPoint2> levelBorders) {
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

    public void continueMovement(float deltaTime, float speed) {
        movementProgress = continueProgress(movementProgress, deltaTime, speed);
        if (isMoving()) {
            // record that the player has reached his/her destination
            coordinates = destinationCoordinates;
        }
    }

    public float getRotation() { return this.rotation; }

    public float getMovementProgress() {
        return this.movementProgress;
    }

    public GridPoint2 getCoordinates() {
        return this.coordinates;
    }

    public GridPoint2 getDestinationCoordinates() {
        return this.destinationCoordinates;
    }
}
