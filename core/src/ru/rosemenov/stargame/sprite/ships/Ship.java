package ru.rosemenov.stargame.sprite.ships;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

import ru.rosemenov.stargame.base.Base2DScreen;
import ru.rosemenov.stargame.base.MovableSprite;
import ru.rosemenov.stargame.math.Rnd;
import ru.rosemenov.stargame.utils.Regions;

public class Ship extends MovableSprite {
    private static final float BIG_SHIP_HEIGHT = 0.2f;
    private static final float MIDDLE_SHIP_HEIGHT = 0.2f;
    private static final float SMALL_SHIP_HEIGHT = 0.2f;
    private static final Vector2 v0 = new Vector2(0, -0.05f);
    private static final Vector2 v1 = new Vector2(0, -0.15f);
    private static List<Ship> shipPool = new ArrayList<>();
    protected ShipState state;


    public Ship(Base2DScreen world) {
        this.world = world;
        v.set(v0);
        state = ShipState.ACTIVE;
    }

    public static Ship getShip(Base2DScreen screen, TextureAtlas atlas) {
        Ship ship = getFromPool(screen);
        float dice = Rnd.nextFloat(0f, 1f);
        if (dice < 0.5f) {
            ship.initSmallShip(atlas);
        } else if (dice < 0.8f) {
            ship.initMiddleShip(atlas);
        } else {
            ship.initBigShip(atlas);
        }
        return ship;
    }

    private static Ship getFromPool(Base2DScreen screen) {
        System.out.println(shipPool.size());
        for (Ship ship : shipPool) {
            if (ship.state == ShipState.INACTIVE) {
                ship.state = ShipState.ACTIVE;
                ship.v.set(v0);
                return ship;
            }
        }
        Ship ship = new Ship(screen);
        shipPool.add(ship);
        return ship;

    }

    private void initBigShip(TextureAtlas atlas) {
        regions = Regions.split(atlas.findRegion("enemy2"), 1, 2, 2);
        setHeightProportion(BIG_SHIP_HEIGHT);
    }

    private void initMiddleShip(TextureAtlas atlas) {
        regions = Regions.split(atlas.findRegion("enemy1"), 1, 2, 2);
        setHeightProportion(MIDDLE_SHIP_HEIGHT);
    }

    private void initSmallShip(TextureAtlas atlas) {
        regions = Regions.split(atlas.findRegion("enemy0"), 1, 2, 2);
        setHeightProportion(SMALL_SHIP_HEIGHT);
    }

    @Override
    protected void checkAndHandleBounds() {
        if (state == ShipState.ACTIVE) {
            if (getTop() < world.worldBounds.getTop()) {
                v.set(v1);
            }
            if (getTop() < world.worldBounds.getBottom()) {
                state = ShipState.INACTIVE;
                setBottom(world.worldBounds.getTop());
                v.setZero();
            }
        }
        ;
    }


}
