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

import java.util.HashMap;

import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class Graphics implements Disposable {
    private Batch batch;
    private MapRenderer levelRenderer;
    private TileMovement tileMovement;
    private HashMap<String, Texture> textures;
    private Level level;
    private ObjectGraphics playerGraphics;
    private ObjectGraphics treeObstacleGraphics;

    public Graphics(Level level) {
        this.level = level;
        batch = new SpriteBatch();
        
        textures = loadTextures();
        playerGraphics = new ObjectGraphics(textures.get("blueTank"));
        treeObstacleGraphics = new ObjectGraphics(textures.get("greenTree"));

        loadLevelTiles();
    }

    private void loadLevelTiles() {
        levelRenderer = createSingleLayerMapRenderer(level.getMap(), batch);
        TiledMapTileLayer groundLayer = getSingleLayer(level.getMap());
        tileMovement = new TileMovement(groundLayer, Interpolation.smooth);
        moveRectangleAtTileCenter(groundLayer, treeObstacleGraphics.getRectangle(), level.getTreeObstacle().getCoordinates());
    }

    private HashMap<String,Texture> loadTextures() {
        // Texture decodes an image file and loads it into GPU memory, it represents a native resource
        HashMap<String,Texture> textures = new HashMap<String,Texture>();
        textures.put("blueTank", new Texture("images/tank_blue.png"));
        textures.put("greenTree", new Texture("images/greenTree.png"));
        return textures;
    }

    public void render() {
        // render each tile of the level
        levelRenderer.render();

        // start recording all drawing commands
        batch.begin();

        playerGraphics.render(batch, level.getPlayer().getRotation());
        treeObstacleGraphics.render(batch, 0f);

        // submit all drawing requests
        batch.end();
    }

    public void calculateInterpolatedPlayerScreenCoordinates() {
        Player player = level.getPlayer();
        tileMovement.moveRectangleBetweenTileCenters(
                playerGraphics.getRectangle(),
                player.getCoordinates(),
                player.getDestinationCoordinates(),
                player.getMovementProgress()
        );
    }

    @Override
    public void dispose() {
        batch.dispose();
        for(Texture texture : textures.values()) { texture.dispose(); }
    }
}
