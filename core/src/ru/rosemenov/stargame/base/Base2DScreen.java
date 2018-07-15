package ru.rosemenov.stargame.base;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

import java.util.ArrayList;
import java.util.List;

import ru.rosemenov.stargame.math.MatrixUtils;
import ru.rosemenov.stargame.math.Rect;

/**
 * Базовый класс экрана
 */

public class Base2DScreen implements Screen, InputProcessor {
    protected SpriteBatch batch;
    public static final float WORLD_HEIGHT = 1f;

    protected Game game;
    private Rect screenBounds; // границы экрана в пикселях
    public Rect worldBounds; // границы проекции мировых координат
    private Rect glBounds; // gl-левские координаты
    protected List<Sprite> sprites;
    protected List<Timer> timers;
    protected List<Disposable> resources;

    protected Matrix4 worldToGl;
    protected Matrix3 screenToWorld;

    private Vector2 touch = new Vector2();

    public Base2DScreen() {
        this.screenBounds = new Rect();
        this.worldBounds = new Rect();
        this.glBounds = new Rect(0, 0, 1f, 1f);
        this.worldToGl = new Matrix4();
        this.screenToWorld = new Matrix3();
        Gdx.input.setInputProcessor(this);
        batch = new SpriteBatch();
        sprites = new ArrayList<>();
        resources = new ArrayList<>();
        timers = new ArrayList<>();
    }

    public Base2DScreen(Game game) {
        this();
        this.game = game;
    }

    protected void register(Sprite sprite) {
        sprites.add(sprite);
    }

    @Override
    public void show() {
        System.out.println("show");
    }


    @Override
    public void resize(int width, int height) {
        screenBounds.setSize(width, height);
        screenBounds.setLeft(0);
        screenBounds.setBottom(0);

        float aspect = width / (float) height;
        worldBounds.setHeight(WORLD_HEIGHT);
        worldBounds.setWidth(WORLD_HEIGHT * aspect);
        MatrixUtils.calcTransitionMatrix(worldToGl, worldBounds, glBounds);
        batch.setProjectionMatrix(worldToGl);
        MatrixUtils.calcInputMatrix(screenToWorld, screenBounds, worldBounds);
        resize(worldBounds);
    }


    @Override
    public void pause() {
        System.out.println("pause");
    }

    @Override
    public void resume() {
        System.out.println("resume");
    }

    @Override
    public void hide() {
        System.out.println("hide");
        dispose();
    }

    public void resize(Rect worldBounds) {
        for (Sprite sprite : sprites) {
            sprite.resize(worldBounds);
        }
    }

    @Override
    public void dispose() {
        for (Disposable resource : resources) {
            resource.dispose();
        }
    }

    @Override
    public void render(float delta) {
        update(delta);
        checkCollisions();
        deleteAllDestroyed();
        draw();
    }

    public void deleteAllDestroyed() {

    }

    private void checkCollisions() {
    }

    public void draw() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        for (Sprite sprite : sprites) {
            sprite.draw(batch);
        }
        batch.end();
    }

    public void update(float delta) {
        for (Timer timer : timers) {
            timer.update(delta);
        }
        for (Sprite sprite : sprites) {
            sprite.update(delta);
        }
    }

    //----------------------------------- Events ------------------------------

    @Override
    public boolean keyDown(int keycode) {
        for (Sprite sprite : sprites) {
            sprite.keyDown(keycode);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        for (Sprite sprite : sprites) {
            sprite.keyUp(keycode);
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        System.out.println("keyTyped character=" + character);
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touch.set(screenX, screenY).mul(screenToWorld);
        for (Sprite sprite : sprites) {
            sprite.touchDown(touch, pointer);
        }
        touchDown(touch, pointer);
        return false;
    }

    public void touchDown(Vector2 touch, int pointer) {

    }


    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        touch.set(screenX, screenY).mul(screenToWorld);
        for (Sprite sprite : sprites) {
            sprite.touchUp(touch, pointer);
        }
        touchUp(touch, pointer);
        return false;
    }

    public void touchUp(Vector2 touch, int pointer) {

    }


    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        touch.set(screenX, screenY).mul(screenToWorld);
        for (Sprite sprite : sprites) {
            sprite.touchDragged(touch, pointer);
        }
        touchDragged(touch, pointer);
        return false;
    }

    public void touchDragged(Vector2 touch, int pointer) {
        System.out.println("touchDragged X=" + touch.x + " Y=" + touch.y);
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        System.out.println("scrolled amount=" + amount);
        return false;
    }


}
