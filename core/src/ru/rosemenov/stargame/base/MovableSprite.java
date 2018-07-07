package ru.rosemenov.stargame.base;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.rosemenov.stargame.math.Rect;

public abstract class MovableSprite extends Sprite {
    protected Vector2 v = new Vector2();
    protected Rect worldBounds;


    public MovableSprite(TextureRegion region) {
        super(region);
    }

    public MovableSprite(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
    }

    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
        checkAndHandleBounds();
    }

    protected abstract void checkAndHandleBounds();

}
