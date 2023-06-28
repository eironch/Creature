package com.ficuno.creature;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.TimeUtils;

public class GameScreen extends CreatureScreen {
    Main main;
    Renderer renderer;
    float currentTime;

    public GameScreen(Game game) {super(game);}

    @Override
    public void show() {
        main = new Main();
        renderer = new Renderer(main);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        currentTime = TimeUtils.nanosToMillis(TimeUtils.nanoTime());

        main.update(delta);
        renderer.render(delta);
    }

    @Override
    public void resize(int width, int height) {

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