package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;

import static com.badlogic.gdx.math.MathUtils.isEqual;

public class Player {
    // player current position coordinates on level 10x8 grid (e.g. x=0, y=1)
    private GridPoint2 coordinates;
    // which tile the player want to go next
    private GridPoint2 destinationCoordinates;

    private float movementProgress = 1f;

    private float rotation;
    Player() {
        // set player initial position
        this.destinationCoordinates = new GridPoint2(1, 1);
        this.coordinates = new GridPoint2(this.destinationCoordinates);
        this.rotation = 0f;
    }

    public boolean isMoving() {
        return isEqual(this.movementProgress, 1f);
    }

    public void move(Direction direction, GridPoint2 obstacleCoordinates) {
        if (isMoving()) {
            // check potential player destination for collision with obstacles
            if (!obstacleCoordinates.equals(new GridPoint2(getCoordinates()).add(direction.getMovementVector()))) {
                destinationCoordinates = new GridPoint2(getDestinationCoordinates()).add(direction.getMovementVector());
                movementProgress = 0f;
            }
            rotation = direction.getRotation();
        }
    }

    public float getRotation() {
        return this.rotation;
    }

    public void setMovementProgress(float movementProgress) {
        this.movementProgress = movementProgress;
    }

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
