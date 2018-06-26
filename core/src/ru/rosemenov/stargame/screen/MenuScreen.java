package ru.rosemenov.stargame.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import ru.rosemenov.stargame.base.Base2DScreen;

/**
 * Экран меню
 */
public class MenuScreen extends Base2DScreen {
    private SpriteBatch batch;
    private Texture background;
    private Texture ship;
    private Vector2 velocity;
    private float speed = 0.5f;

    private Vector2 pos;
    int width;
    int height;
    int background_width;
    int background_height;


    public MenuScreen(Game game) {
        super(game);
        background = new Texture("space.jpg");
        ship = new Texture("starship.png");
        pos = new Vector2(ship.getWidth()/2, ship.getHeight()/2);
        velocity = new Vector2(0.0f, speed);
        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background, 0, 0, background_width, background_height);
        pos.add(velocity);
        batch.draw(ship, pos.x - ship.getWidth()/2, pos.y - ship.getHeight()/2);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
        super.dispose();
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        super.touchUp(screenX, screenY, pointer, button);
        velocity = getTouchVector(screenX, screenY).sub(pos).setLength(speed);
        return true;
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        this.width = width;
        this.height = height;
        background_width = Math.max(width, background.getWidth());
        background_height = Math.max(height, background.getHeight());
    }

    private Vector2 getTouchVector(int x, int y) {
        return new Vector2(x, height - y);
    }
}
