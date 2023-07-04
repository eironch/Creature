package com.ficuno.creature;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MainMenuScreen extends CreatureScreen {
    SpriteBatch batch;
    BitmapFont font;
    OrthographicCamera cam;
    GlyphLayout glyphLayout;
    float w;
    final static float GAME_WORLD_WIDTH = 100;
    final static float GAME_WORLD_HEIGHT = 50;
    final static float aspectRatio = (float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight();

    private final Viewport viewport;
    public MainMenuScreen(Game game){
        super(game);

        font = new BitmapFont();
        font.setColor(0.36f,0.82f,0.912f, 1.0f);
        batch = new SpriteBatch();
        glyphLayout = new GlyphLayout();

        cam = new OrthographicCamera();
        viewport = new ExtendViewport(GAME_WORLD_WIDTH * aspectRatio, GAME_WORLD_HEIGHT, cam);
        cam.translate(GAME_WORLD_WIDTH/2, GAME_WORLD_HEIGHT/2);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        cam.update();
        batch.begin();
        batch.setProjectionMatrix(cam.combined);

        glyphLayout.setText(font,"Welcome to Frostfell.");
        w = glyphLayout.width;
        font.draw(batch, glyphLayout, (GAME_WORLD_WIDTH - w)/2, 30);
        glyphLayout.setText(font,"Touch to Begin Trials.");
        w = glyphLayout.width;
        font.draw(batch, glyphLayout, (GAME_WORLD_HEIGHT - w)/2, 90);
        batch.end();

        game.setScreen(new GameScreen(game));
        dispose();

        if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(game));
            dispose();
        }

    }

    @Override
    public void hide (){
        Gdx.app.debug("Frostfell", "dispose main menu");
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
