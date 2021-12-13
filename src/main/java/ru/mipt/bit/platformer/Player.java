package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class Player implements Movable {
    // player current position coordinates on level 10x8 grid (e.g. x=0, y=1)
    private GridPoint2 coordinates;
    // which tile the player want to go next
    private GridPoint2 destinationCoordinates;
    private float movementProgress = 1f;
    private float rotation;

    private final ArrayList<Bullet> bullets = new ArrayList<>();

    private int healthPoints = 3;

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
        if (!didShootRecently()) {
            Bullet bullet = new Bullet(this, level);
            level.addBullet(bullet);
            bullets.add(bullet);
        }
    }

    private boolean didShootRecently() {
        for (Bullet bullet : bullets) {
            if (bullet.getCoordinates().equals(new GridPoint2(destinationCoordinates).add(Direction.getMovementVectorFromRotation(rotation)))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void continueMovement(float deltaTime, float speed) {
        movementProgress = continueProgress(movementProgress, deltaTime, speed);
        if (isMoving()) {
            // record that the player has reached his/her destination
            coordinates = destinationCoordinates;
        }
    }

    public void takeDamage(int damage) {
        healthPoints -= damage;
    }

    public int getHealthPoints() {
        return healthPoints;
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
}
