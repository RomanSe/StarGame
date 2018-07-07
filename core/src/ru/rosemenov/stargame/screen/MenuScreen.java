package ru.rosemenov.stargame.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.rosemenov.stargame.base.ActionListener;
import ru.rosemenov.stargame.base.Base2DScreen;
import ru.rosemenov.stargame.math.Rnd;
import ru.rosemenov.stargame.sprite.Background;
import ru.rosemenov.stargame.sprite.Button;
import ru.rosemenov.stargame.sprite.Star;

/**
 * Экран меню
 */

public class MenuScreen extends Base2DScreen {

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

        Background background1 = new Background(new TextureRegion(bg));
        background1.setBottom(0.5f);
        addToWorld(background1);

        Background background2 = new Background(new TextureRegion(bg));
        background2.setBottom(-0.5f);
        addToWorld(background2);


        atlas = new TextureAtlas("textures/menuAtlas.tpack");
        resources.add(atlas);
        TextureRegion starRegion = atlas.findRegion("star");
        for (int i = 0; i < STAR_NUMBER; i++) {
            float vx = Rnd.nextFloat(-0.005f, 0.005f);
            float vy = Rnd.nextFloat(-0.5f, -0.1f);
            float scale = vy / 0.5f;
            addToWorld(new Star(starRegion, vx, vy, 0.01f, scale));
        }
        TextureRegion btPlay = atlas.findRegion("btPlay");
        playButton = new Button(btPlay, 0.25f, 0.25f, 0.20f);
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(Object src) {
                game.setScreen(new GameScreen(game));
            }
        });
        addToWorld(playButton);
        TextureRegion btExit = atlas.findRegion("btExit");
        exitButton = new Button(btExit, 0.75f, 0.225f, 0.16f);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(Object src) {
                Gdx.app.exit();
            }
        });
        addToWorld(exitButton);
    }


}
