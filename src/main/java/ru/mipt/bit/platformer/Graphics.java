package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.ArrayList;
import java.util.HashMap;


public class Graphics implements Disposable {
    private Batch batch;
    private HashMap<String, Texture> textures;
    private Level level;
    private LevelGraphics levelGraphics;
    private ObjectGraphics playerGraphics;
    private ArrayList<ObjectGraphics> otherTanksGraphics = new ArrayList<>();

    public Graphics(Level level) {
        this.level = level;
        batch = new SpriteBatch();
        textures = loadTextures();
        playerGraphics = new ObjectGraphics(textures.get("blueTank"), level.getPlayer());
        for (Player tank : level.getOtherTanks()) {
            otherTanksGraphics.add(new ObjectGraphics(textures.get("blueTank"), tank));
        }
        levelGraphics = new LevelGraphics(level);

        levelGraphics.loadLevelTiles(batch);
    }

    private HashMap<String,Texture> loadTextures() {
        HashMap<String,Texture> textures = new HashMap<>();
        textures.put("blueTank", new Texture("images/tank_blue.png"));
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
        batch.end();
    }

    public void calculateInterpolatedObjectScreenCoordinates() {
        TileMovement tileMovement = levelGraphics.getTileMovement();
        playerGraphics.calculateInterpolatedObjectScreenCoordinates(tileMovement);
        for (ObjectGraphics tankGraphics : otherTanksGraphics) {
            tankGraphics.calculateInterpolatedObjectScreenCoordinates(tileMovement);
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        levelGraphics.dispose();
        for(Texture texture : textures.values()) { texture.dispose(); }
    }
}
