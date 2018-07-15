package ru.rosemenov.stargame.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.rosemenov.stargame.base.MovingSprite;
import ru.rosemenov.stargame.math.Rect;
import ru.rosemenov.stargame.math.Rnd;

/**
 * Created by Alexey on 30.06.2018.
 */

public class Star extends MovingSprite {

    public Star(Rect worldBounds, TextureRegion region, float vx, float vy, float height, float scale) {
        super(worldBounds, region);
        v.set(vx, vy);
        setHeightProportion(height);
        this.scale = scale;
    }

    @Override
    public void resize(Rect worldBounds) {
        float posX = Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight());
        float posY = Rnd.nextFloat(worldBounds.getBottom(), worldBounds.getTop());
        pos.set(posX, posY);
    }

    protected void checkAndHandleBounds() {
        if (getRight() < worldBounds.getLeft()) setLeft(worldBounds.getRight());
        if (getLeft() > worldBounds.getRight()) setRight(worldBounds.getLeft());
        if (getTop() < worldBounds.getBottom()) setBottom(worldBounds.getTop());
        if (getBottom() > worldBounds.getTop()) setTop(worldBounds.getBottom());
    }
}
