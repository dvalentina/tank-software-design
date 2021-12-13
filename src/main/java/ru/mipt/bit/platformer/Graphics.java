package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Disposable;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Graphics implements Disposable {
    private final Batch batch;
    private final ShapeRenderer shapeRenderer = new ShapeRenderer();
    private final HashMap<String, Texture> textures;
    private final Level level;
    private final LevelGraphics levelGraphics;
    private final ObjectGraphicsInterface playerGraphics;
    private final ArrayList<ObjectGraphicsInterface> otherTanksGraphics = new ArrayList<>();
    private final ArrayList<ObjectGraphics> bulletsGraphics = new ArrayList<>();

    public Graphics(Level level) {
        this.level = level;
        batch = new SpriteBatch();
        textures = loadTextures();
        playerGraphics = new ObjectGraphics(textures.get("blueTank"), level.getPlayer());
        for (Player tank : level.getOtherTanks()) {
            ObjectGraphics otherTankGraphics = new ObjectGraphics(textures.get("blueTank"), tank);
            otherTanksGraphics.add(new ObjectLivesDecorator(otherTankGraphics));
        }
        levelGraphics = new LevelGraphics(level);
        levelGraphics.loadLevelTiles(batch);
    }

    private HashMap<String,Texture> loadTextures() {
        HashMap<String,Texture> textures = new HashMap<>();
        textures.put("blueTank", new Texture("images/tank_blue.png"));
        textures.put("bullet", new Texture("images/bullet.png"));
        return textures;
    }

    public void render() {
        levelGraphics.renderLevel(batch);
        batch.begin();
        levelGraphics.renderTrees(batch);

        playerGraphics.render(batch, level.getPlayer().getRotation());
        for (int i = 0; i < otherTanksGraphics.size(); i++) {
            otherTanksGraphics.get(i).render(batch, level.getOtherTanks().get(i).getRotation());
        }
        for (int i = 0; i < bulletsGraphics.size(); i++) {
            bulletsGraphics.get(i).render(batch, level.getBullets().get(i).getRotation());
        }
        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (ObjectGraphicsInterface otherTanksGraphic : otherTanksGraphics) {
            otherTanksGraphic.renderShape(shapeRenderer);
        }
        shapeRenderer.end();
    }

    public void calculateInterpolatedObjectScreenCoordinates() {
        TileMovement tileMovement = levelGraphics.getTileMovement();
        playerGraphics.calculateInterpolatedObjectScreenCoordinates(tileMovement);
        for (ObjectGraphicsInterface tankGraphics : otherTanksGraphics) {
            tankGraphics.calculateInterpolatedObjectScreenCoordinates(tileMovement);
        }
        for (ObjectGraphics bulletsGraphics : bulletsGraphics) {
            bulletsGraphics.calculateInterpolatedObjectScreenCoordinates(tileMovement);
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        levelGraphics.dispose();
        for(Texture texture : textures.values()) { texture.dispose(); }
    }

    public void addBullet() {
        List<Bullet> bullets = level.getBullets();
        bulletsGraphics.add(new ObjectGraphics(textures.get("bullet"), bullets.get(bullets.size()-1)));
    }

    public void removeBullet() {
        List<Bullet> bullets = level.getBullets();
        bulletsGraphics.removeIf(bulletGraphics -> !bullets.contains(bulletGraphics.getSource()));
    }

    public void removeTank() {
        List<Player> otherTanks = level.getOtherTanks();
        otherTanksGraphics.removeIf(otherTanksGraphics -> !otherTanks.contains(otherTanksGraphics.getSource()));
    }
}
