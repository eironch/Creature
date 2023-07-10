package com.ficuno.creature;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
    Texture idCardsTexture;
    Texture egoCardsTexture;
    Texture superegoCardsTexture;
    Texture systemIconsTexture;
    TextureRegion helpSelectedIcon;
    TextureRegion menuSelectedIcon;
    TextureRegion bagSelectedIcon;
    TextureRegion runSelectedIcon;
    Texture psykiesTexture;
    Texture encounterBgTexture;
    Texture uiBarsTexture;
    Texture descBoxTexture;
    Texture cardBackgroundTexture;
    Texture spotlightTexture;
    TextureRegion helpIcon;
    TextureRegion menuIcon;
    TextureRegion bagIcon;
    TextureRegion actionsIcon;
    TextureRegion drawPileIcon;
    TextureRegion discardPileIcon;
    TextureRegion[] encounterBgTextures = new TextureRegion[4];
    TextureRegion yourUIBar;
    TextureRegion enemyUIBar;
    TextureRegion[] idCards;
    TextureRegion[] egoCards;
    TextureRegion[] superegoCards;
    TextureRegion[] psykeyTextures = new TextureRegion[Creature.maxPsykies];
    TextureRegion[] psykeyTexturesMirror = new TextureRegion[Creature.maxPsykies];
    public Assets(){
        idCards = new TextureRegion[Main.maxCards];
        egoCards = new TextureRegion[Main.maxCards];
        superegoCards = new TextureRegion[Main.maxCards];

        loadAssets();
        createAssets();
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

        Texture psykiesTexture = new Texture(Gdx.files.internal("psykiesTexture.png"));
    }

    public void createAssets() {
        int i = 0;
        for (int y = 0; y < 5; y++){
            for (int x = 0; x < 7; x++) {
                idCards[i] = new TextureRegion(splitTexture(idCardsTexture, y, 112, 192)[x]);

                egoCards[i] = new TextureRegion(splitTexture(egoCardsTexture, y, 112, 192)[x]);

                superegoCards[i] = new TextureRegion(splitTexture(superegoCardsTexture, y, 112, 192)[x]);

                i++;
                if (i == Main.maxCards){
                    break;
                }
            }

            if (i == Main.maxCards){
                break;
            }
        }

        for (int x = 0; x < Creature.maxPsykies; x++){
            psykeyTextures[x] = new TextureRegion(splitTexture(psykiesTexture, 0, 160, 160)[x]);
            psykeyTexturesMirror[x] = new TextureRegion(mirrorTexture(psykiesTexture, 0, 160, 160)[x]);
        }

        for (int y = 0; y < 4; y++){
            encounterBgTextures[y] = new TextureRegion(splitTexture(encounterBgTexture, y, 3480, 1252)[0]);
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
}
