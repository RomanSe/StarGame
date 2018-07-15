package ru.rosemenov.stargame.pools;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.rosemenov.stargame.base.SpritesPool;
import ru.rosemenov.stargame.math.Rect;
import ru.rosemenov.stargame.sprite.Explosion;

/**
 * Created by Alexey on 14.07.2018.
 */

public class ExplosionPool extends SpritesPool<Explosion> {

    private Sound sound;
    private TextureRegion textureRegion;
    private Rect worldBounds;

    public ExplosionPool(Rect worldBounds, TextureAtlas atlas, Sound sound) {
        this.textureRegion = atlas.findRegion("explosion");
        this.sound = sound;
        this.worldBounds = worldBounds;
    }

    @Override
    protected Explosion newObject() {
        return new Explosion(worldBounds, textureRegion, 9, 9, 74, sound);
    }
}
