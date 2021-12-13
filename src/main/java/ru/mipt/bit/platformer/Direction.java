package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;

public enum Direction {
    UP(new GridPoint2(0, 1), 90f),
    LEFT(new GridPoint2(-1, 0), 180f),
    DOWN(new GridPoint2(0, -1), -90f),
    RIGHT(new GridPoint2(1, 0), 0f);

    private GridPoint2 movementVector;

    private float rotation;
    Direction(GridPoint2 movementVector, float rotation) {
        this.movementVector = movementVector;
        this.rotation = rotation;
    }

    public GridPoint2 getMovementVector() {
        return movementVector;
    }

    public float getRotation() {
        return rotation;
    }

    public static Direction getDirectionFromRotation(float rotation) {
        Direction direction = Direction.RIGHT;
        for (Direction dir : Direction.values()) {
            if (dir.getRotation() == rotation) {
                direction = dir;
            }
        }
        return direction;
    }

    public static GridPoint2 getMovementVectorFromRotation(float rotation) {
        Direction direction = Direction.RIGHT;
        for (Direction dir : Direction.values()) {
            if (dir.getRotation() == rotation) {
                direction = dir;
            }
        }
        return direction.getMovementVector();
    }
}
