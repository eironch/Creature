package com.ficuno.PSYKIES;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

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
    Texture encounterIconsTexture;
    TextureRegion helpIcon;
    TextureRegion menuIcon;
    TextureRegion bagIcon;
    TextureRegion actionsIconClose;
    TextureRegion drawPileIcon;
    TextureRegion discardPileIcon;
    TextureRegion enemyAttackPlayIcon;
    TextureRegion enemyDefendPlayIcon;
    TextureRegion enemySpecialPlayIcon;
    TextureRegion playerAttackPlayIcon;
    TextureRegion playerDefendPlayIcon;
    TextureRegion playerSpecialPlayIcon;
    TextureRegion spotlight;
    TextureRegion notSpotlight;
    TextureRegion[] backgrounds = new TextureRegion[4];
    TextureRegion playerUIBar;
    TextureRegion enemyUIBar;
    TextureRegion[] idCards;
    TextureRegion[] egoCards;
    TextureRegion[] superegoCards;
    TextureRegion[] psykeyTextures = new TextureRegion[Creature.maxPsykies];
    TextureRegion[] psykeyTexturesMirror = new TextureRegion[Creature.maxPsykies];
    Texture actionsIconOpenTexture;
    TextureRegion actionsIconOpen;
    Texture statsDisplayTexture;
    Texture statsCounterTexture;
    TextureRegion[] statsCounterPositiveIcons;
    TextureRegion[] statsCounterPositiveIconsMirror;
    TextureRegion[] statsCounterNegativeIcons;
    TextureRegion[] statsCounterNegativeIconsMirror;
    float w;
    float h;
    FreeTypeFontGenerator fontGenerator;
    FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    BitmapFont font;
    GlyphLayout glyphLayout;
    Texture chooseButtonTexture;
    TextureRegion choiceButtonIcon;
    TextureRegion choiceButtonBackground;
    TextureRegion playerDefDownIcon;
    TextureRegion playerDefUpIcon;
    TextureRegion playerProwDownIcon;
    TextureRegion playerProwUpIcon;
    TextureRegion enemyDefDownIcon;
    TextureRegion enemyDefUpIcon;
    TextureRegion enemyProwDownIcon;
    TextureRegion enemyProwUpIcon;
    Sound cardSlideSound1;
    Sound cardSlideSound2;
    Sound cardPlaceSound1;
    Sound cardPlaceSound2;
    Sound uiSound;
    Sound encounterLoseSound;
    Sound encounterWonSound;
    Sound chooseSound;
    Music encounterMusic;
    public Assets(){
        idCards = new TextureRegion[Main.maxCards];
        egoCards = new TextureRegion[Main.maxCards];
        superegoCards = new TextureRegion[Main.maxCards];
        statsCounterPositiveIcons = new TextureRegion[10];
        statsCounterPositiveIconsMirror = new TextureRegion[10];
        statsCounterNegativeIcons = new TextureRegion[10];
        statsCounterNegativeIconsMirror = new TextureRegion[10];

        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 25;
        fontParameter.borderWidth = 2;
        fontParameter.borderColor = Color.BLACK;
        fontParameter.color = Color.WHITE;
        font = fontGenerator.generateFont(fontParameter);
        glyphLayout = new GlyphLayout();

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
        encounterIconsTexture = new Texture(Gdx.files.internal("encounterIconsTexture.png"));
        actionsIconOpenTexture = new Texture(Gdx.files.internal("actionsIconTexture.png"));
        statsDisplayTexture = new Texture(Gdx.files.internal("statsDisplayTexture.png"));
        statsCounterTexture = new Texture(Gdx.files.internal("statsCounterTexture.png"));
        chooseButtonTexture = new Texture(Gdx.files.internal("chooseButtonTexture.png"));

        cardSlideSound1 = Gdx.audio.newSound(Gdx.files.internal("cardSlideSound1.ogg"));
        cardSlideSound2 = Gdx.audio.newSound(Gdx.files.internal("cardSlideSound2.ogg"));
        cardPlaceSound1 = Gdx.audio.newSound(Gdx.files.internal("cardPlaceSound1.ogg"));
        cardPlaceSound2 = Gdx.audio.newSound(Gdx.files.internal("cardPlaceSound2.ogg"));
        encounterLoseSound = Gdx.audio.newSound(Gdx.files.internal("encounterLoseSound.mp3"));
        encounterWonSound = Gdx.audio.newSound(Gdx.files.internal("encounterWonSound.mp3"));
        chooseSound = Gdx.audio.newSound(Gdx.files.internal("chooseSound.mp3"));

        uiSound = Gdx.audio.newSound(Gdx.files.internal("uiSound.mp3"));
        encounterMusic = Gdx.audio.newMusic(Gdx.files.internal("encounterMusic.wav"));
    }

    public void createAssets() {
        int i = 0;
        for (int y = 0; y < 6; y++){
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
            backgrounds[y] = new TextureRegion(splitTexture(encounterBgTexture, y, 3480, 1252)[0]);
        }

        for (int x = 0; x < 10; x++){
            statsCounterPositiveIcons[x] = new TextureRegion(splitTexture(statsCounterTexture, x, 80, 16)[0]);
            statsCounterPositiveIconsMirror[x] = new TextureRegion(mirrorTexture(statsCounterTexture, x, 80, 16)[0]);
            statsCounterNegativeIcons[x] = new TextureRegion(splitTexture(statsCounterTexture, x + 10, 80, 16)[0]);
            statsCounterNegativeIconsMirror[x] = new TextureRegion(mirrorTexture(statsCounterTexture, x + 10, 80, 16)[0]);
        }

        helpIcon = new TextureRegion(splitTexture(systemIconsTexture, 0, 96,96)[0]);
        menuIcon = new TextureRegion(splitTexture(systemIconsTexture, 0, 96,96)[1]);
        bagIcon = new TextureRegion(splitTexture(systemIconsTexture, 0, 96,96)[2]);
        actionsIconClose = new TextureRegion(splitTexture(systemIconsTexture, 0, 96,96)[3]);
        actionsIconOpen = new TextureRegion(actionsIconOpenTexture);

        helpSelectedIcon = new TextureRegion(splitTexture(systemIconsTexture, 1, 96,96)[0]);
        menuSelectedIcon = new TextureRegion(splitTexture(systemIconsTexture, 1, 96,96)[1]);
        bagSelectedIcon = new TextureRegion(splitTexture(systemIconsTexture, 1, 96,96)[2]);
        runSelectedIcon = new TextureRegion(splitTexture(systemIconsTexture, 1, 96,96)[3]);

        discardPileIcon = new TextureRegion(splitTexture(systemIconsTexture, 2, 96,96)[0]);
        drawPileIcon = new TextureRegion(splitTexture(systemIconsTexture, 2, 96,96)[1]);

        playerUIBar = new TextureRegion(splitTexture(uiBarsTexture, 0, 1056, 160)[0]);
        enemyUIBar = new TextureRegion(splitTexture(uiBarsTexture, 1, 1056, 160)[0]);

        enemyAttackPlayIcon = new TextureRegion(splitTexture(encounterIconsTexture, 0, 80, 64)[0]);
        enemyDefendPlayIcon = new TextureRegion(splitTexture(encounterIconsTexture, 0, 80, 64)[1]);
        enemySpecialPlayIcon = new TextureRegion(splitTexture(encounterIconsTexture, 0, 80, 64)[2]);

        playerAttackPlayIcon = new TextureRegion(splitTexture(encounterIconsTexture, 1, 80, 64)[0]);
        playerDefendPlayIcon = new TextureRegion(splitTexture(encounterIconsTexture, 1, 80, 64)[1]);
        playerSpecialPlayIcon = new TextureRegion(splitTexture(encounterIconsTexture, 1, 80, 64)[2]);

        choiceButtonIcon = new TextureRegion(splitTexture(chooseButtonTexture, 0, 320,80)[0]);
        choiceButtonBackground = new TextureRegion(splitTexture(chooseButtonTexture, 1, 320,80)[0]);

        spotlight = new TextureRegion(splitTexture(spotlightTexture, 0, 216,80)[0]);
        notSpotlight = new TextureRegion(splitTexture(spotlightTexture, 1, 216,80)[0]);

        playerDefDownIcon = new TextureRegion(splitTexture(encounterIconsTexture, 2, 80, 64)[0]);
        playerDefUpIcon = new TextureRegion(splitTexture(encounterIconsTexture, 2, 80, 64)[1]);
        playerProwDownIcon = new TextureRegion(splitTexture(encounterIconsTexture, 4, 80, 64)[0]);
        playerProwUpIcon = new TextureRegion(splitTexture(encounterIconsTexture, 4, 80, 64)[1]);

        enemyDefDownIcon = new TextureRegion(splitTexture(encounterIconsTexture, 3, 80, 64)[1]);
        enemyDefUpIcon = new TextureRegion(splitTexture(encounterIconsTexture, 3, 80, 64)[0]);
        enemyProwDownIcon = new TextureRegion(splitTexture(encounterIconsTexture, 5, 80, 64)[1]);
        enemyProwUpIcon = new TextureRegion(splitTexture(encounterIconsTexture, 5, 80, 64)[0]);
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

    public void setGlyphLayout(int text){
        glyphLayout.setText(font, Integer.toString(text));
        w = glyphLayout.width;
        h = glyphLayout.height;
    }

    public TextureRegion getStatsTexture(int statValue, boolean mirrored){
        if (mirrored){
            if (statValue >= 0){
                return statsCounterPositiveIconsMirror[statValue];
            } else {
                return statsCounterNegativeIconsMirror[-statValue];
            }

        } else {
            if (statValue >= 0){
                return statsCounterPositiveIcons[statValue];
            } else {
                return statsCounterNegativeIcons[-statValue];
            }
        }
    }

    public TextureRegion getCardTexture(String cardType, String cardName){
        switch (cardType) {
            case "Id":
                switch (cardName) {
                    case "Maul":
                        return idCards[0];
                    case "Strike":
                        return idCards[7];
                    case "Arcane Blast":
                        return idCards[21];
                    case "Battlecry":
                        return idCards[28];
                    case "Slander":
                        return idCards[29];
                    case "Block":
                        return idCards[35];
                }

                break;

            case "Ego":
                switch (cardName) {
                    case "Maul":
                        return egoCards[0];
                    case "Strike":
                        return egoCards[7];
                    case "Arcane Blast":
                        return egoCards[21];
                    case "Battlecry":
                        return egoCards[28];
                    case "Slander":
                        return egoCards[29];
                    case "Block":
                        return egoCards[35];
                }

                break;

            case "Superego":
                switch (cardName) {
                    case "Maul":
                        return superegoCards[0];
                    case "Strike":
                        return superegoCards[7];
                    case "Arcane Blast":
                        return superegoCards[21];
                    case "Battlecry":
                        return superegoCards[28];
                    case "Slander":
                        return superegoCards[29];
                    case "Block":
                        return superegoCards[35];
                }

                break;
        }

        return null;
    }
}
