package com.ficuno.creature;

import com.badlogic.gdx.Game;

public class Creature extends Game {
	@Override
	public void create () {setScreen(new MainMenuScreen(this));}
}
