package com.ficuno.creature;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Card {
    final static String[] attackCards = new String[]{
            "Maul",
            "Strike",
    };

    Main main;
    Psykey currentPsykey;
    Psykey enemyPsykey;
    TextureRegion[] handPileTextures;
    List<String[]> playerDrawPileCardTypesNames;
    List<String[]> playerHandPileCardTypesNames;
    List<String[]> playerDiscardPileCardTypesNames;
    List<String[]> enemyDrawPileCardTypesNames;
    List<String[]> enemyHandPileCardTypesNames;
    List<String[]> enemyDiscardPileCardTypesNames;

    Assets  assets;
    public Card(Main main){
        this.main = main;
        this.currentPsykey = main.currentPsykey;
        this.enemyPsykey = main.enemyPsykey;
        this.assets = main.assets;

        playerDrawPileCardTypesNames = new ArrayList<>();
        playerHandPileCardTypesNames = new ArrayList<>();
        playerDiscardPileCardTypesNames = new ArrayList<>();
        enemyDrawPileCardTypesNames = new ArrayList<>();
        enemyHandPileCardTypesNames = new ArrayList<>();
        enemyDiscardPileCardTypesNames = new ArrayList<>();

        handPileTextures = new TextureRegion[6];
    }

    public void setDrawPile(){
        playerDrawPileCardTypesNames.addAll(Arrays.asList(currentPsykey.deck));
        enemyDrawPileCardTypesNames.addAll(Arrays.asList(enemyPsykey.deck));
    }

    public void drawStartCards(){
        for (int x = 0; x < 6; x++){
            drawCard();
            enemyDrawCard();
        }
    }

    public void drawCard(){
        int randIndex = (int) (Math.random() * playerDrawPileCardTypesNames.size());

        playerHandPileCardTypesNames.add(playerDrawPileCardTypesNames.get(randIndex));
        playerDrawPileCardTypesNames.remove(randIndex);
    }

    public void reshuffleDrawPile(){
        playerDrawPileCardTypesNames.addAll(playerDiscardPileCardTypesNames);

        playerDiscardPileCardTypesNames.clear();
    }

    public void enemyDrawCard(){
        int randIndex = (int) (Math.random() * enemyDrawPileCardTypesNames.size());

        enemyHandPileCardTypesNames.add(enemyDrawPileCardTypesNames.get(randIndex));
        enemyDrawPileCardTypesNames.remove(randIndex);
    }

    public void enemyReshuffleDrawPile(){
        enemyDrawPileCardTypesNames.addAll(enemyDiscardPileCardTypesNames);

        enemyDiscardPileCardTypesNames.clear();
    }

    public TextureRegion getTexture(String cardType, String cardName){
        switch (cardType) {
            case "Id":
                switch (cardName) {
                    case "Maul":
                        return assets.idCards[0];
                    case "Strike":
                        return assets.idCards[7];
                    case "Battlecry":
                        return assets.idCards[28];
                    case "Slander":
                        return assets.idCards[29];
                    case "Block":
                        return assets.idCards[31];
                }

                break;

            case "Ego":
                switch (cardName) {
                    case "Maul":
                        return assets.egoCards[0];
                    case "Strike":
                        return assets.egoCards[7];
                    case "Battlecry":
                        return assets.egoCards[28];
                    case "Slander":
                        return assets.egoCards[29];
                    case "Block":
                        return assets.egoCards[31];
                }

                break;

            case "Superego":
                switch (cardName) {
                    case "Maul":
                        return assets.superegoCards[0];
                    case "Strike":
                        return assets.superegoCards[7];
                    case "Battlecry":
                        return assets.superegoCards[28];
                    case "Slander":
                        return assets.superegoCards[29];
                    case "Block":
                        return assets.superegoCards[31];
                }

                break;
        }

        return null;
    }

}