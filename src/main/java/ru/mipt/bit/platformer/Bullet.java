package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;
import java.util.HashSet;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class Bullet implements Movable {
    private GridPoint2 coordinates;
    private GridPoint2 destinationCoordinates;
    private float movementProgress = 1f;
    private float rotation;
    private final Player tank;

    final int damage = 2;

    Bullet(Player tank, Level level) {
        this.tank = tank;
        this.rotation = tank.getRotation();
        this.destinationCoordinates = new GridPoint2(tank.getDestinationCoordinates()).add(Direction.getMovementVectorFromRotation(rotation));
        this.coordinates = new GridPoint2(this.destinationCoordinates);
    }

    @Override
    public boolean isMoving() {
        return isEqual(this.movementProgress, 1f);
    }

    public void moveFurther(Level level) {
        move(Direction.getDirectionFromRotation(rotation), level);
    }

    @Override
    public void move(Direction direction, Level level) {
        if (level.checkHasObstacle(coordinates)) {
            level.removeBullet(this);
            this.tank.removeBullet(this);
        }
        if (level.checkHasTank(coordinates) != null) {
            Player tank = level.checkHasTank(coordinates);
            tank.takeDamage(damage);
        }

        if (isMoving()) {
            GridPoint2 newCoordinates = new GridPoint2(coordinates).add(direction.getMovementVector());
            if (!level.checkHasObstacle(newCoordinates)) {
                destinationCoordinates = newCoordinates;
                movementProgress = 0f;
            } else {
                level.removeBullet(this);
                this.tank.removeBullet(this);
            }

            if (level.checkHasTank(newCoordinates) != null) {
                Player tank = level.checkHasTank(newCoordinates);
                tank.takeDamage(damage);
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
