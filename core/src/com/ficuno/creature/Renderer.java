package com.ficuno.creature;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
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
    Texture idCardsTexture;
    Texture egoCardsTexture;
    Texture superegoCardsTexture;
    Texture cardBackgroundTexture;
    Texture systemIconsTexture;
    TextureRegion helpIcon;
    TextureRegion menuIcon;
    TextureRegion bagIcon;
    TextureRegion actionsIcon;
    TextureRegion drawPileIcon;
    TextureRegion discardPileIcon;
    TextureRegion helpSelectedIcon;
    TextureRegion menuSelectedIcon;
    TextureRegion bagSelectedIcon;
    TextureRegion runSelectedIcon;
    Texture spotlightTexture;
    Texture psykiesTexture;
    TextureRegion[] encounterBgTextures = new TextureRegion[4];
    Texture encounterBgTexture;
    Psykey currentPsykey;
    Psykey enemyPsykey;
    FreeTypeFontGenerator fontGenerator;
    FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    BitmapFont font;
    Viewport viewport;
    GlyphLayout glyphLayout;
    float w;
    float h;
    Texture uiBarsTexture;
    TextureRegion yourUIBar;
    TextureRegion enemyUIBar;
    Texture descBoxTexture;
    List<Vector2> handCardPos = new ArrayList<>();
    TouchRegion touchRegion;
    public Renderer(Main main){
        this.main = main;
        this.currentPsykey = main.currentPsykey;
        this.enemyPsykey = main.enemyPsykey;
        this.controller = main.controller;
        this.card = main.card;
        this.touchRegion = main.touchRegion;

        shape = new ShapeRenderer();
        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 25;
        fontParameter.borderWidth = 2;
        fontParameter.borderColor = Color.BLACK;
        fontParameter.color = Color.WHITE;
        font = fontGenerator.generateFont(fontParameter);

        glyphLayout = new GlyphLayout();
        cam = new OrthographicCamera();
        viewport = new ScreenViewport(cam);
        cam.translate(32,32);
        cam.update();

        for (int x = 0; x < 6; x++){
            handCardPos.add(new Vector2(Main.SCREEN_WIDTH / 2f - findStartPos(card.handPileCardNames.size()) - getXPosition(x - 2), 40));
        }

        loadAssets();
        createAssets();

        touchRegion.uiTouchRegionPoly.get(0).setPosition(3, Creature.HEIGHT - (helpIcon.getRegionHeight() + 3));
        touchRegion.uiTouchRegionPoly.get(1).setPosition(Main.SCREEN_WIDTH - (menuIcon.getRegionWidth() + 3),
                Creature.HEIGHT - (helpIcon.getRegionHeight() + 3));
        touchRegion.uiTouchRegionPoly.get(2).setPosition(3, 3);
        touchRegion.uiTouchRegionPoly.get(3).setPosition(Main.SCREEN_WIDTH - (actionsIcon.getRegionWidth() + 3), 3);
    }

    public void loadAssets(){
        idCardsTexture = new Texture(Gdx.files.internal("idCardsTexture.png"));
        egoCardsTexture = new Texture(Gdx.files.internal("egoCardsTexture.png"));
        superegoCardsTexture = new Texture(Gdx.files.internal("superegoCardsTexture.png"));

        psykiesTexture = new Texture(Gdx.files.internal("psykiesTexture.png"));

        cardBackgroundTexture = new Texture(Gdx.files.internal("cardBackgroundTexture.png"));
        spotlightTexture = new Texture(Gdx.files.internal("spotlightTexture.png"));

        encounterBgTexture = new Texture(Gdx.files.internal("encounterBackgroundTexture.png"));

        systemIconsTexture = new Texture(Gdx.files.internal("systemIconsTexture.png"));
        uiBarsTexture = new Texture(Gdx.files.internal("uiBarsTexture.png"));

        descBoxTexture = new Texture(Gdx.files.internal("descBoxTexture.png"));
    }

    public void createAssets() {
        int i = 0;
        for (int y = 0; y < 5; y++){
            for (int x = 0; x < 7; x++) {
                card.idCards[i] = new TextureRegion(splitTexture(idCardsTexture, y, 112, 192)[x]);

                card.egoCards[i] = new TextureRegion(splitTexture(egoCardsTexture, y, 112, 192)[x]);

                card.superegoCards[i] = new TextureRegion(splitTexture(superegoCardsTexture, y, 112, 192)[x]);

                i++;
                if (i == card.maxCards){
                    break;
                }
            }

            if (i == card.maxCards){
                break;
            }
        }

        for (int y = 0; y < 4; y++){
            encounterBgTextures[y] = new TextureRegion(splitTexture(encounterBgTexture, y, 1920, 720)[0]);
        }

        helpIcon = new TextureRegion(splitTexture(systemIconsTexture, 0, 96,96)[0]);
        menuIcon = new TextureRegion(splitTexture(systemIconsTexture, 0, 96,96)[1]);
        bagIcon = new TextureRegion(splitTexture(systemIconsTexture, 0, 96,96)[2]);
        actionsIcon = new TextureRegion(splitTexture(systemIconsTexture, 0, 96,96)[3]);

        helpSelectedIcon = new TextureRegion(splitTexture(systemIconsTexture, 1, 96,96)[0]);
        menuSelectedIcon = new TextureRegion(splitTexture(systemIconsTexture, 1, 96,96)[1]);
        bagSelectedIcon = new TextureRegion(splitTexture(systemIconsTexture, 1, 96,96)[2]);
        runSelectedIcon = new TextureRegion(splitTexture(systemIconsTexture, 1, 96,96)[3]);

        discardPileIcon = new TextureRegion(splitTexture(systemIconsTexture, 2, 96,96)[0]);
        drawPileIcon = new TextureRegion(splitTexture(systemIconsTexture, 2, 96,96)[1]);

        yourUIBar = new TextureRegion(splitTexture(uiBarsTexture, 0, 1048, 160)[0]);
        enemyUIBar = new TextureRegion(splitTexture(uiBarsTexture, 1, 1048, 160)[0]);
    }

    public void render(float deltaTime){
        ScreenUtils.clear(0.796f, 0.859f, 0.988f, 1);
        Gdx.gl.glClearColor(132, 126, 135, 255);

        batch.begin();
        shape.begin(ShapeRenderer.ShapeType.Line);
        shape.setColor(Color.CYAN);

        renderBackground();
        renderCards();
        renderUI();
        renderPsykies();
        renderText();
        renderOverlay();

        for (int x = 0; x < touchRegion.cardTouchRegionPoly.size(); x++){
            shape.polygon(touchRegion.cardTouchRegionPoly.get(x).getTransformedVertices());
        }

        for (Polygon uiTouchRegion : touchRegion.uiTouchRegionPoly){
            shape.polygon(uiTouchRegion.getTransformedVertices());
        }

        batch.end();
        shape.end();
    }

    public void renderOverlay(){
       // batch.draw(descBoxTexture,(Main.SCREEN_WIDTH / 2f) - descBoxTexture.getWidth() / 2f, 300);
    }

    public void renderText(){
        font.draw(batch, "Deal 15 damage to the enemy and inflict stun.", 100,100);

        setGlyphLayout(Integer.toString(card.drawPileCardNames.size()));
        font.draw(batch, glyphLayout, (Main.SCREEN_WIDTH - 128) - w / 2, 208 + h);

        setGlyphLayout(Integer.toString(card.discardPileCardNames.size()));
        font.draw(batch, glyphLayout, 125 + w / 2, 208 + h);

        setGlyphLayout(currentPsykey.name);
        font.draw(batch, glyphLayout, ((Main.SCREEN_WIDTH / 2f) - (Main.SCREEN_WIDTH / 8f)) - 240 - (w/2), 440);

        setGlyphLayout(enemyPsykey.name);
        font.draw(batch, glyphLayout, ((Main.SCREEN_WIDTH / 2f) + (Main.SCREEN_WIDTH / 8f)) + 236 - (w/2), 556);
    }

    public void renderPsykies(){
        batch.draw(currentPsykey.textureMirror, ((Main.SCREEN_WIDTH / 2f) - (Main.SCREEN_WIDTH / 8f)) - 80,312);
        batch.draw(enemyPsykey.texture, ((Main.SCREEN_WIDTH / 2f) + (Main.SCREEN_WIDTH / 8f)) - 80, 512);
    }

    public void renderUI(){
        batch.draw(spotlightTexture, ((Main.SCREEN_WIDTH / 2f) - (Main.SCREEN_WIDTH / 8f)) - 108,272);
        batch.draw(spotlightTexture, ((Main.SCREEN_WIDTH / 2f) + (Main.SCREEN_WIDTH / 8f)) - 108,472);

        batch.draw(helpIcon, 3, Creature.HEIGHT - (helpIcon.getRegionHeight() + 3));
        batch.draw(menuIcon, Main.SCREEN_WIDTH - (menuIcon.getRegionWidth() + 3),
                Creature.HEIGHT - (helpIcon.getRegionHeight() + 3));
        batch.draw(bagIcon, 3, 3);
        batch.draw(actionsIcon, Main.SCREEN_WIDTH - (actionsIcon.getRegionWidth() + 3), 3);

        batch.draw(yourUIBar, ((Main.SCREEN_WIDTH / 2f) - (Main.SCREEN_WIDTH / 8f)) - 364,312);
        batch.draw(enemyUIBar, ((Main.SCREEN_WIDTH / 2f) + (Main.SCREEN_WIDTH / 8f)) - 688,504);

        batch.draw(discardPileIcon, 52, 140);
        batch.draw(drawPileIcon, Main.SCREEN_WIDTH - 128, 140);
    }



    public void renderCards(){
        if (card.handPileCardNames == null){
            return;
        }

        for (int x = 0; x < 6; x++){
            handCardPos.get(x).set(Main.SCREEN_WIDTH / 2f -
                            findStartPos(card.handPileCardNames.size()) - getXPosition(x + 1),
                    40 + main.handCardSelected.get(x));
        }

        for (int x = 0; x < touchRegion.cardTouchRegionPoly.size(); x++){
            touchRegion.cardTouchRegionPoly.get(x).setPosition(handCardPos.get(x).x, handCardPos.get(x).y);
        }

        if (card.handPileCardNames.size() >= 1){
            batch.draw(cardBackgroundTexture, handCardPos.get(0).x + 16, handCardPos.get(0).y - 16);
            batch.draw(getTexture(0), handCardPos.get(0).x, handCardPos.get(0).y);
        }

        if (card.handPileCardNames.size() >= 2){
            batch.draw(cardBackgroundTexture, handCardPos.get(1).x + 16, handCardPos.get(1).y - 16);
            batch.draw(getTexture(1), handCardPos.get(1).x, handCardPos.get(1).y);
        }

        if (card.handPileCardNames.size() >= 3){
            batch.draw(cardBackgroundTexture, handCardPos.get(2).x + 16, handCardPos.get(2).y - 16);
            batch.draw(getTexture(2), handCardPos.get(2).x, handCardPos.get(2).y);
        }

        if (card.handPileCardNames.size() >= 4){
            batch.draw(cardBackgroundTexture, handCardPos.get(3).x + 16, handCardPos.get(3).y - 16);
            batch.draw(getTexture(3), handCardPos.get(3).x, handCardPos.get(3).y);
        }

        if (card.handPileCardNames.size() >= 5){
            batch.draw(cardBackgroundTexture, handCardPos.get(4).x + 16, handCardPos.get(4).y - 16);
            batch.draw(getTexture(4), handCardPos.get(4).x, handCardPos.get(4).y);
        }

        if (card.handPileCardNames.size() >= 6){
            batch.draw(cardBackgroundTexture, handCardPos.get(5).x + 16, handCardPos.get(5).y - 16);
            batch.draw(getTexture(5), handCardPos.get(5).x, handCardPos.get(5).y);
        }
    }

    public  int getXPosition(int num){
        return 120 * (Math.round(((card.handPileCardNames.size())/ 2f)) - num);
    }
    public TextureRegion getTexture(int index){
        return card.getTexture(card.handPileCardNames.get(index), card.handPileCardTypes.get(index));
    }

    public void renderBackground(){
        batch.draw(encounterBgTextures[0], 0,0);
    }

    public int findStartPos(int cardCount){
        if (cardCount % 2 ==0){
            return 124;
        }

        return 56;
    }

    public static TextureRegion[] splitTexture(Texture texture, int index, int width, int height){
        return new TextureRegion(texture).split(width,height)[index];
    }
    public static TextureRegion[] mirrorTexture(Texture texture, int index, int width, int height){
        TextureRegion[] mirror = new TextureRegion(texture).split(width,height)[index];
        for (TextureRegion region: mirror){
            region.flip(true, false);
        }

        return mirror;
    }

    public void setGlyphLayout(String text){
        glyphLayout.setText(font, text);
        w = glyphLayout.width;
        h = glyphLayout.height;
    }
}