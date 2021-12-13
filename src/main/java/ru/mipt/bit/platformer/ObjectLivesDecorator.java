package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.util.TileMovement;

public class ObjectLivesDecorator implements ObjectGraphicsInterface{
    private final ObjectGraphics objectGraphics;

    public ObjectLivesDecorator(ObjectGraphics objectGraphics) {
        this.objectGraphics = objectGraphics;
    }

    public ObjectGraphics getObjectGraphics() {
        return objectGraphics;
    }

    @Override
    public TextureRegion getGraphics() {
        return objectGraphics.getGraphics();
    }

    @Override
    public Object getSource() {
        return objectGraphics.getSource();
    }

    @Override
    public Rectangle getRectangle() {
        return objectGraphics.getRectangle();
    }

    @Override
    public void calculateInterpolatedObjectScreenCoordinates(TileMovement tileMovement) {
        objectGraphics.calculateInterpolatedObjectScreenCoordinates(tileMovement);
    }

    @Override
    public void render(Batch batch, float rotation) {
        objectGraphics.render(batch, rotation);
    }

    @Override
    public void renderShape(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.RED);
        TextureRegion texture = objectGraphics.getGraphics();
        Rectangle rectangle = objectGraphics.getRectangle();

        float healthWidth = texture.getRegionWidth() * 0.25f;
        float healthHeight = texture.getRegionHeight() * 0.75f;

        Killable tank = (Killable)objectGraphics.getSource();
        float widthInitial = 50f;
        float multiplier = (float)tank.getHealthPoints() / (float)tank.getMaxLives();
        float width = widthInitial * multiplier;

        shapeRenderer.rect(rectangle.x + healthWidth, rectangle.y + healthHeight, (int)width, 10);
    }
}
