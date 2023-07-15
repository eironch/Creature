package com.ficuno.PSYKIES;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class Creature extends Game {
	static int WIDTH = 1280;
	static int HEIGHT = 720;
	final static int maxPsykies = 15;
	@Override
	public void create () {
		if (Gdx.graphics.getWidth() % WIDTH != 0 && Gdx.graphics.getWidth() > WIDTH){
			WIDTH = 1280;
		}

		setScreen(new MainMenuScreen(this));
	}
}
