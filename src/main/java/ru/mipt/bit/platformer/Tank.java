package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class Tank implements Movable, Killable {
    private State state;
    // player current position coordinates on level 10x8 grid (e.g. x=0, y=1)
    private GridPoint2 coordinates;
    // which tile the player want to go next
    private GridPoint2 destinationCoordinates;
    private float movementProgress = 1f;
    private float rotation;

    private final ArrayList<Bullet> bullets = new ArrayList<>();

    private final int maxLives = 5;
    private int healthPoints = maxLives;

    Tank(GridPoint2 initialCoordinates, float rotation) {
        this.destinationCoordinates = initialCoordinates;
        this.coordinates = new GridPoint2(this.destinationCoordinates);
        this.rotation = rotation;
        this.state = new LightState();
    }

    private void changeState(State newState) {
        this.state = newState;
    }

    @Override
    public boolean isMoving() {
        return isEqual(this.movementProgress, 1f);
    }

    @Override
    public void move(Direction direction, Level level) {
        if (isMoving()) {
            // check potential player destination for collision with obstacles
            GridPoint2 newCoordinates = new GridPoint2(coordinates).add(direction.getMovementVector());
            if (!level.checkHasObstacle(newCoordinates)) {
                destinationCoordinates = newCoordinates;
                movementProgress = 0f;
            }
            rotation = direction.getRotation();
        }
    }

    public void shoot(Level level) {
        if (state.canShoot()) {
            Bullet bullet = new Bullet(this, level);
            level.addBullet(bullet);
            bullets.add(bullet);
        }
    }

    @Override
    public void continueMovement(float deltaTime, float speed) {
        state.continueMovement(deltaTime, speed);
    }

    @Override
    public void takeDamage(int damage) {
        healthPoints -= damage;
        if ((healthPoints > 1) && (healthPoints <= 3)) {
            changeState(new MediumState());
        } else if (healthPoints <= 1) {
            changeState(new HeavyState());
        }
    }

    @Override
    public int getHealthPoints() {
        return healthPoints;
    }

    @Override
    public int getMaxLives() {
        return maxLives;
    }

    public void removeBullet(Bullet bullet) {
        bullets.remove(bullet);
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

    private class LightState implements State {
        @Override
        public void continueMovement(float deltaTime, float speed) {
            movementProgress = continueProgress(movementProgress, deltaTime, speed);
            if (isMoving()) {
                // record that the player has reached his/her destination
                coordinates = destinationCoordinates;
            }
        }

        @Override
        public boolean canShoot() {
            for (Bullet bullet : bullets) {
                if (bullet.getCoordinates().equals(new GridPoint2(destinationCoordinates).add(Direction.getMovementVectorFromRotation(rotation)))) {
                    return false;
                }
            }
            return true;
        }
    }

    private class MediumState implements State {
        @Override
        public void continueMovement(float deltaTime, float speed) {
            // moves 2 times slower
            final float slowerSpeed = speed * 2;
            movementProgress = continueProgress(movementProgress, deltaTime, slowerSpeed);
            if (isMoving()) {
                // record that the player has reached his/her destination
                coordinates = destinationCoordinates;
            }
        }

        @Override
        public boolean canShoot() {
            for (Bullet bullet : bullets) {
                if (bullet.getCoordinates().equals(new GridPoint2(destinationCoordinates).add(Direction.getMovementVectorFromRotation(rotation)))) {
                    return false;
                }
            }
            return true;
        }
    }

    private class HeavyState implements State {
        @Override
        public void continueMovement(float deltaTime, float speed) {
            // moves 3 times slower
            final float slowerSpeed = speed * 3;
            movementProgress = continueProgress(movementProgress, deltaTime, slowerSpeed);
            if (isMoving()) {
                // record that the player has reached his/her destination
                coordinates = destinationCoordinates;
            }
        }

        @Override
        public boolean canShoot() {
            return false;
        }
    }
}
