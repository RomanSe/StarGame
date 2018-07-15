package ru.rosemenov.stargame.pools;


import ru.rosemenov.stargame.base.SpritesPool;
import ru.rosemenov.stargame.sprite.Bullet;

public class BulletPool extends SpritesPool<Bullet> {
    @Override
    protected Bullet newObject() {
        return new Bullet();
    }
}
