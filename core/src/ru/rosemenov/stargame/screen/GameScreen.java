package ru.rosemenov.stargame.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import java.util.List;

import ru.rosemenov.stargame.base.ActionListener;
import ru.rosemenov.stargame.base.Base2DScreen;
import ru.rosemenov.stargame.base.Font;
import ru.rosemenov.stargame.base.ProgressBar;
import ru.rosemenov.stargame.base.Sprite;
import ru.rosemenov.stargame.math.Rect;
import ru.rosemenov.stargame.math.Rnd;
import ru.rosemenov.stargame.pools.BulletPool;
import ru.rosemenov.stargame.pools.EnemyPool;
import ru.rosemenov.stargame.pools.ExplosionPool;
import ru.rosemenov.stargame.sprite.Background;
import ru.rosemenov.stargame.sprite.Bullet;
import ru.rosemenov.stargame.sprite.Button;
import ru.rosemenov.stargame.sprite.Enemy;
import ru.rosemenov.stargame.sprite.MainShip;
import ru.rosemenov.stargame.sprite.Star;
import ru.rosemenov.stargame.utils.EnemiesEmitter;

public class GameScreen extends Base2DScreen {
    private static final int STAR_COUNT = 56;
    private static final float FONT_SIZE = 0.02f;
    private static final float MARGINE = 0.01f;
    private Font font;

    ;
    private State state;

    private Button playButton;
    private Background background;
    private ProgressBar progressBar;
    private TextureAtlas progressAtlas;
    private Texture bg;
    private Star star[];
    private TextureAtlas atlas;
    private Sprite gameOverLabel;
    private BulletPool bulletPool;

    private MainShip mainShip;
    private EnemyPool enemyPool;
    private ExplosionPool explosionPool;
    private EnemiesEmitter enemiesEmitter;
    private Music music;
    private Sound explosionSound;
    private Sound bulletSound;
    private Sound laserSound;

    private int frags;
    private StringBuilder sbFrags = new StringBuilder();
    private StringBuilder sbHp = new StringBuilder();
    private StringBuilder sbStage = new StringBuilder();



