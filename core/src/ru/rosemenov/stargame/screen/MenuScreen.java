package ru.rosemenov.stargame.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ru.rosemenov.stargame.base.Base2DScreen;

/**
 * Экран меню
 */
public class MenuScreen extends Base2DScreen {
    SpriteBatch batch;

    public MenuScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        batch = new SpriteBatch();
        //game.setScreen(new GameScreen(game));
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        //int width = Math.max(Gdx.graphics.getWidth(),background.getWidth());
        //int height = Math.max(Gdx.graphics.getHeight(),background.getHeight());
        //batch.draw(background, 0, 0, width, height);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        super.dispose();
    }
}
