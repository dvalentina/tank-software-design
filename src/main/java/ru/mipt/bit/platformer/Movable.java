package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;
import java.util.HashSet;

public interface Movable {
    boolean isMoving();
    void move(Direction direction, ArrayList<Tree> trees, ArrayList<Player> tanks, HashSet<GridPoint2> levelBorders);
    void continueMovement(float deltaTime, float speed);
    float getRotation();
    float getMovementProgress();
    GridPoint2 getCoordinates();
    GridPoint2 getDestinationCoordinates();
}
