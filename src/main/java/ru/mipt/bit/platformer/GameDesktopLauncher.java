package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.math.GridPoint2;
import org.lwjgl.system.CallbackI;
import ru.mipt.bit.platformer.commands.*;
import ru.mipt.bit.platformer.events.BulletAddedListener;
import ru.mipt.bit.platformer.events.EventManager;
import ru.mipt.bit.platformer.events.EventTypes;

import java.util.ArrayDeque;
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
    private CommandsExecutor commandsExecutor = new CommandsExecutor();

    private EventManager events;
    private BulletAddedListener bulletAddedListener;

    @Override
    public void create() {
        events = new EventManager();
        LevelGenerator levelGenerator = new RandomLevelGenerator();
//        LevelGenerator levelGenerator = new FileLevelGenerator("src/test/resources/testLevel");
        level = levelGenerator.generateLevel(events);
        graphics = new Graphics(level);
        bulletAddedListener = new BulletAddedListener(graphics);
        events.subscribe(EventTypes.BULLET_ADDED, bulletAddedListener);
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
        Player player = level.getPlayer();
        ArrayList<Player> otherTanks = level.getOtherTanks();

        commandsExecutor.addCommand(InputHandler.handlePlayerInput(player, level));
        commandsExecutor.addCommandQueue(generateOtherTanksCommands(level));
//        commandsExecutor.addCommandQueue(gameAiAdapter.generateOtherTanksCommands(level));

        commandsExecutor.executeCommands();

        graphics.calculateInterpolatedObjectScreenCoordinates();

        player.continueMovement(getTimeSinceLastRender(), MOVEMENT_SPEED);
        for (Player tank : otherTanks) {
            tank.continueMovement(getTimeSinceLastRender(), MOVEMENT_SPEED);
        }
    }

    @Override
    public ArrayDeque<Command> generateOtherTanksCommands(Level level) {
        ArrayDeque<Command> commands = new ArrayDeque<>();
        for (Player tank : level.getOtherTanks()) {
            Direction direction = Direction.values()[new Random().nextInt(Direction.values().length)];

            switch (direction) {
                case UP:
                    commands.add(new MoveUpCommand(tank, level));
                    break;
                case LEFT:
                    commands.add(new MoveLeftCommand(tank, level));
                    break;
                case DOWN:
                    commands.add(new MoveDownCommand(tank, level));
                    break;
                case RIGHT:
                    commands.add(new MoveRightCommand(tank, level));
                    break;
            }
        }
        return commands;
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
