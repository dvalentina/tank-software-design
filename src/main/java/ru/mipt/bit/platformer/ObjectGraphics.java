package ru.mipt.bit.platformer;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.util.TileMovement;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;
import static ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled;

public class ObjectGraphics implements ObjectGraphicsInterface{
    private final TextureRegion graphics;
    private final Object source;
    private final Rectangle rectangle;

    public ObjectGraphics(Texture texture, Object object) {
        this.graphics = new TextureRegion(texture);
        this.rectangle = new Rectangle(createBoundingRectangle(graphics));
        this.source = object;
    }

    @Override
    public TextureRegion getGraphics() {
        return graphics;
    }

    @Override
    public Object getSource() {
        return source;
    }

    @Override
    public Rectangle getRectangle() {
        return rectangle;
    }

    @Override
    public void calculateInterpolatedObjectScreenCoordinates(TileMovement tileMovement) {
        tileMovement.moveRectangleBetweenTileCenters(
                rectangle,
                ((Movable)source).getCoordinates(),
                ((Movable)source).getDestinationCoordinates(),
                ((Movable)source).getMovementProgress()
        );
    }

    @Override
    public void render(Batch batch, float rotation) {
        drawTextureRegionUnscaled(batch, graphics, rectangle, rotation);
    }

    @Override
    public void renderShape(ShapeRenderer shapeRenderer) {
        // do nothing
    }
}
