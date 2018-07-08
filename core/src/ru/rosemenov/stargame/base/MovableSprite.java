package ru.rosemenov.stargame.base;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public abstract class MovableSprite extends Sprite {
    protected Vector2 v = new Vector2();


    public MovableSprite() {
    }

    public MovableSprite(Base2DScreen world, TextureRegion region) {
        super(world, region);
    }

    public MovableSprite(Base2DScreen world, TextureRegion region, int rows, int cols, int frames) {
        super(world, region, rows, cols, frames);
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
        checkAndHandleBounds();
    }

    protected abstract void checkAndHandleBounds();

}
