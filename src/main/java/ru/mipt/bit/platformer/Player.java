package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;

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

    public void move(Direction direction, GridPoint2 obstacleCoordinates) {
        if (isMoving()) {
            // check potential player destination for collision with obstacles
            if (!obstacleCoordinates.equals(new GridPoint2(coordinates).add(direction.getMovementVector()))) {
                destinationCoordinates = new GridPoint2(destinationCoordinates).add(direction.getMovementVector());
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

    public void setCoordinates(GridPoint2 coordinates) {
        this.coordinates = coordinates;
    }

    public GridPoint2 getCoordinates() {
        return this.coordinates;
    }

    public GridPoint2 getDestinationCoordinates() {
        return this.destinationCoordinates;
    }
}
