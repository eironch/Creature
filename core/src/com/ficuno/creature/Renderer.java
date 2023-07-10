package com.ficuno.creature;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.List;

public class Renderer {
    Main main;
    OrthographicCamera cam;
    SpriteBatch batch = new SpriteBatch();
    ShapeRenderer shape;
    Card card;
    Controller controller;
    Psykey currentPsykey;
    Psykey enemyPsykey;
    FreeTypeFontGenerator fontGenerator;
    FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    BitmapFont font;
    Viewport viewport;
    GlyphLayout glyphLayout;
    float w;
    float h;
    List<Vector2> handCardPos = new ArrayList<>();
    TouchRegion touchRegion;
    Vector3 touchPos;
    Assets assets;
    Psykey psykeyRef;
    Psykey enemyPsykeyRef;

    public Renderer(Main main){
        this.main = main;
        this.currentPsykey = main.currentPsykey;
        this.psykeyRef = main.psykeyRef;
        this.enemyPsykey = main.enemyPsykey;
        this.enemyPsykeyRef = main.enemyPsykeyRef;
        this.controller = main.controller;
        this.card = main.card;
        this.assets = main.assets;
        this.touchRegion = main.touchRegion;

        touchPos = new Vector3();
        shape = new ShapeRenderer();
        cam = new OrthographicCamera();

        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 25;
        fontParameter.borderWidth = 2;
        fontParameter.borderColor = Color.BLACK;
        fontParameter.color = Color.WHITE;
        font = fontGenerator.generateFont(fontParameter);
        glyphLayout = new GlyphLayout();
        // highlight card
        for (int x = 0; x < 6; x++){
            handCardPos.add(new Vector2(Main.SCREEN_WIDTH / 2f - findStartPos(card.yourHandPileCardTypesNames.size()) - getXPosition(x - 2), 40));
        }
        configureCam();
    }


    public void render(float deltaTime){
        ScreenUtils.clear(0.796f, 0.859f, 0.988f, 1);
        Gdx.gl.glClearColor(132, 126, 135, 255);

        cam.update();
        batch.begin();
        batch.setProjectionMatrix(cam.combined);
        shape.setProjectionMatrix(cam.combined);
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(172/255f, 50/255f, 50/255f, 255/255f);

        renderBackground();
        renderCards();
        renderUI();
        renderPsykies();
        renderText();
        renderOverlay();

        batch.end();
        shape.end();

        shape.begin(ShapeRenderer.ShapeType.Line);
        shape.setColor(Color.CYAN);

        renderDebugUI();

        shape.end();
    }

    public void renderOverlay(){
        // batch.draw(descBoxTexture,(Main.SCREEN_WIDTH / 2f) - descBoxTexture.getWidth() / 2f, 300);
    }

    public void renderText(){
        font.draw(batch, "Deal 15 damage to the enemy and inflict stun.", 100,100);

        setGlyphLayout(card.yourDrawPileCardTypesNames.size());
        font.draw(batch, glyphLayout, (Main.SCREEN_WIDTH - 128) - w / 2, 208 + h);

        setGlyphLayout(card.yourDiscardPileCardTypesNames.size());
        font.draw(batch, glyphLayout, 125 + w / 2, 208 + h);

        setGlyphLayout(currentPsykey.name);
        font.draw(batch, glyphLayout, ((Main.SCREEN_WIDTH / 2f) - (Main.SCREEN_WIDTH / 8f)) - 240 - (w/2), 440);

        setGlyphLayout(enemyPsykey.name);
        font.draw(batch, glyphLayout, ((Main.SCREEN_WIDTH / 2f) + (Main.SCREEN_WIDTH / 8f)) + 236 - (w/2), 556);

        setGlyphLayout(currentPsykey.healthPoints + "/" + psykeyRef.healthPoints);
        font.draw(batch, glyphLayout, ((Main.SCREEN_WIDTH / 2f) - (Main.SCREEN_WIDTH / 8f)) + 200 - (w/2), 361);

        setGlyphLayout(enemyPsykey.healthPoints + "/" + enemyPsykeyRef.healthPoints);
        font.draw(batch, glyphLayout, ((Main.SCREEN_WIDTH / 2f) + (Main.SCREEN_WIDTH / 8f)) - 200 - (w/2), 632);
    }

