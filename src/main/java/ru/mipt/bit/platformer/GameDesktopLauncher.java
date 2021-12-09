package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

public class GameDesktopLauncher implements ApplicationListener, Game {

    private static final float MOVEMENT_SPEED = 0.4f;

    private Level level;
    private Graphics graphics;
    private GameAiAdapter gameAiAdapter = new GameAiAdapter();

    @Override
    public void create() {
        /* Порт */

        LevelGenerator levelGenerator = new RandomLevelGenerator();
//        LevelGenerator levelGenerator = new FileLevelGenerator("src/test/resources/testLevel");
        level = levelGenerator.generateLevel();
        graphics = new Graphics(level);
    }

    @Override
    public void render() {
        clearScreen();
        calculateMovement();
        graphics.render();
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
    }

    private void calculateMovement() {
        /* Порт */

        Player player = level.getPlayer();
        ArrayList<Tree> treeObstacles = level.getTreeObstacles();
        ArrayList<Player> otherTanks = level.getOtherTanks();
        HashSet<GridPoint2> levelBorders = level.getBorders();

        movePlayerIfKeyPressed(player, treeObstacles, otherTanks, levelBorders);
//        moveOtherTanks(player, treeObstacles, otherTanks, levelBorders);
        gameAiAdapter.moveOtherTanks(player, treeObstacles, otherTanks, levelBorders);

        graphics.calculateInterpolatedPlayerScreenCoordinates();
        graphics.calculateInterpolatedOtherTanksScreenCoordinates();

        player.continueMovement(getTimeSinceLastRender(), MOVEMENT_SPEED);
        for (Player tank : otherTanks) {
            tank.continueMovement(getTimeSinceLastRender(), MOVEMENT_SPEED);
        }
    }

    private void movePlayerIfKeyPressed(Player player, ArrayList<Tree> treeObstacles, ArrayList<Player> otherTanks, HashSet<GridPoint2> levelBorders) {
        /* Адаптер, Application (use-case) */

        if (Gdx.input.isKeyPressed(UP) || Gdx.input.isKeyPressed(W)) {
            player.move(Direction.UP, treeObstacles, otherTanks, levelBorders);
        }
        if (Gdx.input.isKeyPressed(LEFT) || Gdx.input.isKeyPressed(A)) {
            player.move(Direction.LEFT, treeObstacles, otherTanks, levelBorders);
        }
        if (Gdx.input.isKeyPressed(DOWN) || Gdx.input.isKeyPressed(S)) {
            player.move(Direction.DOWN, treeObstacles, otherTanks, levelBorders);
        }
        if (Gdx.input.isKeyPressed(RIGHT) || Gdx.input.isKeyPressed(D)) {
            player.move(Direction.RIGHT, treeObstacles, otherTanks, levelBorders);
        }
    }

    @Override
    public void moveOtherTanks(Player player, ArrayList<Tree> treeObstacles, ArrayList<Player> otherTanks, HashSet<GridPoint2> levelBorders) {
        /* Application (use-case) */

        for (Player tank : otherTanks) {
            ArrayList<Player> newOtherTanks = new ArrayList<>(otherTanks);
            newOtherTanks.remove(tank);
            newOtherTanks.add(player);
            Direction direction = Direction.values()[new Random().nextInt(Direction.values().length)];
            tank.move(direction, treeObstacles, newOtherTanks, levelBorders);
        }
    }

    private float getTimeSinceLastRender() { return Gdx.graphics.getDeltaTime(); }

    @Override
    public void resize(int width, int height) {
        // do not react to window resizing
    }

    @Override
    public void pause() {
        // game doesn't get paused
    }

    @Override
    public void resume() {
        // game doesn't get paused
    }

    @Override
    public void dispose() {
        // dispose of all the native resources (classes which implement com.badlogic.gdx.utils.Disposable)
        graphics.dispose();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}
