package ru.rosemenov.stargame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Game extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("space.jpg");
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		int width = Math.max(Gdx.graphics.getWidth(),background.getWidth());
		int height = Math.max(Gdx.graphics.getHeight(),background.getHeight());
		batch.draw(background, 0, 0, width, height);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		background.dispose();
	}
}
