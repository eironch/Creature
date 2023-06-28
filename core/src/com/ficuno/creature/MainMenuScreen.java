package com.ficuno.creature;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MainMenuScreen extends CreatureScreen {
    SpriteBatch batch;
    BitmapFont font;
    Texture mainMenuTexture;
    GlyphLayout glyphLayout;
    float w;

    public MainMenuScreen(Game game){
        super(game);

        font = new BitmapFont();
        font.setColor(0.36f,0.82f,0.912f, 1.0f);
        batch = new SpriteBatch();
        glyphLayout = new GlyphLayout();
        batch.getProjectionMatrix().setToOrtho2D(0, 0, 320, 180);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        glyphLayout.setText(font,"Welcome to Frostfell.");
        w = glyphLayout.width;
        font.draw(batch, glyphLayout, (Main.WIDTH - w)/2, 30);
        glyphLayout.setText(font,"Touch to Begin Trials.");
        w = glyphLayout.width;
        font.draw(batch, glyphLayout, (Main.WIDTH - w)/2, 90);
        batch.end();


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
