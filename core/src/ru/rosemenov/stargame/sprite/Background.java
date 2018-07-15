package ru.rosemenov.stargame.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.rosemenov.stargame.base.MovingSprite;
import ru.rosemenov.stargame.math.Rect;


public class Background extends MovingSprite {
    private static final Vector2 V = new Vector2(0f, -0.1f);

    public Background(Rect worldBounds, TextureRegion region) {
        super(worldBounds, region);
        scale = 1.01f;
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(worldBounds.getHeight());
        //pos.set(worldBounds.pos);
        v = V;
    }

    @Override
    protected void checkAndHandleBounds() {
        if (getTop() <= worldBounds.getBottom()) setBottom(worldBounds.getTop());
    }


}
