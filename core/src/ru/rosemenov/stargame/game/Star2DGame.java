package ru.rosemenov.stargame.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

import ru.rosemenov.stargame.screen.MenuScreen;

/**
 * Основной класс игры
 */

public class Star2DGame extends Game {
    @Override
    public void create() {
        setScreen(new MenuScreen(this));
    }
}
