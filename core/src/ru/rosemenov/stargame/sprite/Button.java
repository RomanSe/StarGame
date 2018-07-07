package ru.rosemenov.stargame.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.rosemenov.stargame.base.ActionListener;
import ru.rosemenov.stargame.base.Sprite;
import ru.rosemenov.stargame.math.Rect;

public class Button extends Sprite {

    private float relativeX;  //положение относительно левого края, от 0 до 1
    private float relativeY; //положение относительно низа, от 0 до 1

    private static final float TOUCHED_SIZE = 0.9f;
    private static final float UNTOUCHED_SIZE = 1f;
    private static final float TOUCHING_SPEED = 1.8f;
    private boolean pressed = false;
    private int pointer;

    public void addActionListener(ActionListener actionListener) {
        this.actionListener = actionListener;
    }

    private ActionListener actionListener;

    public Button(TextureRegion region, float relativeX, float relativeY, float height) {
        super(region);
        setHeightProportion(height);
        this.relativeX = relativeX;
        this.relativeY = relativeY;
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        pos.x = worldBounds.getWidth() * (relativeX - 0.5f);
        pos.y = worldBounds.getHeight() * (relativeY - 0.5f);
    }

    @Override
    public void touchDown(Vector2 touch, int pointer) {
        if (pressed || !isMe(touch)) {
            return;
        }
        this.pointer = pointer;
        this.pressed = true;
    }

    @Override
    public void touchUp(Vector2 touch, int pointer) {
        if (this.pointer != pointer || !pressed) {
            return;
        }
        if (isMe(touch)) {
            actionListener.actionPerformed(this);
            return;
        }
        this.pressed = false;
        this.scale = 1f;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        float scale = getScale();
        if (pressed) {
            if (scale > TOUCHED_SIZE) {
                setScale(scale - delta * TOUCHING_SPEED);
            } else {
                setScale(TOUCHED_SIZE);
            }
        } else {
            if (scale < UNTOUCHED_SIZE) {
                setScale(scale + delta * TOUCHING_SPEED);
            } else {
                setScale(UNTOUCHED_SIZE);
            }
        }
    }
}
