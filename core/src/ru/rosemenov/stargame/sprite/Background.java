package ru.rosemenov.stargame.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.rosemenov.stargame.base.Base2DScreen;
import ru.rosemenov.stargame.base.MovableSprite;
import ru.rosemenov.stargame.math.Rect;


public class Background extends MovableSprite {
    private static final Vector2 V = new Vector2(0f, -0.1f);

    public Background(Base2DScreen world, TextureRegion region) {
        super(world, region);
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
        if (getTop() <= world.worldBounds.getBottom()) setBottom(world.worldBounds.getTop());
    }


}
