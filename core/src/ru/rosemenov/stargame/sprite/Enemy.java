package ru.rosemenov.stargame.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.rosemenov.stargame.base.Ship;
import ru.rosemenov.stargame.math.Rect;
import ru.rosemenov.stargame.pools.BulletPool;
import ru.rosemenov.stargame.pools.ExplosionPool;


public class Enemy extends Ship {

    private MainShip mainShip;
    private State state;
    private Vector2 v0 = new Vector2();
    private Vector2 descentV = new Vector2(0, -0.15f);

    public Enemy(BulletPool bulletPool, Rect worldBounds, ExplosionPool explosionPool, MainShip mainShip, Sound sound) {
        super(bulletPool, worldBounds, explosionPool, sound);
        this.v.set(v0);
        this.state = State.DESCENT;
        this.v.set(descentV);
        this.mainShip = mainShip;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        switch (state) {
            case DESCENT:
                break;
            case FIGHT:
                reloadTimer += delta;
                if (reloadTimer >= reloadInterval) {
                    reloadTimer = 0f;
                    shoot();
                }
                break;
        }
    }

    @Override
    protected void checkAndHandleBounds() {
        switch (state) {
            case DESCENT:
                if (getTop() <= worldBounds.getTop()) {
                    v.set(v0);
                    state = State.FIGHT;
                }
                break;
            case FIGHT:
                if (getBottom() < worldBounds.getBottom()) {
                    mainShip.damage(bulletDamage);
                    destroy();
                }
                break;
        }
    }

    public void set(
            TextureRegion[] regions,
            Vector2 v0,
            TextureRegion bulletRegion,
            float bulletHeight,
            float bulletVY,
            int bulletDamage,
            float reloadInterval,
            float height,
            int hp
    ) {
        this.regions = regions;
        this.v0.set(v0);
        this.bulletRegion = bulletRegion;
        this.bulletHeight = bulletHeight;
        this.bulletV.set(0f, bulletVY);
        this.bulletDamage = bulletDamage;
        this.reloadInterval = reloadInterval;
        setHeightProportion(height);
        reloadTimer = reloadInterval;
        this.v.set(descentV);
        this.state = State.DESCENT;
        this.hp = hp;
    }

    public boolean isBulletCollision(Rect bullet) {
        return !(bullet.getRight() < getLeft()
                || bullet.getLeft() > getRight()
                || bullet.getBottom() > getTop()
                || bullet.getTop() < pos.y);
    }

    private enum State {DESCENT, FIGHT}


}
