package com.ficuno.creature;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
    BitmapFont font;
    GlyphLayout glyphLayout;
    Viewport viewport;
    List<Vector2> handCardPos = new ArrayList<>();
    TouchRegion touchRegion;
    Vector3 touchPos;
    Assets assets;
    Psykey[] playerPsykeyRef;
    Psykey[] playerPsykey;
    Psykey[] enemyPsykey;
    Psykey[] enemyPsykeyRef;
    List<Vector2> handCardTextPos = new ArrayList<>();
    List<Vector2> handCardTextPos2 = new ArrayList<>();
    Encounter encounter;
    Vector2 playerCardDisplayPos;
    Vector2 enemyCardDisplayPos;
    Starter starter;

    public Renderer(Main main){
        this.main = main;
        this.playerPsykey = main.playerPsykey;
        this.playerPsykeyRef = main.playerPsykeyRef;
        this.enemyPsykey = main.enemyPsykey;
        this.enemyPsykeyRef = main.enemyPsykeyRef;
        this.controller = main.controller;
        this.encounter = main.encounter;
        this.card = main.card;
        this.assets = main.assets;
        this.touchRegion = main.touchRegion;
        this.starter = main.starter;

        font = assets.font;
        glyphLayout = assets.glyphLayout;
        playerCardDisplayPos = new Vector2();
        enemyCardDisplayPos = new Vector2();
        playerCardDisplayPos.x = ((Main.SCREEN_WIDTH/3.5f) - assets.cardBackgroundTexture.getWidth()/2f);
        playerCardDisplayPos.y = 488 - assets.cardBackgroundTexture.getHeight()/2f;
        enemyCardDisplayPos.x = (((Main.SCREEN_WIDTH) - (Main.SCREEN_WIDTH/3.5f)) - assets.cardBackgroundTexture.getWidth()/2f);
        enemyCardDisplayPos.y = 488 - assets.cardBackgroundTexture.getHeight()/2f;

        touchPos = new Vector3();
        shape = new ShapeRenderer();
        cam = new OrthographicCamera();

        // highlight card
        for (int x = 0; x < 6; x++){
            handCardPos.add(new Vector2(0,0));
            handCardTextPos.add(new Vector2(0,0));
            handCardTextPos2.add(new Vector2(0,0));
        }

        configureCam();
    }

    public void render(float deltaTime){
        ScreenUtils.clear(0.796f, 0.859f, 0.988f, 1);
        Gdx.gl.glClearColor(132, 126, 135, 255);
        main.overlayTimer += deltaTime;

        if (main.showOverlay){
            if (main.overlayTimer >= 1.5){
                main.showCardPlayed = false;
                if (main.turnState == Main.PRE_TURN){
                    main.enemyPlayIcon[Main.FIRST] = null;
                    main.enemyPlayIcon[Main.SECOND] = null;
                    main.playerPlayIcon[Main.FIRST] = null;
                    main.playerPlayIcon[Main.SECOND] = null;
                    encounter.enemyCardPlayed = null;
                    encounter.playerCardPlayed = null;
                }
            }

            if (main.overlayTimer >= 2){
                main.showOverlay = false;

                main.turnState = main.currentTurn;

            }
        }

        if (main.showTurnDisplay){
            if (main. overlayTimer >= 2){
                if (main.turnState == Main.ENEMY_TURN){
                    main.showTurnDisplay = false;

                    encounter.enemyTurn();
                }

                if (playerPsykey[main.playerPsykeySelected].healthPoints == 0){
                    if (main.playerPsykeySelected == Main.FIRST){
                        if (playerPsykey[Main.SECOND] != null){
                            main.playerPsykeySelected = Main.SECOND;
                        } else {
                            GameScreen.lost = true;
                        }
                    } else {
                        if (playerPsykey[Main.FIRST] != null){
                            main.playerPsykeySelected = Main.FIRST;
                        } else {
                            GameScreen.lost = true;
                        }
                    }
                }
            }
        }

        if (main.overlayTimer >= 4){
            main.overlayTimer = 0;
        }

        if (Gdx.input.justTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(),0);
            cam.unproject(touchPos);
        }

        font.getData().setScale(1);
        cam.update();
        shape.setProjectionMatrix(cam.combined);
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(172/255f, 50/255f, 50/255f, 255/255f);
        batch.begin();
        batch.setProjectionMatrix(cam.combined);

        if (!GameScreen.lost){
            if (main.chosePsykey){
                renderBackground();
                renderCards();
                renderUI();
                renderOtherUI();
                renderPsykies();
                renderText();
            } else {
                starter.render(batch, font);
            }
        } else {
            renderLost();
        }


        renderOverlay(deltaTime);

        batch.end();
        shape.end();

        shape.begin(ShapeRenderer.ShapeType.Line);
        shape.setColor(Color.CYAN);

        renderDebugUI();

        shape.end();
    }

    public void renderOverlay(float deltaTime){
        showStats();

        if (main.showOverlay){
            font.getData().setScale(2);

            if (main.currentTurn == Main.ENEMY_TURN){
                font.setColor(new Color(172/255f, 50/255f, 50/255f, 255/255f));

                assets.setGlyphLayout("Enemy Turn");
                font.draw(batch, glyphLayout, (Main.SCREEN_WIDTH/2f) - assets.w/2, 509);
            } else if (main.currentTurn == Main.PLAYER_TURN){
                font.setColor(new Color(99/255f, 155/255f, 255/255f, 255/255f));

                assets.setGlyphLayout("Player Turn");
                font.draw(batch, glyphLayout, (Main.SCREEN_WIDTH/2f) - assets.w/2, 509);
            }

            font.setColor(Color.WHITE);
            font.getData().setScale(1);

            showCardPlayedDisplay();
        }
    }

    public void renderText(){
        //font.draw(batch, "Deal 15 damage to the enemy and inflict stun.", 100,100);

        assets.setGlyphLayout(card.playerDrawPileCardTypesNames__.get(main.playerPsykeySelected).size());
        font.draw(batch, glyphLayout, (Main.SCREEN_WIDTH - (104 + assets.drawPileIcon.getRegionWidth())) - assets.w/2, 208 + assets.h);

        assets.setGlyphLayout(card.playerDiscardPileCardTypesNames__.get(main.playerPsykeySelected).size());
        font.draw(batch, glyphLayout, (100 + assets.drawPileIcon.getRegionWidth()) - assets.w/2, 208 + assets.h);

        if (!main.showStatsPlayer){
            assets.setGlyphLayout(playerPsykey[main.playerPsykeySelected].name);
            font.draw(batch, glyphLayout, ((Main.SCREEN_WIDTH / 2f) - (Main.SCREEN_WIDTH / 8f)) - 240 - (assets.w/2), 428);
            assets.setGlyphLayout(playerPsykey[main.playerPsykeySelected].healthPoints + "/" + playerPsykeyRef[main.playerPsykeySelected].healthPoints);
            font.draw(batch, glyphLayout, ((Main.SCREEN_WIDTH / 2f) - (Main.SCREEN_WIDTH / 8f)) + 204 - (assets.w/2), 348);
        }

        if (!main.showStatsEnemy){
            assets.setGlyphLayout(enemyPsykey[main.enemyPsykeySelected].name);
            font.draw(batch, glyphLayout, ((Main.SCREEN_WIDTH / 2f) + (Main.SCREEN_WIDTH / 8f)) + 232 - (assets.w/2), 556);
            assets.setGlyphLayout(enemyPsykey[main.enemyPsykeySelected].healthPoints + "/" + enemyPsykeyRef[main.enemyPsykeySelected].healthPoints);
            font.draw(batch, glyphLayout, ((Main.SCREEN_WIDTH / 2f) + (Main.SCREEN_WIDTH / 8f)) - 200 - (assets.w/2), 632);
        }
    }

    public void renderPsykies(){
        batch.draw(playerPsykey[main.playerPsykeySelected].textureMirror, ((Main.SCREEN_WIDTH / 2f) - (Main.SCREEN_WIDTH / 8f)) - 80,308);
        touchRegion.playerPsykeyPoly.setPosition(((Main.SCREEN_WIDTH / 2f) - (Main.SCREEN_WIDTH / 8f)) - 80,308);

        batch.draw(enemyPsykey[main.enemyPsykeySelected].texture, ((Main.SCREEN_WIDTH / 2f) + (Main.SCREEN_WIDTH / 8f)) - 80, 508);
        touchRegion.enemyPsykeyPoly.setPosition(((Main.SCREEN_WIDTH / 2f) + (Main.SCREEN_WIDTH / 8f)) - 80, 508);
    }

    public void renderOtherUI(){
        batch.draw(assets.helpIcon, 0, Creature.HEIGHT - (assets.helpIcon.getRegionHeight() + 10));
        batch.draw(assets.menuIcon, Main.SCREEN_WIDTH - (assets.menuIcon.getRegionWidth() + 10),
                Creature.HEIGHT - (assets.menuIcon.getRegionHeight() + 10));
        batch.draw(assets.bagIcon, 10, 10);
        batch.draw(main.actionsMenuState, Main.SCREEN_WIDTH - (main.actionsMenuState.getRegionWidth() + 10), 10);


        touchRegion.uiTouchRegionPolys.get(0).setPosition(0, Creature.HEIGHT - (assets.helpIcon.getRegionHeight() + 10));
        touchRegion.uiTouchRegionPolys.get(1).setPosition(Main.SCREEN_WIDTH - (assets.menuIcon.getRegionWidth() + 10),
                Creature.HEIGHT - (assets.menuIcon.getRegionHeight() + 10));
        touchRegion.uiTouchRegionPolys.get(2).setPosition(10, 10);
        touchRegion.uiTouchRegionPolys.get(3).setPosition(Main.SCREEN_WIDTH - (assets.actionsIconClose.getRegionWidth() + 10), 10);

        batch.draw(assets.discardPileIcon, 104, 140);
        batch.draw(assets.drawPileIcon, Main.SCREEN_WIDTH - (104 + assets.drawPileIcon.getRegionWidth()), 140);
    }

    public void renderUI(){
        if (!main.showStatsPlayer){
            shape.rect(((Main.SCREEN_WIDTH / 2f) - (Main.SCREEN_WIDTH / 8f)) + 292,332,
                    376 * ((float) playerPsykey[main.playerPsykeySelected].healthPoints / (float) playerPsykeyRef[main.playerPsykeySelected].healthPoints),16);
            batch.draw(assets.playerUIBar, ((Main.SCREEN_WIDTH / 2f) - (Main.SCREEN_WIDTH / 8f)) - 368,300);

            if (main.playerPlayIcon[main.playerPsykeySelected] != null){
                batch.draw(main.playerPlayIcon[main.playerPsykeySelected],
                        ((Main.SCREEN_WIDTH / 2f) - (Main.SCREEN_WIDTH / 8f)) + 96, 400);
            }

        }

        if (!main.showStatsEnemy){
            shape.rect(((Main.SCREEN_WIDTH / 2f) + (Main.SCREEN_WIDTH / 8f)) - 292 -
                            (376 * ((float) enemyPsykey[main.enemyPsykeySelected].healthPoints / (float) enemyPsykeyRef[main.enemyPsykeySelected].healthPoints)),616,
                    376 * ((float) enemyPsykey[main.enemyPsykeySelected].healthPoints / (float) enemyPsykeyRef[main.enemyPsykeySelected].healthPoints),16);
            batch.draw(assets.enemyUIBar, ((Main.SCREEN_WIDTH / 2f) + (Main.SCREEN_WIDTH / 8f)) - 688,504);

            if (main.enemyPlayIcon[main.enemyPsykeySelected] != null){
                batch.draw(main.enemyPlayIcon[main.enemyPsykeySelected],
                        ((Main.SCREEN_WIDTH / 2f) - (Main.SCREEN_WIDTH / 8f)) + 144, 500);
            }
        }
    }

    public void renderCards(){
        if (card.playerHandPileCardTypesNames__ == null){
            return;
        }

        for (int x = 0; x < 6; x++){
            handCardPos.get(x).set(Main.SCREEN_WIDTH / 2f -
                            findStartPos(card.playerHandPileCardTypesNames__.get(main.playerPsykeySelected).size()) - getXPosition(x + 1),
                    40 + main.handCardSelected.get(x));

            handCardTextPos.get(x).set(Main.SCREEN_WIDTH / 2f -
                            findStartPos(card.playerHandPileCardTypesNames__.get(main.playerPsykeySelected).size()) - getXPosition(x + 1),
                    222 + main.handCardSelected.get(x));

            handCardTextPos2.get(x).set(Main.SCREEN_WIDTH / 2f -
                            findStartPos(card.playerHandPileCardTypesNames__.get(main.playerPsykeySelected).size()) - getXPosition(x + 1),
                    67 + main.handCardSelected.get(x));
        }

        for (int x = 0; x < touchRegion.cardTouchRegionPolys__.get(main.playerPsykeySelected).size(); x++){
            touchRegion.cardTouchRegionPolys__.get(main.playerPsykeySelected).get(x).setPosition(handCardPos.get(x).x, handCardPos.get(x).y);
        }

        if (card.playerHandPileCardTypesNames__.get(main.playerPsykeySelected).size() >= 1){
            batch.draw(assets.cardBackgroundTexture, handCardPos.get(0).x + 16, handCardPos.get(0).y - 16);
            batch.draw(getTexture(0), handCardPos.get(0).x, handCardPos.get(0).y);

            if ((card.playerHandPileCardTypesNames__.get(main.playerPsykeySelected).get(0)).length == 3){
                assets.setGlyphLayout(card.playerHandPileCardTypesNames__.get(main.playerPsykeySelected).get(0)[2]);
                font.draw(batch, glyphLayout, handCardTextPos.get(0).x + 22 - assets.w/2, handCardTextPos.get(0).y);

                assets.setGlyphLayout(card.playerHandPileCardTypesNames__.get(main.playerPsykeySelected).get(0)[2]);
                font.draw(batch, glyphLayout, handCardTextPos2.get(0).x + 90 - assets.w/2, handCardTextPos2.get(0).y);
            }
        }

        if (card.playerHandPileCardTypesNames__.get(main.playerPsykeySelected).size() >= 2){
            batch.draw(assets.cardBackgroundTexture, handCardPos.get(1).x + 16, handCardPos.get(1).y - 16);
            batch.draw(getTexture(1), handCardPos.get(1).x, handCardPos.get(1).y);

            if ((card.playerHandPileCardTypesNames__.get(main.playerPsykeySelected).get(1)).length == 3){
                assets.setGlyphLayout(card.playerHandPileCardTypesNames__.get(main.playerPsykeySelected).get(1)[2]);
                font.draw(batch, glyphLayout, handCardTextPos.get(1).x + 22 - assets.w/2, handCardTextPos.get(1).y);

                assets.setGlyphLayout(card.playerHandPileCardTypesNames__.get(main.playerPsykeySelected).get(1)[2]);
                font.draw(batch, glyphLayout, handCardTextPos2.get(1).x + 90 - assets.w/2, handCardTextPos2.get(1).y);
            }
        }

        if (card.playerHandPileCardTypesNames__.get(main.playerPsykeySelected).size() >= 3){
            batch.draw(assets.cardBackgroundTexture, handCardPos.get(2).x + 16, handCardPos.get(2).y - 16);
            batch.draw(getTexture(2), handCardPos.get(2).x, handCardPos.get(2).y);

            if ((card.playerHandPileCardTypesNames__.get(main.playerPsykeySelected).get(2)).length == 3){
                assets.setGlyphLayout(card.playerHandPileCardTypesNames__.get(main.playerPsykeySelected).get(2)[2]);
                font.draw(batch, glyphLayout, handCardTextPos.get(2).x + 22 - assets.w/2, handCardTextPos.get(2).y);

                assets.setGlyphLayout(card.playerHandPileCardTypesNames__.get(main.playerPsykeySelected).get(2)[2]);
                font.draw(batch, glyphLayout, handCardTextPos2.get(2).x + 90 - assets.w/2, handCardTextPos2.get(2).y);
            }
        }

        if (card.playerHandPileCardTypesNames__.get(main.playerPsykeySelected).size() >= 4){
            batch.draw(assets.cardBackgroundTexture, handCardPos.get(3).x + 16, handCardPos.get(3).y - 16);
            batch.draw(getTexture(3), handCardPos.get(3).x, handCardPos.get(3).y);

            if ((card.playerHandPileCardTypesNames__.get(main.playerPsykeySelected).get(3)).length == 3){
                assets.setGlyphLayout(card.playerHandPileCardTypesNames__.get(main.playerPsykeySelected).get(3)[2]);
                font.draw(batch, glyphLayout, handCardTextPos.get(3).x + 22 - assets.w/2, handCardTextPos.get(3).y);

                assets.setGlyphLayout(card.playerHandPileCardTypesNames__.get(main.playerPsykeySelected).get(3)[2]);
                font.draw(batch, glyphLayout, handCardTextPos2.get(3).x + 90 - assets.w/2, handCardTextPos2.get(3).y);
            }
        }

        if (card.playerHandPileCardTypesNames__.get(main.playerPsykeySelected).size() >= 5){
            batch.draw(assets.cardBackgroundTexture, handCardPos.get(4).x + 16, handCardPos.get(4).y - 16);
            batch.draw(getTexture(4), handCardPos.get(4).x, handCardPos.get(4).y);

            if ((card.playerHandPileCardTypesNames__.get(main.playerPsykeySelected).get(4)).length == 3){
                assets.setGlyphLayout(card.playerHandPileCardTypesNames__.get(main.playerPsykeySelected).get(4)[2]);
                font.draw(batch, glyphLayout, handCardTextPos.get(4).x + 22 - assets.w/2, handCardTextPos.get(4).y);

                assets.setGlyphLayout(card.playerHandPileCardTypesNames__.get(main.playerPsykeySelected).get(4)[2]);
                font.draw(batch, glyphLayout, handCardTextPos2.get(4).x + 90 - assets.w/2, handCardTextPos2.get(4).y);
            }
        }

        if (card.playerHandPileCardTypesNames__.get(main.playerPsykeySelected).size() >= 6){
            batch.draw(assets.cardBackgroundTexture, handCardPos.get(5).x + 16, handCardPos.get(5).y - 16);
            batch.draw(getTexture(5), handCardPos.get(5).x, handCardPos.get(5).y);

            if ((card.playerHandPileCardTypesNames__.get(main.playerPsykeySelected).get(5)).length == 3){
                assets.setGlyphLayout(card.playerHandPileCardTypesNames__.get(main.playerPsykeySelected).get(5)[2]);
                font.draw(batch, glyphLayout, handCardTextPos.get(5).x + 22 - assets.w/2, handCardTextPos.get(5).y);

                assets.setGlyphLayout(card.playerHandPileCardTypesNames__.get(main.playerPsykeySelected).get(5)[2]);
                font.draw(batch, glyphLayout, handCardTextPos2.get(5).x + 90 - assets.w/2, handCardTextPos2.get(5).y);
            }
        }
    }

    public void showStats(){
        if (main.showStatsEnemy){
            batch.draw(assets.statsDisplayTexture, (Main.SCREEN_WIDTH/2f + 160) - assets.statsDisplayTexture.getWidth()/2f, 500);

            batch.draw(assets.statsCounterIconsMirror[enemyPsykey[main.enemyPsykeySelected].idDefenseValue], Main.SCREEN_WIDTH/2f - 96,636);
            batch.draw(assets.statsCounterIconsMirror[enemyPsykey[main.enemyPsykeySelected].egoDefenseValue], Main.SCREEN_WIDTH/2f - 96,584);
            batch.draw(assets.statsCounterIconsMirror[enemyPsykey[main.enemyPsykeySelected].superegoDefenseValue], Main.SCREEN_WIDTH/2f - 96,532);

            batch.draw(assets.statsCounterIcons[enemyPsykey[main.enemyPsykeySelected].idProwessValue], Main.SCREEN_WIDTH/2f + 336,636);
            batch.draw(assets.statsCounterIcons[enemyPsykey[main.enemyPsykeySelected].egoProwessValue], Main.SCREEN_WIDTH/2f + 336,584);
            batch.draw(assets.statsCounterIcons[enemyPsykey[main.enemyPsykeySelected].superegoProwessValue], Main.SCREEN_WIDTH/2f + 336,532);
        }

        if (main.showStatsPlayer){
            batch.draw(assets.statsDisplayTexture, (Main.SCREEN_WIDTH/2f - 160) - assets.statsDisplayTexture.getWidth()/2f, 300);

            batch.draw(assets.statsCounterIconsMirror[playerPsykey[main.playerPsykeySelected].idDefenseValue], Main.SCREEN_WIDTH/2f - 416,436);
            batch.draw(assets.statsCounterIconsMirror[playerPsykey[main.playerPsykeySelected].idDefenseValue], Main.SCREEN_WIDTH/2f - 416,384);
            batch.draw(assets.statsCounterIconsMirror[playerPsykey[main.playerPsykeySelected].idDefenseValue], Main.SCREEN_WIDTH/2f - 416,332);

            batch.draw(assets.statsCounterIcons[playerPsykey[main.playerPsykeySelected].idProwessValue], Main.SCREEN_WIDTH/2f + 16,436);
            batch.draw(assets.statsCounterIcons[playerPsykey[main.playerPsykeySelected].egoProwessValue], Main.SCREEN_WIDTH/2f + 16,384);
            batch.draw(assets.statsCounterIcons[playerPsykey[main.playerPsykeySelected].superegoProwessValue], Main.SCREEN_WIDTH/2f + 16,332);
        }
    }

    public void showCardPlayedDisplay(){
        if (main.showCardPlayed){
            if (encounter.playerCardPlayedPrev != null && encounter.enemyCardPlayedPrev != null){
                batch.draw(assets.cardBackgroundTexture, playerCardDisplayPos.x + 16, playerCardDisplayPos.y - 16);

                batch.draw(assets.getTexture(encounter.playerCardPlayedPrev[0], encounter.playerCardPlayedPrev[1]),
                        playerCardDisplayPos.x, playerCardDisplayPos.y);

                assets.setGlyphLayout(encounter.playerCardPlayedPrev[2]);
                font.draw(batch, glyphLayout, playerCardDisplayPos.x + 23 - assets.w/2, playerCardDisplayPos.y + 182);

                assets.setGlyphLayout(encounter.playerCardPlayedPrev[2]);
                font.draw(batch, glyphLayout, playerCardDisplayPos.x + 91 - assets.w/2, playerCardDisplayPos.y + 27);

                batch.draw(assets.cardBackgroundTexture, enemyCardDisplayPos.x,enemyCardDisplayPos.y - 16);

                batch.draw(assets.getTexture(encounter.enemyCardPlayedPrev[0], encounter.enemyCardPlayedPrev[1]),
                        enemyCardDisplayPos.x - 16, enemyCardDisplayPos.y);

                assets.setGlyphLayout(encounter.enemyCardPlayedPrev[2]);
                font.draw(batch, glyphLayout, enemyCardDisplayPos.x + 7 - assets.w/2, enemyCardDisplayPos.y + 182);

                assets.setGlyphLayout(encounter.enemyCardPlayedPrev[2]);
                font.draw(batch, glyphLayout, enemyCardDisplayPos.x + 75 - assets.w/2, enemyCardDisplayPos.y + 27);
            }
        }
    }

    public void renderBackground(){
        batch.draw(assets.backgrounds[0], -668,-216);

        if (main.currentTurn == Main.ENEMY_TURN){
            batch.draw(assets.spotlight, ((Main.SCREEN_WIDTH / 2f) + (Main.SCREEN_WIDTH / 8f)) - 108,472);
        } else {
            batch.draw(assets.notSpotlight, ((Main.SCREEN_WIDTH / 2f) + (Main.SCREEN_WIDTH / 8f)) - 108,472);
        }

        if (main.currentTurn == Main.PLAYER_TURN){
            batch.draw(assets.spotlight, ((Main.SCREEN_WIDTH / 2f) - (Main.SCREEN_WIDTH / 8f)) - 108,272);
        } else {
            batch.draw(assets.notSpotlight, ((Main.SCREEN_WIDTH / 2f) - (Main.SCREEN_WIDTH / 8f)) - 108,272);
        }
    }

    public void renderLost(){
        batch.draw(assets.backgrounds[3], -668, -266);

        font.setColor(new Color(172/255f, 50/255f, 50/255f, 255/255f));
        font.getData().setScale(1.5f);
        assets.setGlyphLayout("DISINTEGRATED.");
        font.draw(batch, glyphLayout, Main.SCREEN_WIDTH/2f - assets.w/2, Main.SCREEN_HEIGHT/2f + assets.h);
    }
    public void  renderDebugUI(){
//        for (int x = 0; x < touchRegion.cardTouchRegionPolys.size(); x++){
//            shape.polygon(touchRegion.cardTouchRegionPolys.get(x).getTransformedVertices());
//        }
//
//        for (Polygon poly : touchRegion.starterPsykeyPolys){
//            shape.polygon(poly.getTransformedVertices());
//        }

//        shape.polygon(touchRegion.chooseButtonPoly.getTransformedVertices());
//        shape.polygon(touchRegion.enemyPsykeyPoly.getTransformedVertices());

        //shape.circle(touchPos.x, touchPos.y, 20);
    }

    public  int getXPosition(int num){
        return 120 * (Math.round(((card.playerHandPileCardTypesNames__.get(main.playerPsykeySelected).size())/ 2f)) - num);
    }
    public TextureRegion getTexture(int index){
        return assets.getTexture(card.playerHandPileCardTypesNames__.get(main.playerPsykeySelected).get(index)[0], card.playerHandPileCardTypesNames__.get(main.playerPsykeySelected).get(index)[1]);
    }

    public int findStartPos(int cardCount){
        if (cardCount % 2 ==0){
            return 124;
        }

        return 56;
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