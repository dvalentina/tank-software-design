package ru.mipt.bit.platformer;

public interface Killable {
    int getHealthPoints();
    int getMaxLives();
    void takeDamage(int damage);
}
