package ru.rosemenov.stargame.base;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import ru.rosemenov.stargame.math.Rect;

public class ProgressBar extends Rect {

    private final float MARGIN = 0.05f;
    private final Color border;
    private final Color filler;
    float relativeX;
    float relativeY;
    private float progress;


    public ProgressBar(float relativeX, float relativeY, float width, float height, Color border, Color filler) {
        this.relativeX = relativeX;
        this.relativeY = relativeY;
        this.border = border;
        this.filler = filler;
        setWidth(width);
        setHeight(height);
    }


    public void resize(Rect worldBounds) {
        pos.x = worldBounds.getWidth() * (relativeX - 0.5f);
        pos.y = worldBounds.getHeight() * (relativeY - 0.5f);
    }


    public void setProgress(float progress) {
        if (progress < 0 || progress > 1)
            throw new IllegalArgumentException("Progress should be between 0 and 1");
        this.progress = progress;
    }

    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.set(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(border);
        shapeRenderer.rect(getLeft(), getBottom(), getWidth(), getHeight());
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(filler);
        float margin = getHeight() * MARGIN;
        float fillerWidth = (getWidth() - 2 * margin) * progress;
        shapeRenderer.rect(getLeft() + margin, getBottom() + margin, fillerWidth, getHeight() - 2 * margin);
    }

}
