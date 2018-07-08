package ru.rosemenov.stargame.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.rosemenov.stargame.math.Rect;
import ru.rosemenov.stargame.utils.Regions;

/**
 * Спрайт
 */

public abstract class Sprite extends Rect implements Updatable {

    protected float angle;
    protected float scale = 1f;
    protected TextureRegion[] regions;
    protected int frame;
    protected Base2DScreen world;


    public Sprite(Base2DScreen world, TextureRegion region) {
        if (region == null) {
            throw new NullPointerException();
        }
        regions = new TextureRegion[1];
        regions[0] = region;
        this.world = world;
    }

    public Sprite(Base2DScreen world, TextureRegion region, int rows, int cols, int frames) {
        if (region == null) {
            throw new NullPointerException();
        }
        regions = Regions.split(region, rows, cols, frames);
        this.world = world;
    }

    protected Sprite() {
    }

    public void draw(SpriteBatch batch) {
        batch.draw(
                regions[frame], // текущий регион
                getLeft(), getBottom(), // точка отрисовки
                halfWidth, halfHeight, // точка вращения
                getWidth(), getHeight(), // ширина и высота
                scale, scale, // масштаб по оси x и y
                angle // угол вращения
        );
    }

    public void setHeightProportion(float height) {
        setHeight(height);
        float aspect = regions[frame].getRegionWidth() / (float) regions[frame].getRegionHeight();
        setWidth(height * aspect);
    }

    public void resize(Rect worldBounds) {

    }


    public void touchDown(Vector2 touch, int pointer) {
    }

    public void touchDragged(Vector2 touch, int pointer) {
    }

    public void touchUp(Vector2 touch, int pointer) {

    }

    public void keyDown(int keycode) {
    }

    public void keyUp(int keycode) {
    }

    public void update(float delta) {

    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

}