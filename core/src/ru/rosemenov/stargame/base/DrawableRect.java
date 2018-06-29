package ru.rosemenov.stargame.base;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import ru.rosemenov.stargame.math.Rect;

public class DrawableRect extends Rect{
    private final Batch batch;
    private Texture texture;

    public DrawableRect(Batch batch, String texture, float x, float y, float halfWidth, float halfHeight) {
        super(x, y, halfWidth, halfHeight);
        this.texture = new Texture(texture);
        this.batch = batch;

    }

    public void draw() {
        batch.draw(texture, getX() - getHalfWidth(), getY() - getHalfHeight(), getWidth(), getHeight());
    }

    public void dispose() {
        texture.dispose();
    }


}
