package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.utils.Disposable;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.ArrayList;
import java.util.HashMap;

import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class Graphics implements Disposable {
    private Batch batch;
    private MapRenderer levelRenderer;
    private TileMovement tileMovement;
    private HashMap<String, Texture> textures;
    private Level level;
    private LevelGraphics levelGraphics;
    private ObjectGraphics playerGraphics;
    private ArrayList<ObjectGraphics> treeObstaclesGraphics = new ArrayList<ObjectGraphics>();
    private ArrayList<ObjectGraphics> otherTanksGraphics = new ArrayList<>();

    public Graphics(Level level) {
        this.level = level;
        batch = new SpriteBatch();
        textures = loadTextures();
        playerGraphics = new ObjectGraphics(textures.get("blueTank"));
        for (int i = 0; i < level.getTreeObstacles().size(); i++) {
            treeObstaclesGraphics.add(new ObjectGraphics(textures.get("greenTree")));
        }
        for (int i = 0; i < level.getOtherTanks().size(); i++) {
            otherTanksGraphics.add(new ObjectGraphics(textures.get("blueTank")));
        }
        levelGraphics = new LevelGraphics();

        loadLevelTiles();
    }

    private void loadLevelTiles() {
        /* Адаптер */
        levelRenderer = createSingleLayerMapRenderer(levelGraphics.getMap(), batch);
        TiledMapTileLayer groundLayer = getSingleLayer(levelGraphics.getMap());
        tileMovement = new TileMovement(groundLayer, Interpolation.smooth);
        for (int i = 0; i < treeObstaclesGraphics.size(); i++) {
            moveRectangleAtTileCenter(groundLayer, treeObstaclesGraphics.get(i).getRectangle(), level.getTreeObstacles().get(i).getCoordinates());
        }
        for (int i = 0; i < otherTanksGraphics.size(); i++) {
            moveRectangleAtTileCenter(groundLayer, otherTanksGraphics.get(i).getRectangle(), level.getOtherTanks().get(i).getCoordinates());
        }
    }

    private HashMap<String,Texture> loadTextures() {
        // Texture decodes an image file and loads it into GPU memory, it represents a native resource
        HashMap<String,Texture> textures = new HashMap<String,Texture>();
        textures.put("blueTank", new Texture("images/tank_blue.png"));
        textures.put("greenTree", new Texture("images/greenTree.png"));
        return textures;
    }

    public void render() {
        /* Адаптер */

        // render each tile of the level
        levelRenderer.render();

        // start recording all drawing commands
        batch.begin();

        playerGraphics.render(batch, level.getPlayer().getRotation());
        for (ObjectGraphics tree : treeObstaclesGraphics) {
            tree.render(batch, 0f);
        }
        for (int i = 0; i < otherTanksGraphics.size(); i++) {
            otherTanksGraphics.get(i).render(batch, level.getOtherTanks().get(i).getRotation());
        }

        // submit all drawing requests
        batch.end();
    }

    public void calculateInterpolatedPlayerScreenCoordinates() {
        /* Адаптер */

        Player player = level.getPlayer();
        tileMovement.moveRectangleBetweenTileCenters(
                playerGraphics.getRectangle(),
                player.getCoordinates(),
                player.getDestinationCoordinates(),
                player.getMovementProgress()
        );
    }

    public void calculateInterpolatedOtherTanksScreenCoordinates() {
        /* Адаптер */

        ArrayList<Player> otherTanks = level.getOtherTanks();
        for (int i = 0; i < otherTanks.size(); i++) {
            tileMovement.moveRectangleBetweenTileCenters(
                    otherTanksGraphics.get(i).getRectangle(),
                    otherTanks.get(i).getCoordinates(),
                    otherTanks.get(i).getDestinationCoordinates(),
                    otherTanks.get(i).getMovementProgress()
            );
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        levelGraphics.dispose();
        for(Texture texture : textures.values()) { texture.dispose(); }
    }
}
