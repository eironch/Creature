package com.ficuno.creature;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.ScreenUtils;

public class Renderer {
    Main main;
    OrthographicCamera cam;
    SpriteBatch batch = new SpriteBatch();
    ShapeRenderer shape;
    Card card;
    Controller controller;
    TextureRegion[] split;
    TextureRegion[] mirror;
    // player
    Texture frigidTexture;
    Texture idCardsTexture;
    Texture egoCardsTexture;
    Texture superEgoCardsTexture;
    Animation<TextureRegion> cardSplashArt;
    Texture cardBackgroundTexture;
    Texture systemIconsTexture;
    TextureRegion helpIcon;
    TextureRegion menuIcon;
    TextureRegion bagIcon;
    TextureRegion runIcon;
    TextureRegion drawPileIcon;
    TextureRegion discardPileIcon;
    TextureRegion helpSelectedIcon;
    TextureRegion menuSelectedIcon;
    TextureRegion bagSelectedIcon;
    TextureRegion runSelectedIcon;
    Texture creaturesTexture;

    public Renderer(Main main){
        this.main = main;
        this.controller = main.controller;
        this.shape = new ShapeRenderer();
        this.card = main.card;

        loadAssets();
        createAnimations();
    }

    public void loadAssets(){
        idCardsTexture = new Texture(Gdx.files.internal("idCardsTexture.png"));
        egoCardsTexture = new Texture(Gdx.files.internal("egoCardsTexture.png"));
        superEgoCardsTexture = new Texture(Gdx.files.internal("superEgoCardsTexture.png"));

        cardBackgroundTexture = new Texture(Gdx.files.internal("cardBackgroundTexture.png"));

        systemIconsTexture = new Texture(Gdx.files.internal("systemIconsTexture.png"));
        creaturesTexture = new Texture(Gdx.files.internal("creatures_battle.png"));
    }

    public void createAnimations() {
        int i = 0;
        for (int y = 0; y < 5; y++){
            for (int x = 0; x < 7; x++) {
                splitTexture(idCardsTexture, y, 28, 48);
                card.idCards[i] = new TextureRegion(split[x]);

                splitTexture(egoCardsTexture, y, 28, 48);
                card.egoCards[i] = new TextureRegion(split[x]);

                splitTexture(superEgoCardsTexture, y, 28, 48);
                card.superEgoCards[i] = new TextureRegion(split[x]);

                i++;
                if (i == card.maxCards){
                    break;
                }
            }

            if (i == card.maxCards){
                break;
            }
        }

        splitTexture(systemIconsTexture, 0, 24,24);
        helpIcon = new TextureRegion(split[0]);
        menuIcon = new TextureRegion(split[1]);
        bagIcon = new TextureRegion(split[2]);
        runIcon = new TextureRegion(split[3]);

        splitTexture(systemIconsTexture, 1, 24,24);
        helpSelectedIcon = new TextureRegion(split[0]);
        menuSelectedIcon = new TextureRegion(split[1]);
        bagSelectedIcon = new TextureRegion(split[2]);
        runSelectedIcon = new TextureRegion(split[3]);

        splitTexture(systemIconsTexture, 2, 24,24);
        discardPileIcon = new TextureRegion(split[0]);
        drawPileIcon = new TextureRegion(split[1]);

        splitTexture(creaturesTexture, 0, 320, 180);
        cardSplashArt = new Animation<>(0f, split[0]);
    }

    public void splitTexture(Texture texture, int index, int width, int height){
        split = new TextureRegion(texture).split(width,height)[index];
//        mirror = new TextureRegion(texture).split(40,40)[index];
//        for (TextureRegion region: mirror){
//            region.flip(true, false);
//        }
    }

    public void render(float deltaTime){
        ScreenUtils.clear(0.796f, 0.859f, 0.988f, 1);
        Gdx.gl.glClearColor(132, 126, 135, 255);

        cam = new OrthographicCamera(320,180);
        batch.getProjectionMatrix().setToOrtho2D(0, 0, 320, 180);
        shape.setProjectionMatrix(cam.combined);

        cam.update();

        batch.begin();
        shape.begin(ShapeRenderer.ShapeType.Line);
        shape.setColor(Color.CYAN);

//        for (Projectile projectile : main.projectileArray){
//            shape.polygon(projectile.projectilePoly.getTransformedVertices());
//        }

        batch.draw(creaturesTexture, 0, 0);

        renderCards();
        renderSystemIcons();

        batch.end();
        shape.end();
    }

