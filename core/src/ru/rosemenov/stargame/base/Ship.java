package ru.rosemenov.stargame.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.rosemenov.stargame.math.Rect;
import ru.rosemenov.stargame.pools.BulletPool;
import ru.rosemenov.stargame.pools.ExplosionPool;
import ru.rosemenov.stargame.sprite.Bullet;
import ru.rosemenov.stargame.sprite.Explosion;


public abstract class Ship extends MovingSprite {

    private static final float DAMAGE_ANIMATE_INTERVAL = 0.1f;
    protected final Vector2 bulletV = new Vector2();
    protected Rect worldBounds;

    protected BulletPool bulletPool;
    protected ExplosionPool explosionPool;
    protected TextureRegion bulletRegion;
    protected float bulletHeight;
    protected int bulletDamage;
    protected float reloadInterval;
    protected float reloadTimer;
    protected int hp;
    protected Sound shootSound;
    private float damageAnimateTimer = DAMAGE_ANIMATE_INTERVAL;

    public Ship(BulletPool bulletPool, Rect worldBounds, ExplosionPool explosionPool, Sound sound) {
        this.bulletPool = bulletPool;
        this.worldBounds = worldBounds;
        this.explosionPool = explosionPool;
        this.shootSound = sound;
    }

    public Ship(Rect worldBounds, TextureRegion region, int rows, int cols, int frames, Sound sound) {
        super(worldBounds, region, rows, cols, frames);
        this.shootSound = sound;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        damageAnimateTimer += delta;
        if (damageAnimateTimer >= DAMAGE_ANIMATE_INTERVAL) {
            frame = 0;
        }
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
    }

    protected void shoot() {
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, pos, bulletV, bulletHeight, worldBounds, bulletDamage);
        shootSound.play();
    }

    private void boom() {
        Explosion explosion = explosionPool.obtain();
        explosion.set(getHeight() * (float) 1.5, pos);
    }

    public void damage(int damage) {
        frame = 1;
        damageAnimateTimer = 0f;
        hp -= damage;
        if (hp <= 0) {
            hp = 0;
            destroy();
        }
    }

    @Override
    public void destroy() {
        boom();
        super.destroy();
    }

    public int getHp() {
        return hp;
    }

}
