package com.ficuno.creature;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Card {
    Main main;
    Psykey currentPsykey;
    Psykey enemyPsykey;
    TextureRegion[] cardsInHand;
    List<String[]> yourDrawPileCardTypesNames;
    List<String[]> yourHandPileCardTypesNames;
    List<String[]> yourDiscardPileCardTypesNames;
    Assets  assets;
    public Card(Main main){
        this.main = main;
        this.currentPsykey = main.currentPsykey;
        this.enemyPsykey = main.enemyPsykey;
        this.assets = main.assets;

        yourDrawPileCardTypesNames = new ArrayList<>();
        yourHandPileCardTypesNames = new ArrayList<>();
        yourDiscardPileCardTypesNames = new ArrayList<>();

        cardsInHand = new TextureRegion[6];
    }

    public void setDrawPile(){
        yourDrawPileCardTypesNames.addAll(Arrays.asList(currentPsykey.deck));
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

    public void drawStartCards(){
        for (int x = 0; x < 6; x++){
            drawCard();
        }
    }

    public void drawCard(){
        int randIndex = (int) (Math.random() * yourDrawPileCardTypesNames.size());

        yourHandPileCardTypesNames.add(yourDrawPileCardTypesNames.get(randIndex));
        yourDrawPileCardTypesNames.remove(randIndex);
    }

    public void reshuffleDrawPile(){
        yourDrawPileCardTypesNames.addAll(yourDiscardPileCardTypesNames);

        yourDiscardPileCardTypesNames.clear();
    }
}