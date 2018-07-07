package ru.rosemenov.stargame.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.rosemenov.stargame.base.Base2DScreen;
import ru.rosemenov.stargame.math.Rnd;
import ru.rosemenov.stargame.sprite.Background;
import ru.rosemenov.stargame.sprite.Star;

class MainScreen extends Base2DScreen {

    private Background background;
    private Texture bg;
    private TextureAtlas atlas;
    private static final int STAR_NUMBER = 40;

    public MainScreen(Game game) {
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

    }
}
