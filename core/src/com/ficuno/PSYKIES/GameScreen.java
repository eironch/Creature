package com.ficuno.PSYKIES;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class GameScreen extends CreatureScreen {
    Main main;
    final static int NEITHER = 0;
    final static int LOST = 1;
    final static int WON = 2;
    final static int WAIT = 3;
    static int gameState  = NEITHER;
    static boolean retry = false;

    public GameScreen(Game game) {super(game);}

    @Override
    public void show() {
        main = new Main();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (!retry){
            main.update(delta);
        } else {
            gameState = NEITHER;
            game.setScreen(new MainMenuScreen(game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
        main.renderer.viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
    }
}