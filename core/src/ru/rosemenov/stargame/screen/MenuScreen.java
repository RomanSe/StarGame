package ru.rosemenov.stargame.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

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
    private Button playButton;
    private Button exitButton;
    private Texture bg;
    private TextureAtlas atlas;
    private static final int STAR_NUMBER = 100;

    public MenuScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        resources.add(bg);
        background = new Background(new TextureRegion(bg));
        sprites.add(background);

        atlas = new TextureAtlas("textures/menuAtlas.tpack");
        resources.add(atlas);
        TextureRegion starRegion = atlas.findRegion("star");
        for (int i = 0; i < STAR_NUMBER; i++) {
            float vx = Rnd.nextFloat(-0.005f, 0.005f);
            float vy = Rnd.nextFloat(-0.5f, -0.1f);
            float scale = vy / 0.5f;
            sprites.add(new Star(starRegion, vx, vy, 0.01f, scale));
        }
        TextureRegion btPlay = atlas.findRegion("btPlay");
        playButton = new Button(btPlay, 0.25f, 0.25f, 0.20f);
        playButton.addActionListener(src -> game.setScreen(new MainScreen(game)));
        sprites.add(playButton);
        TextureRegion btExit = atlas.findRegion("btExit");
        exitButton = new Button(btExit, 0.75f, 0.225f, 0.16f);
        exitButton.addActionListener(src -> Gdx.app.exit());
        sprites.add(exitButton);
    }


}
