package ru.mipt.bit.platformer;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Disposable;

public class Level implements Disposable {
    private TiledMap map;
    private Player player;
    private Tree treeObstacle;

    Level() {
        map = new TmxMapLoader().load("level.tmx");
        player = new Player(new GridPoint2(1, 1), 0f);
        treeObstacle = new Tree(new GridPoint2(1, 3));
    }

    @Override
    public void dispose() {
        map.dispose();
    }

    public TiledMap getMap() { return map; }

    public Player getPlayer() { return player; }

    public Tree getTreeObstacle() { return treeObstacle; }
}
