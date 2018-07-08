package ru.rosemenov.stargame.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.rosemenov.stargame.base.ActionListener;
import ru.rosemenov.stargame.base.Base2DScreen;
import ru.rosemenov.stargame.base.Timer;
import ru.rosemenov.stargame.math.Rnd;
import ru.rosemenov.stargame.sprite.Background;
import ru.rosemenov.stargame.sprite.MainShip;
import ru.rosemenov.stargame.sprite.Star;
import ru.rosemenov.stargame.sprite.ships.Ship;

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

        Background background1 = new Background(this, new TextureRegion(bg));
        background1.setBottom(0.5f);
        addToWorld(background1);

        Background background2 = new Background(this, new TextureRegion(bg));
        background2.setBottom(-0.5f);
        addToWorld(background2);

        TextureAtlas atlas = new TextureAtlas("textures/mainAtlas.tpack");
        resources.add(atlas);
        TextureRegion starRegion = atlas.findRegion("star");
        for (int i = 0; i < STAR_NUMBER; i++) {
            float vx = Rnd.nextFloat(-0.005f, 0.005f);
            float vy = Rnd.nextFloat(-0.15f, -0.05f);
            float scale = vy / 0.5f;
            addToWorld(new Star(this, starRegion, vx, vy, 0.01f, scale));
        }
        Base2DScreen screen = this;
        timers.add(new Timer(4, new ActionListener() {
            public void actionPerformed(Object src) {
                Ship ship = Ship.getShip(screen, atlas);
                ship.setBottom(worldBounds.getTop());
                ship.pos.x = Rnd.nextFloat(worldBounds.getLeft() + ship.getHalfWidth(), worldBounds.getRight() - ship.getHalfWidth());
                if (!sprites.contains(ship)) sprites.add(ship);
            }
        }));
        mainShip = new MainShip(screen, atlas);
        addToWorld(mainShip);
    }
}
