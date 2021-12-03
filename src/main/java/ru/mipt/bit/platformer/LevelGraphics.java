package ru.mipt.bit.platformer;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Disposable;

public class LevelGraphics implements Disposable {
    private TiledMap map;

    public LevelGraphics() {
        map = new TmxMapLoader().load("level.tmx");
    }

    public TiledMap getMap() { return map; }

    @Override
    public void dispose() {
        map.dispose();
    }
}
