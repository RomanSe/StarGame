package ru.rosemenov.stargame.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;

import ru.rosemenov.stargame.base.Base2DScreen;
import ru.rosemenov.stargame.base.DrawableRect;
import ru.rosemenov.stargame.math.Rect;

import java.util.ArrayList;
import java.util.List;

/**
 * Экран меню
 */
public class MenuScreen extends Base2DScreen {

    DrawableRect ship;
    DrawableRect backgroung;
    List<DrawableRect> drawableRects = new ArrayList<DrawableRect>();


    public MenuScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        ship = new DrawableRect( batch, "starship.png", 0f, 0f, 5f, 5f);
        backgroung = new DrawableRect(batch, "space.jpg", 0f, 0f, WORLD_WIDTH/2, WORLD_HEIGHT/2);
        drawableRects.add(backgroung); //похоже, порядок отрисовки важен
        drawableRects.add(ship);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        for (DrawableRect obj: drawableRects) {
            obj.draw();
        }
        //backgroung.draw(); //WORLD_WIDTH, WORLD_HEIGHT
        batch.end();
    }

    @Override
    public void dispose() {
        ship.dispose();
        super.dispose();
    }

    @Override
    public void touchDown(Vector2 touch, int pointer) {
        super.touchDown(touch, pointer);
    }

    @Override
    public void touchUp(Vector2 touch, int pointer) {
        super.touchUp(touch, pointer);
        ship.setPos(this.touch);
    }

    @Override
    public void resize(Rect worldBounds) {
        backgroung.set(worldBounds);
    }


}
