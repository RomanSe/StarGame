package ru.rosemenov.stargame.sprite;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import java.util.HashMap;
import java.util.Map;

import ru.rosemenov.stargame.base.Base2DScreen;
import ru.rosemenov.stargame.base.MovableSprite;
import ru.rosemenov.stargame.math.Rect;


public class MainShip extends MovableSprite {

    private static final float SHIP_HEIGHT = 0.15f;
    private static final float BOTTOM_MARGIN = 0.05f;

    private Vector2 v0 = new Vector2(0.5f, 0f);

    private boolean pressedLeft;
    private boolean pressedRight;
    private static final float DELTA = 0.005f;
    private boolean pressed;
    private int pointer;
    private Map<Integer, Vector2> touchList;
    private Vector2 touch = new Vector2(0f, 0f);

    public MainShip(Base2DScreen screen, TextureAtlas atlas) {
        super(screen, atlas.findRegion("main_ship"), 1, 2, 2);
        touchList = new HashMap<>();
        setHeightProportion(SHIP_HEIGHT);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setBottom(worldBounds.getBottom() + BOTTOM_MARGIN);
    }

    @Override
    public void update(float delta) {
        if (pressed) {
            if (Math.abs(touch.x - pos.x) < DELTA) {
                stop();
            } else if (touch.x < pos.x) {
                moveLeft();
            } else {
                moveRight();
            }
        }
        super.update(delta);
    }

    protected void checkAndHandleBounds() {
        if (getRight() < world.worldBounds.getLeft()) setLeft(world.worldBounds.getRight());
        if (getLeft() > world.worldBounds.getRight()) setRight(world.worldBounds.getLeft());
    }

    public void keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = true;
                moveLeft();
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = true;
                moveRight();
                break;
        }
    }

    public void keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = false;
                if (pressedRight) {
                    moveRight();
                } else {
                    stop();
                }
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = false;
                if (pressedLeft) {
                    moveLeft();
                } else {
                    stop();
                }
                break;
        }
    }

    @Override
    public void touchDown(Vector2 touch, int pointer) {
        touchList.put(pointer, touch);
        System.out.println(pointer + " " + touchList.size() + " " + pressed);
        if (touchList.size() == 1) {
            this.pointer = pointer;
            this.touch.set(touchList.get(pointer));
            this.pressed = true;
        }
        for (Integer key : touchList.keySet()) {
            System.out.println(key + "=" + touchList.get(key));
        }
    }

    @Override
    public void touchUp(Vector2 touch, int pointer) {
        if (touchList.containsKey(pointer)) {
            touchList.remove(pointer);
        }
        if (touchList.isEmpty()) {
            this.pressed = false;
            stop();
        } else if (!touchList.containsKey(this.pointer)) {
            int nextKey = touchList.keySet().iterator().next();
            this.pointer = nextKey;
            this.touch.set(touchList.get(nextKey));
            this.pressed = true;
        }
    }

    private void updateTouch(int pointer) {
        this.pointer = pointer;
        this.touch.set(touchList.get(pointer));
        this.pressed = true;
    }

    @Override
    public void touchDragged(Vector2 touch, int pointer) {
        if (this.pointer == pointer) {
            this.touch.set(touch);
        }
        System.out.println(touch + " " + pointer);
    }

    private void moveLeft() {
        v.set(v0).rotate(180);
    }

    private void moveRight() {
        v.set(v0);
    }

    private void stop() {
        v.setZero();
    }
}
