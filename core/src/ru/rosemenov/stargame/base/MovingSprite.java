package ru.rosemenov.stargame.base;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.rosemenov.stargame.math.Rect;

public abstract class MovingSprite extends Sprite {
    protected Vector2 v = new Vector2();


    public MovingSprite() {
    }

    public MovingSprite(Rect worldBounds, TextureRegion region) {
        super(worldBounds, region);
    }

    public MovingSprite(Rect worldBounds, TextureRegion region, int rows, int cols, int frames) {
        super(worldBounds, region, rows, cols, frames);
    }


    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
        checkAndHandleBounds();
    }

    protected abstract void checkAndHandleBounds();

}
