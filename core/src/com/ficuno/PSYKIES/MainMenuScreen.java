package com.ficuno.PSYKIES;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MainMenuScreen extends CreatureScreen {
    SpriteBatch batch;
    BitmapFont font;
    OrthographicCamera cam;
    GlyphLayout glyphLayout;
    float w;
    float h;
    FreeTypeFontGenerator fontGenerator;
    FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    final static float GAME_WORLD_WIDTH = 100;
    final static float GAME_WORLD_HEIGHT = 56;
    Texture menuBackgroundTexture;
    Texture psykiesLogoTexture;
    private Viewport viewport;
    Vector2 backgroundPos;
    Texture buttonTexture;
    TextureRegion buttonIcon;
    TextureRegion buttonBackground;
    Polygon[] buttonPolys;
    Vector3 touchPos;
    Sound uiSound;
    static Music menuMusic;
    boolean showCredits;
    public MainMenuScreen(Game game){
        super(game);

        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 25;
        fontParameter.borderWidth = 2;
        fontParameter.borderColor = Color.BLACK;
        fontParameter.color = Color.WHITE;
        font = fontGenerator.generateFont(fontParameter);
        glyphLayout = new GlyphLayout();

        backgroundPos = new Vector2(-512,-48);
        touchPos = new Vector3();

        batch = new SpriteBatch();
        cam = new OrthographicCamera();

        configureCam();
        buttonPolys = new Polygon[]{new Polygon(new float[]{0, 0, 320, 0, 320, 80, 0, 80}),
                new Polygon(new float[]{0, 0, 320, 0, 320, 80, 0, 80}),
                new Polygon(new float[]{0, 0, 320, 0, 320, 80, 0, 80})
        };

        menuBackgroundTexture = new Texture("menuBackgroundTexture.png");
        psykiesLogoTexture = new Texture("psykiesLogoTexture.png");
        buttonTexture = new Texture(Gdx.files.internal("chooseButtonTexture.png"));
        uiSound = Gdx.audio.newSound(Gdx.files.internal("uiSound.mp3"));
        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("menuMusic.mp3"));

        buttonIcon = new TextureRegion(buttonTexture).split(320, 80)[0][0];
        buttonBackground = new TextureRegion(buttonTexture).split(320, 80)[1][0];

        buttonPolys[0].setPosition(Main.SCREEN_WIDTH/2f - buttonIcon.getRegionWidth()/2f, Main.SCREEN_HEIGHT/2f - 40);
        buttonPolys[1].setPosition(Main.SCREEN_WIDTH/2f - buttonIcon.getRegionWidth()/2f, Main.SCREEN_HEIGHT/2f - (buttonIcon.getRegionHeight() + 60));
        buttonPolys[2].setPosition(Main.SCREEN_WIDTH/2f - buttonIcon.getRegionWidth()/2f, Main.SCREEN_HEIGHT/2f - (buttonIcon.getRegionHeight() * 2 + 80));

        menuMusic.setVolume(1f);
        menuMusic.setLooping(true);
        menuMusic.play();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        cam.update();

        batch.begin();
        batch.setProjectionMatrix(cam.combined);

        if (backgroundPos.x >= 0){
            backgroundPos.set(-512,-48);
        }

        backgroundPos.set(backgroundPos.x + 20 * delta, backgroundPos.y - 20 * delta);
        batch.draw(menuBackgroundTexture, backgroundPos.x,backgroundPos.y);

        if (!showCredits){
            batch.draw(psykiesLogoTexture, Main.SCREEN_WIDTH/2f - psykiesLogoTexture.getWidth()/2f, Main.SCREEN_HEIGHT - Main.SCREEN_HEIGHT/5f - psykiesLogoTexture.getHeight()/2f - 20);

            batch.draw(buttonBackground, Main.SCREEN_WIDTH/2f - buttonIcon.getRegionWidth()/2f + 8, Main.SCREEN_HEIGHT/2f - 40 - 8);
            batch.draw(buttonBackground, Main.SCREEN_WIDTH/2f - buttonIcon.getRegionWidth()/2f + 8, (Main.SCREEN_HEIGHT/2f - (buttonIcon.getRegionHeight() + 60)) - 8);
            batch.draw(buttonBackground, Main.SCREEN_WIDTH/2f - buttonIcon.getRegionWidth()/2f + 8, (Main.SCREEN_HEIGHT/2f - (buttonIcon.getRegionHeight() * 2 + 80)) - 8);

            batch.draw(buttonIcon, Main.SCREEN_WIDTH/2f - buttonIcon.getRegionWidth()/2f - 8, Main.SCREEN_HEIGHT/2f - 40 + 8);
            batch.draw(buttonIcon, Main.SCREEN_WIDTH/2f - buttonIcon.getRegionWidth()/2f - 8, Main.SCREEN_HEIGHT/2f - (buttonIcon.getRegionHeight() + 60) + 8);
            batch.draw(buttonIcon, Main.SCREEN_WIDTH/2f - buttonIcon.getRegionWidth()/2f - 8, Main.SCREEN_HEIGHT/2f - (buttonIcon.getRegionHeight() * 2 + 80) + 8);


            font.getData().setScale(.5f);
            glyphLayout.setText(font,"a game by ficuno");
            w = glyphLayout.width;
            font.draw(batch, glyphLayout, (Main.SCREEN_WIDTH - w) - 16, 24);

            font.getData().setScale(1.2f);
            glyphLayout.setText(font,"Embark");
            w = glyphLayout.width;
            font.draw(batch, glyphLayout, (Main.SCREEN_WIDTH/2f - w/2f) - 8, Main.SCREEN_HEIGHT/2f + 12 + 8);
            glyphLayout.setText(font,"Credits");
            w = glyphLayout.width;
            font.draw(batch, glyphLayout, (Main.SCREEN_WIDTH/2f - w/2f) - 8, Main.SCREEN_HEIGHT/2f - (buttonIcon.getRegionHeight()/2f + 60) + 12 + 8);

            glyphLayout.setText(font,"Quit");
            w = glyphLayout.width;
            font.draw(batch, glyphLayout, (Main.SCREEN_WIDTH/2f - w/2f) - 8, Main.SCREEN_HEIGHT/2f - (buttonIcon.getRegionHeight() * 2 + 42) + 12 + 8);

        } else {
            font.getData().setScale(1f);

            glyphLayout.setText(font,"Developers");
            w = glyphLayout.width;
            font.draw(batch, glyphLayout, (Main.SCREEN_WIDTH/2f - w/2f) - 8, Main.SCREEN_HEIGHT/2f + 300 + 12 + 8);
            glyphLayout.setText(font,"Cheiron Candelaria");
            w = glyphLayout.width;
            font.draw(batch, glyphLayout, (Main.SCREEN_WIDTH/2f - w/2f) - 8, Main.SCREEN_HEIGHT/2f + 200 + 12 + 8);
            glyphLayout.setText(font,"Exequiel Mercolita");
            w = glyphLayout.width;
            font.draw(batch, glyphLayout, (Main.SCREEN_WIDTH/2f - w/2f) - 8, Main.SCREEN_HEIGHT/2f + 100 + 12 + 8);
            glyphLayout.setText(font,"Joshua Tejada");
            w = glyphLayout.width;
            font.draw(batch, glyphLayout, (Main.SCREEN_WIDTH/2f - w/2f) - 8, Main.SCREEN_HEIGHT/2f + 12 + 8);
            glyphLayout.setText(font,"Joshua Cuebillas");
            w = glyphLayout.width;
            font.draw(batch, glyphLayout, (Main.SCREEN_WIDTH/2f - w/2f) - 8, Main.SCREEN_HEIGHT/2f - 100  + 12 + 8);
            glyphLayout.setText(font,"Special thanks to our Playtesters!");
            w = glyphLayout.width;
            font.draw(batch, glyphLayout, (Main.SCREEN_WIDTH/2f - w/2f) - 8, Main.SCREEN_HEIGHT/2f - 200 + 12 + 8);
            glyphLayout.setText(font,"a game by ficuno");
            w = glyphLayout.width;
            font.draw(batch, glyphLayout, (Main.SCREEN_WIDTH/2f - w/2f) - 8, Main.SCREEN_HEIGHT/2f - 300 + 12 + 8);
        }

        batch.end();

        checkTouchRegion();
    }

    public void checkTouchRegion(){
        if (Gdx.input.justTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            cam.unproject(touchPos);

            if (showCredits){
                showCredits = false;
                uiSound.play(0.5f);
                return;
            }

            if (buttonPolys[0].contains(touchPos.x, touchPos.y)) {
                GameScreen.retry = false;
                game.setScreen(new GameScreen(game));
                uiSound.play(0.5f);
                menuMusic.stop();
            } else if (buttonPolys[1].contains(touchPos.x, touchPos.y)) {
                showCredits = true;
                uiSound.play(0.5f);

            } else if (buttonPolys[2].contains(touchPos.x, touchPos.y)){
                Gdx.app.exit();
                System.exit(0);
            }
        }
    }

    public void configureCam(){
        if (Main.SCREEN_HEIGHT >= 800){
            cam.translate(Main.SCREEN_WIDTH / 2f, Main.SCREEN_WIDTH/4f + 20);
            cam.zoom = 15.2f;
            viewport = new FillViewport(MainMenuScreen.GAME_WORLD_WIDTH * Main.aspectRatio, MainMenuScreen.GAME_WORLD_HEIGHT, cam);

        } else {
            cam.translate(Main.SCREEN_WIDTH / 2f, 720 / 2f);
            viewport = new ExtendViewport(MainMenuScreen.GAME_WORLD_WIDTH * Main.aspectRatio, MainMenuScreen.GAME_WORLD_HEIGHT, cam);
            cam.zoom = 7.2f;
        }
    }
    @Override
    public void hide (){}

    @Override
    public void resize(int width, int height) {
        Main.SCREEN_WIDTH = Gdx.graphics.getWidth();
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
