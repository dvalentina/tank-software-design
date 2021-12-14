package ru.mipt.bit.platformer;

public interface State {
    void continueMovement(float deltaTime, float speed);
    boolean canShoot();
}
