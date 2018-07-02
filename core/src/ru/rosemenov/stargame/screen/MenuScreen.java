package ru.rosemenov.stargame.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

import ru.rosemenov.stargame.base.Base2DScreen;
import ru.rosemenov.stargame.base.Sprite;
import ru.rosemenov.stargame.math.Rect;
import ru.rosemenov.stargame.math.Rnd;
import ru.rosemenov.stargame.sprite.Background;
import ru.rosemenov.stargame.sprite.Button;
import ru.rosemenov.stargame.sprite.Star;

/**
 * Экран меню
 */

public class MenuScreen extends Base2DScreen {

    private Background background;
    private Button okButton;
    private Button exitButton;
    private Texture bg;
    private List<Star> stars;
    private TextureAtlas atlas;


    public MenuScreen(Game game) {
        super(game);
        stars = new ArrayList<>();
        System.out.println("menu");
    }

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        background = new Background(new TextureRegion(bg));
        atlas = new TextureAtlas("textures/menuAtlas.tpack");
        TextureRegion starRegion = atlas.findRegion("star");
        for (int i = 0; i < 100; i++) {
            float vx = Rnd.nextFloat(-0.005f, 0.005f);
            float vy = Rnd.nextFloat(-0.5f, -0.1f);
            float scale = vy / 0.5f;
            stars.add(new Star(starRegion, vx,  vy, 0.01f, scale));
        }
        TextureRegion btPlay = atlas.findRegion("btPlay");
        okButton = new Button(btPlay, 0.25f, 0.25f, 0.20f);
        TextureRegion btExit = atlas.findRegion("btExit");
        exitButton = new Button(btExit, 0.75f, 0.225f, 0.16f);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
    }

    public void update(float delta) {
        for (Star star: stars) {
            star.update(delta);
        }
        okButton.update(delta);
        exitButton.update(delta);
    }

    public void draw() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (Star star: stars) {
            star.draw(batch);
        }
        okButton.draw(batch);
        exitButton.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        super.dispose();
    }

    @Override
    public void touchDown(Vector2 touch, int pointer) {
        super.touchDown(touch, pointer);
        okButton.touchDown(touch, pointer);
        exitButton.touchDown(touch, pointer);
    }

    @Override
    public void touchUp(Vector2 touch, int pointer) {
        super.touchUp(touch, pointer);
        okButton.touchUp(touch, pointer);
        exitButton.touchUp(touch, pointer);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        exitButton.resize(worldBounds);
        okButton.resize(worldBounds);
        for (Star star: stars) {
            star.resize(worldBounds);
        }

    }
}