    @Override
    public void show() {
        super.show();
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
        bulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        laserSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        music.setLooping(true);
        music.play();
        bg = new Texture("textures/bg.png");
        background = new Background(this.worldBounds, new TextureRegion(bg));
        background.setBottom(-0.5f);
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        TextureRegion starRegion = atlas.findRegion("star");
        star = new Star[STAR_COUNT];
        for (int i = 0; i < star.length; i++) {
            float vx = Rnd.nextFloat(-0.005f, 0.005f);
            float vy = Rnd.nextFloat(-0.15f, -0.05f);
            float scale = vy / 0.5f;
            star[i] = new Star(this.worldBounds, starRegion, vx, vy, 0.02f, scale);
        }
        bulletPool = new BulletPool();
        explosionPool = new ExplosionPool(this.worldBounds, atlas, explosionSound);
        mainShip = new MainShip(this.worldBounds, atlas, bulletPool, explosionPool, laserSound);
        enemyPool = new EnemyPool(bulletPool, worldBounds, explosionPool, mainShip, bulletSound);
        enemiesEmitter = new EnemiesEmitter(worldBounds, enemyPool, atlas);
        font = new Font("font/font.fnt", "font/font.png");
        font.setWorldSize(FONT_SIZE);
        //
        progressBar = new ProgressBar(0.5f, 0.98f, 0.4f, FONT_SIZE, Color.WHITE, Color.RED);
        //
        this.state = State.GAME;
        //----------
        gameOverLabel = new Button(this.worldBounds, atlas.findRegion("message_game_over"), 0.5f, 0.66f, 0.05f);
        playButton = new Button(this.worldBounds, atlas.findRegion("button_new_game"), 0.5f, 0.33f, 0.03f);
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(Object src) {
                newGame();
            }
        });
    }

    public GameScreen(Game game) {
        super(game);
    }

    private void printInfo() {
        sbFrags.setLength(0);
        sbHp.setLength(0);
        sbStage.setLength(0);
        font.draw(batch, sbFrags.append("Frags: ").append(frags), worldBounds.getLeft(), worldBounds.getTop() - MARGINE);
        //font.draw(batch, sbHp.append("HP: ").append(mainShip.getHp()), worldBounds.pos.x, worldBounds.getTop(), Align.center);
        font.draw(batch, sbStage.append("Stage: ").append(enemiesEmitter.getStage()), worldBounds.getRight(), worldBounds.getTop() - MARGINE, Align.right);

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        checkCollisions();
        deleteAllDestroyed();
        draw();
    }

    public void update(float delta) {
        switch (state) {
            case GAME:
                for (int i = 0; i < star.length; i++) {
                    star[i].update(delta);
                }
                background.update(delta);
                //background2.update(delta);
                mainShip.update(delta);
                bulletPool.updateActiveSprites(delta);
                enemyPool.updateActiveSprites(delta);
                explosionPool.updateActiveSprites(delta);
                enemiesEmitter.generateEnemies(delta, frags);
                break;
            case GAME_OVER:
                explosionPool.updateActiveSprites(delta);
                break;
        }
    }

    public void draw() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (int i = 0; i < star.length; i++) {
            star[i].draw(batch);
        }
        if (state == State.GAME) {
            mainShip.draw(batch);
        }
        bulletPool.drawActiveSprites(batch);
        enemyPool.drawActiveSprites(batch);
        explosionPool.drawActiveSprites(batch);
        if (state == State.GAME_OVER) {
            gameOverLabel.draw(batch);
            playButton.draw(batch);
        }
        printInfo();
        batch.end();
        shapeRenderer.begin();
        progressBar.setProgress(mainShip.getDemage());
        progressBar.draw(shapeRenderer);
        shapeRenderer.end();

    }

    public void checkCollisions() {
        List<Enemy> enemyList = enemyPool.getActiveObjects();
        List<Bullet> bulletList = bulletPool.getActiveObjects();
        for (Enemy enemy : enemyList) {
            if (enemy.isDestroyed()) {
                continue;
            }
            float minDist = enemy.getHalfWidth() + mainShip.getHalfWidth();
            if (enemy.pos.dst2(mainShip.pos) < minDist * minDist) {
                enemy.destroy();
                mainShip.destroy();
                gameOver();
                return;
            }

            for (Bullet bullet : bulletList) {
                if (bullet.getOwner() != mainShip || bullet.isDestroyed()) {
                    continue;
                }
                if (enemy.isBulletCollision(bullet)) {
                    enemy.damage(bullet.getDamage());
                    bullet.destroy();
                    if (enemy.isDestroyed()) {
                        frags++;
                        break;
                    }
                }
            }
        }

        for (Bullet bullet : bulletList) {
            if (bullet.isDestroyed() || bullet.getOwner() == mainShip) {
                continue;
            }
            if (mainShip.isBulletCollision(bullet)) {
                mainShip.damage(bullet.getDamage());
                bullet.destroy();
            }
        }
        if (mainShip.isDestroyed()) {
            gameOver();
        }

    }

    public void deleteAllDestroyed() {
        bulletPool.freeAllDestroyedActiveSprites();
        enemyPool.freeAllDestroyedActiveSprites();
        explosionPool.freeAllDestroyedActiveSprites();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        //background2.resize(worldBounds);
        for (int i = 0; i < star.length; i++) {
            star[i].resize(worldBounds);
        }
        mainShip.resize(worldBounds);
        gameOverLabel.resize(worldBounds);
        playButton.resize(worldBounds);
        progressBar.resize(worldBounds);
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        bulletPool.dispose();
        enemyPool.dispose();
        explosionPool.dispose();
        music.dispose();
        explosionSound.dispose();
        bulletSound.dispose();
        laserSound.dispose();
        font.dispose();
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        mainShip.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        mainShip.keyUp(keycode);
        return false;
    }

    @Override
    public void touchDown(Vector2 touch, int pointer) {
        switch (state) {
            case GAME:
                mainShip.touchDown(touch, pointer);
                break;
            case GAME_OVER:
                playButton.touchDown(touch, pointer);
                break;
        }
    }

    @Override
    public void touchUp(Vector2 touch, int pointer) {
        switch (state) {
            case GAME:
                mainShip.touchUp(touch, pointer);
                break;
            case GAME_OVER:
                playButton.touchUp(touch, pointer);
                break;
        }
    }

    @Override
    public void touchDragged(Vector2 touch, int pointer) {
        mainShip.touchDragged(touch, pointer);
    }

    public void newGame() {
        frags = 0;
        mainShip.reset();
        bulletPool.freeAll();
        enemyPool.freeAll();
        explosionPool.freeAll();
        music.play();
        state = State.GAME;
    }

    public void gameOver() {
        state = State.GAME_OVER;
        music.stop();
    }

    private enum State {GAME, GAME_OVER}
}
