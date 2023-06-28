package com.ficuno.creature;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Card {
    Main main;
    int maxCards = 32;
    TextureRegion[] cardsInHand;
    TextureRegion[] idCards;
    TextureRegion[] egoCards;
    TextureRegion[] superEgoCards;
    public Card(Main main){
        this.main = main;

        idCards = new TextureRegion[maxCards];
        egoCards = new TextureRegion[maxCards];
        superEgoCards = new TextureRegion[maxCards];
    }

    public void shuffleDeck(){
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            cardsInHand = new TextureRegion[]{idCards[(int)(Math.random() * maxCards)],
                    egoCards[(int)(Math.random() * maxCards)],
                    idCards[(int)(Math.random() * maxCards)],
                    superEgoCards[(int)(Math.random() * maxCards)],
                    superEgoCards[(int)(Math.random() * maxCards)],
                    egoCards[(int)(Math.random() * maxCards)]
            };

            arrangeCards();
        }
    }

    public void arrangeCards(){
        if (cardsInHand[1] != null && cardsInHand[0] == null){
            arrangeCardOrder(cardsInHand[1]);
            cardsInHand[1] = null;
        }

        if (cardsInHand[2] != null && (cardsInHand[0] == null || cardsInHand[1] == null)){
            arrangeCardOrder(cardsInHand[2]);
            cardsInHand[2] = null;
        }

        if (cardsInHand[3] != null && (cardsInHand[0] == null
                || cardsInHand[1] == null || cardsInHand[2] == null)){
            arrangeCardOrder(cardsInHand[3]);
            cardsInHand[3] = null;
        }

        if (cardsInHand[4] != null && (cardsInHand[0] == null || cardsInHand[1] == null
                || cardsInHand[2] == null || cardsInHand[3] == null)){
            arrangeCardOrder(cardsInHand[4]);
            cardsInHand[4] = null;
        }

        if (cardsInHand[5] != null && (cardsInHand[0] == null || cardsInHand[1] == null
                || cardsInHand[2] == null || cardsInHand[3] == null || cardsInHand[4] == null)){
            arrangeCardOrder(cardsInHand[5]);
            cardsInHand[5] = null;
        }
    }

    public void arrangeCardOrder(TextureRegion card){
        if (cardsInHand[0] == null){
            cardsInHand[0] = card;
        } else if (cardsInHand[1] == null){
            cardsInHand[1] = card;
        } else if (cardsInHand[2] == null){
            cardsInHand[2] = card;
        } else if (cardsInHand[3] == null){
            cardsInHand[3] = card;
        } else if (cardsInHand[4] == null){
            cardsInHand[4] = card;
        } else if (cardsInHand[5] == null){
            cardsInHand[5] = card;
        }
    }

    public int countCardsInHand(){
        int cardInHand = 0;

        if (cardsInHand[0] != null){
            cardInHand++;
        }
        if (cardsInHand[1] != null){
            cardInHand++;
        }
        if (cardsInHand[2] != null){
            cardInHand++;
        }
        if (cardsInHand[3] != null){
            cardInHand++;
        }
        if (cardsInHand[4] != null){
            cardInHand++;
        }
        if (cardsInHand[5] != null){
            cardInHand++;
        }

        return cardInHand;
    }
}