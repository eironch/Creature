package com.ficuno.creature;

import java.util.Arrays;

public class Encounter {
    Main main;
    Card card;
    String enemyCardPlay;
    Assets assets;
    public Encounter(Main main){
        this.main = main;
        this.card = main.card;
        this.assets = main.assets;

        startFight();
    }

    public void startFight(){
        card.setDrawPile();
        card.drawStartCards();

        enemyTurn();
    }

    public void enemyTurn(){
        while (card.enemyHandPileCardTypesNames.size() > 0){
            int randIndex = (int) (Math.random() * card.enemyHandPileCardTypesNames.size());

            if (Arrays.asList(Card.attackCards).contains(card.enemyHandPileCardTypesNames.get(randIndex)[1])){
                enemyCardPlay = card.enemyHandPileCardTypesNames.get(randIndex)[1];
                main.turn = Main.PLAYER_TURN;

                main.enemyPlayIcon = assets.enemyAttackPlayIcon;
                System.out.println("Enemy has chosen to attack");
                break;
            }
        }
    }
}
