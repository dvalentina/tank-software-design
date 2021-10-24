package ru.mipt.bit.platformer;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;
import static ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled;

public class ObjectGraphics {
    private final TextureRegion graphics;

    public Rectangle getRectangle() {
        return rectangle;
    }

    private final Rectangle rectangle;

    public ObjectGraphics(Texture texture) {
        this.graphics = new TextureRegion(texture);
        this.rectangle = new Rectangle(createBoundingRectangle(graphics));
    }

    public void render(Batch batch, float rotation) {
        drawTextureRegionUnscaled(batch, graphics, rectangle, rotation);
    }
}
