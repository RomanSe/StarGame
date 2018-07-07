package ru.rosemenov.stargame.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.rosemenov.stargame.base.Base2DScreen;
import ru.rosemenov.stargame.math.Rnd;
import ru.rosemenov.stargame.sprite.Background;
import ru.rosemenov.stargame.sprite.MainShip;
import ru.rosemenov.stargame.sprite.Star;

class GameScreen extends Base2DScreen {

    private static final int STAR_NUMBER = 40;
    private MainShip mainShip;

    public GameScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        Texture bg = new Texture("textures/bg.png");
        resources.add(bg);

        Background background1 = new Background(new TextureRegion(bg));
        background1.setBottom(0.5f);
        addToWorld(background1);

        Background background2 = new Background(new TextureRegion(bg));
        background2.setBottom(-0.5f);
        addToWorld(background2);

        TextureAtlas atlas = new TextureAtlas("textures/mainAtlas.tpack");
        resources.add(atlas);
        TextureRegion starRegion = atlas.findRegion("star");
        for (int i = 0; i < STAR_NUMBER; i++) {
            float vx = Rnd.nextFloat(-0.005f, 0.005f);
            float vy = Rnd.nextFloat(-0.5f, -0.1f);
            float scale = vy / 0.5f;
            addToWorld(new Star(starRegion, vx, vy, 0.01f, scale));
        }
        mainShip = new MainShip(atlas);
        addToWorld(mainShip);
    }
}
