package com.ficuno.creature;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
import java.util.List;

public class Card {
    Main main;
    Psykey currentPsykey;
    Psykey enemyPsykey;
    int maxCards = 32;
    TextureRegion[] cardsInHand;
    TextureRegion[] idCards;
    TextureRegion[] egoCards;
    TextureRegion[] superegoCards;
    List<String> drawPileCardNames;
    List<String> drawPileCardTypes;
    List<String> handPileCardNames;
    List<String> handPileCardTypes;
    List<String> discardPileCardNames;
    List<String> discardPileCardTypes;
    public Card(Main main){
        this.main = main;
        this.currentPsykey = main.currentPsykey;
        this.enemyPsykey = main.enemyPsykey;

        idCards = new TextureRegion[maxCards];
        egoCards = new TextureRegion[maxCards];
        superegoCards = new TextureRegion[maxCards];
        drawPileCardNames = new ArrayList<>();
        drawPileCardTypes = new ArrayList<>();
        handPileCardNames = new ArrayList<>();
        handPileCardTypes = new ArrayList<>();
        discardPileCardNames = new ArrayList<>();
        discardPileCardTypes = new ArrayList<>();
        cardsInHand = new TextureRegion[6];
    }

    public void setDrawPile(){
        for (String[] card: currentPsykey.deck){
            drawPileCardNames.add(card[0]);
            drawPileCardTypes.add(card[1]);
        }
    }

    public TextureRegion getTexture(String cardName, String cardType){
        switch (cardType) {
            case "Id":
                switch (cardName) {
                    case "Maul":
                        return idCards[0];
                    case "Strike":
                        return idCards[7];
                    case "Battlecry":
                        return idCards[28];
                    case "Slander":
                        return idCards[29];
                    case "Block":
                        return idCards[31];
                }

                break;

            case "Ego":
                switch (cardName) {
                    case "Maul":
                        return egoCards[0];
                    case "Strike":
                        return egoCards[7];
                    case "Battlecry":
                        return egoCards[28];
                    case "Slander":
                        return egoCards[29];
                    case "Block":
                        return egoCards[31];
                }

                break;

            case "Superego":
                switch (cardName) {
                    case "Maul":
                        return superegoCards[0];
                    case "Strike":
                        return superegoCards[7];
                    case "Battlecry":
                        return superegoCards[28];
                    case "Slander":
                        return superegoCards[29];
                    case "Block":
                        return superegoCards[31];
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
        int randIndex = (int) (Math.random() * drawPileCardNames.size());
        System.out.println(randIndex + " " + drawPileCardNames.get(randIndex) + " " + drawPileCardTypes.get(randIndex));

        handPileCardNames.add(drawPileCardNames.get(randIndex));
        handPileCardTypes.add(drawPileCardTypes.get(randIndex));

        drawPileCardNames.remove(randIndex);
        drawPileCardTypes.remove(randIndex);
    }

    public void reshuffleDrawPile(){
        drawPileCardNames.addAll(discardPileCardNames);
        drawPileCardTypes.addAll(discardPileCardTypes);

        discardPileCardNames.clear();
        discardPileCardTypes.clear();
    }
}