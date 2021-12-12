package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.utils.Disposable;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.ArrayList;

import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class LevelGraphics implements Disposable {
    private TiledMap map;
    private Level level;
    private MapRenderer levelRenderer;
    private TileMovement tileMovement;
    private Texture treeTexture = new Texture("images/greenTree.png");

    private final ArrayList<ObjectGraphics> treeObstaclesGraphics = new ArrayList<>();

    public LevelGraphics(Level level) {
        this.level = level;
        map = new TmxMapLoader().load("level.tmx");
        for (Tree tree : level.getTreeObstacles()) {
            treeObstaclesGraphics.add(new ObjectGraphics(treeTexture, tree));
        }
    }

    public void loadLevelTiles(Batch batch) {
        levelRenderer = createSingleLayerMapRenderer(map, batch);
        TiledMapTileLayer groundLayer = getSingleLayer(map);
        tileMovement = new TileMovement(groundLayer, Interpolation.smooth);
        for (int i = 0; i < treeObstaclesGraphics.size(); i++) {
            moveRectangleAtTileCenter(groundLayer, treeObstaclesGraphics.get(i).getRectangle(), level.getTreeObstacles().get(i).getCoordinates());
        }
    }

    public void renderLevel(Batch batch) {
        levelRenderer.render();
    }

    public void renderTrees(Batch batch) {
        for (ObjectGraphics tree : treeObstaclesGraphics) {
            tree.render(batch, 0f);
        }
    }

    public TiledMap getMap() { return map; }

    @Override
    public void dispose() {
        map.dispose();
        treeTexture.dispose();
    }

    public TileMovement getTileMovement() {
        return tileMovement;
    }
}
