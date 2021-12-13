package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.util.TileMovement;

public interface ObjectGraphicsInterface {
    Object getSource();
    Rectangle getRectangle();
    TextureRegion getGraphics();
    void calculateInterpolatedObjectScreenCoordinates(TileMovement tileMovement);
    void render(Batch batch, float rotation);
    void renderShape(ShapeRenderer shapeRenderer);
}