    public void renderPsykies(){
        batch.draw(currentPsykey.textureMirror, ((Main.SCREEN_WIDTH / 2f) - (Main.SCREEN_WIDTH / 8f)) - 80,312);
        batch.draw(enemyPsykey.texture, ((Main.SCREEN_WIDTH / 2f) + (Main.SCREEN_WIDTH / 8f)) - 80, 512);
    }

    public void renderUI(){
        batch.draw(assets.spotlightTexture, ((Main.SCREEN_WIDTH / 2f) - (Main.SCREEN_WIDTH / 8f)) - 108,272);
        batch.draw(assets.spotlightTexture, ((Main.SCREEN_WIDTH / 2f) + (Main.SCREEN_WIDTH / 8f)) - 108,472);

        batch.draw(assets.helpIcon, 3, Creature.HEIGHT - (assets.helpIcon.getRegionHeight()));
        batch.draw(assets.menuIcon, Main.SCREEN_WIDTH - (assets.menuIcon.getRegionWidth() + 3),
                Creature.HEIGHT - (assets.helpIcon.getRegionHeight() + 3));
        batch.draw(assets.bagIcon, 3, 3);
        batch.draw(assets.actionsIcon, Main.SCREEN_WIDTH - (assets.actionsIcon.getRegionWidth() + 3), 3);

        touchRegion.uiTouchRegionPolys.get(0).setPosition(3, Creature.HEIGHT - (assets.helpIcon.getRegionHeight() + 3));
        touchRegion.uiTouchRegionPolys.get(1).setPosition(Main.SCREEN_WIDTH - (assets.menuIcon.getRegionWidth() + 3),
                Creature.HEIGHT - (assets.helpIcon.getRegionHeight() + 3));
        touchRegion.uiTouchRegionPolys.get(2).setPosition(3, 3);
        touchRegion.uiTouchRegionPolys.get(3).setPosition(Main.SCREEN_WIDTH - (assets.actionsIcon.getRegionWidth() + 3), 3);

        batch.draw(assets.yourUIBar, ((Main.SCREEN_WIDTH / 2f) - (Main.SCREEN_WIDTH / 8f)) - 364,312);
        batch.draw(assets.enemyUIBar, ((Main.SCREEN_WIDTH / 2f) + (Main.SCREEN_WIDTH / 8f)) - 688,504);

        batch.draw(assets.discardPileIcon, 52, 140);
        batch.draw(assets.drawPileIcon, Main.SCREEN_WIDTH - 128, 140);

        shape.rect(((Main.SCREEN_WIDTH / 2f) - (Main.SCREEN_WIDTH / 8f)) + 288,344,
                376 * ((float) currentPsykey.healthPoints / (float) psykeyRef.healthPoints),16);
        shape.rect(((Main.SCREEN_WIDTH / 2f) + (Main.SCREEN_WIDTH / 8f)) - 292 - (376 * ((float) enemyPsykey.healthPoints / (float) enemyPsykeyRef.healthPoints)),616,
                376 * ((float) enemyPsykey.healthPoints / (float) enemyPsykeyRef.healthPoints),16);
    }

