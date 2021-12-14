package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import ru.mipt.bit.platformer.commands.*;
import ru.mipt.bit.platformer.controller.BotsController;
import ru.mipt.bit.platformer.controller.BulletsController;
import ru.mipt.bit.platformer.controller.PlayerController;
import ru.mipt.bit.platformer.controller.UserInterfaceController;
import ru.mipt.bit.platformer.events.*;

import java.util.*;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

public class GameDesktopLauncher implements ApplicationListener {

    private static final float MOVEMENT_SPEED = 0.4f;

    private Level level;
    private Graphics graphics;

    private final CommandsExecutor commandsExecutor = new CommandsExecutor();
    private BotsController botsController;
    private PlayerController playerController;
    private BulletsController bulletsController;
    private UserInterfaceController userInterfaceController;

    private EventManager events;
    private BulletAddedListener bulletAddedListener;
    private BulletRemovedListener bulletRemovedListener;
    private TankRemovedListener tankRemovedListener;

    @Override
    public void create() {
        events = new EventManager();
        LevelGenerator levelGenerator = new RandomLevelGenerator();
//        LevelGenerator levelGenerator = new FileLevelGenerator("src/test/resources/testLevel");
        level = levelGenerator.generateLevel(events);
        graphics = new Graphics(level);

        botsController = new BotsController(commandsExecutor, level);
//        botsController = new BotsAiController(commandsExecutor, level);
        playerController = new PlayerController(commandsExecutor, level);
        bulletsController = new BulletsController(commandsExecutor, level);
        userInterfaceController = new UserInterfaceController(commandsExecutor, graphics);

        bulletAddedListener = new BulletAddedListener(graphics);
        bulletRemovedListener = new BulletRemovedListener(graphics);
        tankRemovedListener = new TankRemovedListener(graphics);

        events.subscribe(EventTypes.BULLET_ADDED, bulletAddedListener);
        events.subscribe(EventTypes.BULLET_REMOVED, bulletRemovedListener);
        events.subscribe(EventTypes.TANK_REMOVED, tankRemovedListener);
    }

    @Override
    public void render() {
        clearScreen();
        createCommands();
        calculateMovement();
        graphics.render();
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
    }

    private void createCommands() {
        playerController.generateCommands();
        botsController.generateCommands();
        bulletsController.generateCommands();
        userInterfaceController.generateCommands();

        commandsExecutor.executeCommands();
    }

    private void calculateMovement() {
        level.checkTanksHealthPoints();

        Tank player = level.getPlayer();
        List<Tank> otherTanks = level.getOtherTanks();
        List<Bullet> bullets = level.getBullets();

        graphics.calculateInterpolatedObjectScreenCoordinates();

        player.continueMovement(getTimeSinceLastRender(), MOVEMENT_SPEED);
        for (Tank tank : otherTanks) {
            tank.continueMovement(getTimeSinceLastRender(), MOVEMENT_SPEED);
        }
        for (Bullet bullet : bullets) {
            bullet.continueMovement(getTimeSinceLastRender(), MOVEMENT_SPEED);
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