    public void renderSystemIcons(){
        batch.draw(helpIcon, 3, Main.HEIGHT - (helpIcon.getRegionHeight() + 3));
        batch.draw(menuIcon, Main.WIDTH - (menuIcon.getRegionWidth() + 3),
                Main.HEIGHT - (helpIcon.getRegionHeight() + 3));
        batch.draw(bagIcon, 3, 3);
        batch.draw(runIcon, Main.WIDTH - (runIcon.getRegionWidth() + 3), 3);

        batch.draw(discardPileIcon, 13, 35);
        batch.draw(drawPileIcon, 284, 35);
    }
    public void renderCards(){
        if (card.cardsInHand == null){
            return;
        }

        if (card.cardsInHand[0] != null){
            batch.draw(cardBackgroundTexture,
                    164 - findStartPos(card.countCardsInHand()) - 30
                            * ((int)(Math.round(((card.countCardsInHand())/ 2f)) - 1)), 10);
            batch.draw(card.cardsInHand[0],
                    160 - findStartPos(card.countCardsInHand()) - 30
                            * ((int)(Math.round(((card.countCardsInHand())/ 2f)) - 1)), 14);
        }

        if (card.cardsInHand[1] != null){
            batch.draw(cardBackgroundTexture,
                    164 - findStartPos(card.countCardsInHand()) - 30
                            * ((int)(Math.round(((card.countCardsInHand())/ 2f)) - 2)), 10);
            batch.draw(card.cardsInHand[1],
                    160 - findStartPos(card.countCardsInHand()) - 30
                            * ((int)(Math.round(((card.countCardsInHand())/ 2f)) - 2)), 14);
        }

        if (card.cardsInHand[2] != null){
            batch.draw(cardBackgroundTexture,
                    164 - findStartPos(card.countCardsInHand()) - 30
                            * ((int)(Math.round(((card.countCardsInHand())/ 2f)) - 3)), 10);
            batch.draw(card.cardsInHand[2],
                    160 - findStartPos(card.countCardsInHand()) - 30
                            * ((int)(Math.round(((card.countCardsInHand())/ 2f)) - 3)), 14);
        }

        if (card.cardsInHand[3] != null){
            batch.draw(cardBackgroundTexture,
                    164 - findStartPos(card.countCardsInHand()) - 30
                            * ((int)(Math.round(((card.countCardsInHand())/ 2f)) - 4)), 10);
            batch.draw(card.cardsInHand[3],
                    160 - findStartPos(card.countCardsInHand()) - 30
                            * ((int)(Math.round(((card.countCardsInHand())/ 2f)) - 4)), 14);
        }

        if (card.cardsInHand[4] != null){
            batch.draw(cardBackgroundTexture,
                    164 - findStartPos(card.countCardsInHand()) - 30
                            * ((int)(Math.round(((card.countCardsInHand())/ 2f)) - 5)), 10);
            batch.draw(card.cardsInHand[4],
                    160 - findStartPos(card.countCardsInHand()) - 30
                            * ((int)(Math.round(((card.countCardsInHand())/ 2f)) - 5)), 14);
        }

        if (card.cardsInHand[5] != null){
            batch.draw(cardBackgroundTexture,
                    164 - findStartPos(card.countCardsInHand()) - 30
                            * ((int)(Math.round(((card.countCardsInHand())/ 2f)) - 6)), 10);
            batch.draw(card.cardsInHand[5],
                    160 - findStartPos(card.countCardsInHand()) - 30
                            * ((int)(Math.round(((card.countCardsInHand())/ 2f)) - 6)), 14);
        }
    }

    public int findStartPos(int cardCount){
        if (cardCount % 2 ==0){
            return 31;
        }

        return 14;
    }
}