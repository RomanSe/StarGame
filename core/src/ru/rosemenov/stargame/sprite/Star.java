package ru.rosemenov.stargame.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.rosemenov.stargame.base.Base2DScreen;
import ru.rosemenov.stargame.base.MovableSprite;
import ru.rosemenov.stargame.math.Rect;
import ru.rosemenov.stargame.math.Rnd;

/**
 * Created by Alexey on 30.06.2018.
 */

public class Star extends MovableSprite {

    public Star(Base2DScreen screen, TextureRegion region, float vx, float vy, float height, float scale) {
        super(screen, region);
        v.set(vx, vy);
        setHeightProportion(height);
        this.scale = scale;
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        float posX = Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight());
        float posY = Rnd.nextFloat(worldBounds.getBottom(), worldBounds.getTop());
        pos.set(posX, posY);
    }

    protected void checkAndHandleBounds() {
        if (getRight() < world.worldBounds.getLeft()) setLeft(world.worldBounds.getRight());
        if (getLeft() > world.worldBounds.getRight()) setRight(world.worldBounds.getLeft());
        if (getTop() < world.worldBounds.getBottom()) setBottom(world.worldBounds.getTop());
        if (getBottom() > world.worldBounds.getTop()) setTop(world.worldBounds.getBottom());
    }
}
