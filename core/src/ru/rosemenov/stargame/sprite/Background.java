package ru.rosemenov.stargame.sprite;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
    public void draw(SpriteBatch batch) {
        batch.draw(
                regions[frame], // текущий регион
                getLeft(), getBottom(), // точка отрисовки
                halfWidth, halfHeight, // точка вращения
                getWidth(), getHeight(), // ширина и высота
                scale, scale, // масштаб по оси x и y
                angle // угол вращения
        );
        batch.draw(
                regions[frame], // текущий регион
                getLeft(), getTop(), // точка отрисовки
                halfWidth, halfHeight, // точка вращения
                getWidth(), getHeight(), // ширина и высота
                scale, scale, // масштаб по оси x и y
                angle // угол вращения
        );

    }

    @Override
    protected void checkAndHandleBounds() {
        if (getTop() <= worldBounds.getBottom()) setBottom(worldBounds.getBottom());
    }


}