    public void renderCards(){
        if (card.yourHandPileCardTypesNames == null){
            return;
        }

        for (int x = 0; x < 6; x++){
            handCardPos.get(x).set(Main.SCREEN_WIDTH / 2f -
                            findStartPos(card.yourHandPileCardTypesNames.size()) - getXPosition(x + 1),
                    40 + main.handCardSelected.get(x));
        }

        for (int x = 0; x < touchRegion.cardTouchRegionPolys.size(); x++){
            touchRegion.cardTouchRegionPolys.get(x).setPosition(handCardPos.get(x).x, handCardPos.get(x).y);
        }

        if (card.yourHandPileCardTypesNames.size() >= 1){
            batch.draw(assets.cardBackgroundTexture, handCardPos.get(0).x + 16, handCardPos.get(0).y - 16);
            batch.draw(getTexture(0), handCardPos.get(0).x, handCardPos.get(0).y);
        }

        if (card.yourHandPileCardTypesNames.size() >= 2){
            batch.draw(assets.cardBackgroundTexture, handCardPos.get(1).x + 16, handCardPos.get(1).y - 16);
            batch.draw(getTexture(1), handCardPos.get(1).x, handCardPos.get(1).y);
        }

        if (card.yourHandPileCardTypesNames.size() >= 3){
            batch.draw(assets.cardBackgroundTexture, handCardPos.get(2).x + 16, handCardPos.get(2).y - 16);
            batch.draw(getTexture(2), handCardPos.get(2).x, handCardPos.get(2).y);
        }

        if (card.yourHandPileCardTypesNames.size() >= 4){
            batch.draw(assets.cardBackgroundTexture, handCardPos.get(3).x + 16, handCardPos.get(3).y - 16);
            batch.draw(getTexture(3), handCardPos.get(3).x, handCardPos.get(3).y);
        }

        if (card.yourHandPileCardTypesNames.size() >= 5){
            batch.draw(assets.cardBackgroundTexture, handCardPos.get(4).x + 16, handCardPos.get(4).y - 16);
            batch.draw(getTexture(4), handCardPos.get(4).x, handCardPos.get(4).y);
        }

        if (card.yourHandPileCardTypesNames.size() >= 6){
            batch.draw(assets.cardBackgroundTexture, handCardPos.get(5).x + 16, handCardPos.get(5).y - 16);
            batch.draw(getTexture(5), handCardPos.get(5).x, handCardPos.get(5).y);
        }
    }
    public void renderBackground(){
        batch.draw(assets.encounterBgTextures[0], -668,-216);
    }
    public void  renderDebugUI(){
        for (int x = 0; x < touchRegion.cardTouchRegionPolys.size(); x++){
            shape.polygon(touchRegion.cardTouchRegionPolys.get(x).getTransformedVertices());
        }

        for (Polygon uiTouchRegion : touchRegion.uiTouchRegionPolys){
            shape.polygon(uiTouchRegion.getTransformedVertices());
        }

        if (Gdx.input.justTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(),0);
            cam.unproject(touchPos);
        }
        shape.circle(touchPos.x, touchPos.y, 20);
    }

    public  int getXPosition(int num){
        return 120 * (Math.round(((card.yourHandPileCardTypesNames.size())/ 2f)) - num);
    }
    public TextureRegion getTexture(int index){
        return card.getTexture(card.yourHandPileCardTypesNames.get(index)[0], card.yourHandPileCardTypesNames.get(index)[1]);
    }

    public int findStartPos(int cardCount){
        if (cardCount % 2 ==0){
            return 124;
        }

        return 56;
    }

    public void setGlyphLayout(String text){
        glyphLayout.setText(font, text);
        w = glyphLayout.width;
        h = glyphLayout.height;
    }

    public void setGlyphLayout(int text){
        glyphLayout.setText(font, Integer.toString(text));
        w = glyphLayout.width;
        h = glyphLayout.height;
    }

    public void configureCam(){
        if (Main.SCREEN_HEIGHT >= 800){
            cam.translate(Main.SCREEN_WIDTH / 2f, 720 / 2f);
            cam.zoom = 15.2f;
            viewport = new FillViewport(MainMenuScreen.GAME_WORLD_WIDTH * Main.aspectRatio, MainMenuScreen.GAME_WORLD_HEIGHT, cam);

        } else {
            cam.translate(Main.SCREEN_WIDTH / 2f, 720 / 2f);
            viewport = new ExtendViewport(MainMenuScreen.GAME_WORLD_WIDTH * Main.aspectRatio, MainMenuScreen.GAME_WORLD_HEIGHT, cam);
            cam.zoom = 7.2f;
        }
    }
}