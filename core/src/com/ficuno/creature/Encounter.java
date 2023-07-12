package com.ficuno.creature;

import com.badlogic.gdx.math.MathUtils;

import java.util.Arrays;
import java.util.Objects;

public class Encounter {
    Main main;
    Card card;
    String[] enemyCardPlayed;
    String[] playerCardPlayed;
    String[] enemyCardPlayedPrev;
    String[] playerCardPlayedPrev;
    Assets assets;
    Psykey playerPsykey;
    Psykey enemyPsykey;
    Psykey currentPsykeyRef;
    Psykey enemyPsykeyRef;
    Renderer renderer;
    boolean enemyTurnCalled = false;
    public Encounter(Main main){
        this.main = main;
        this.card = main.card;
        this.playerPsykey = main.currentPsykey;
        this.enemyPsykey = main.enemyPsykey;
        this.currentPsykeyRef = main.currentPsykey;
        this.enemyPsykeyRef = main.enemyPsykey;
        this.renderer = main.renderer;
        this.assets = main.assets;

        startFight();
    }

    public void update(float deltaTime){
        if (main.turnState != Main.PRE_TURN && main.turnState != Main.NEXT_TURN){
            if (main.currentTurn == Main.ENEMY_TURN) {
                main.showTurnDisplay = true;
                main.overlayTimer = 0;
                enemyTurn();
            }

            return;
        }

        if (main.turnState == Main.NEXT_TURN){
            handleAttack(enemyPsykey, playerCardPlayed[0], playerCardPlayed[1], Integer.parseInt(playerCardPlayed[2]), enemyCardPlayed[0]);
            handleAttack(playerPsykey, enemyCardPlayed[0], enemyCardPlayed[1], Integer.parseInt(enemyCardPlayed[2]), playerCardPlayed[0]);

            playerPsykey.block = 0;
            enemyPsykey.block = 0;
            enemyCardPlayedPrev = enemyCardPlayed;
            playerCardPlayedPrev = playerCardPlayed;
            main.enemyPlayIcon = null;
            main.playerPlayIcon = null;
            enemyCardPlayed = null;
            playerCardPlayed = null;

            if (card.playerDrawPileCardTypesNames.size() == 0){
                card.reshuffleDrawPile();
            }
            card.drawCard();

            if (card.enemyDrawPileCardTypesNames.size() == 0){
                card.enemyReshuffleDrawPile();
            }
            card.enemyDrawCard();

            main.showTurnDisplay = true;
            main.overlayTimer = 0;
            main.turnState = main.currentTurn;
        }
    }

    public void handleAttack(Psykey psykey, String cardType, String cardName, int cardDamage, String otherCardType){
        if (Arrays.asList(Card.attackCards).contains(cardName)){
            switch (cardType){
                case "Id":
                    psykey.healthPoints = MathUtils.clamp(
                            psykey.healthPoints - handleDefend(
                                    psykey, cardType, cardDamage + psykey.idProwessValue, otherCardType),
                            0, 1000);
                    break;

                case "Ego":
                    psykey.healthPoints = MathUtils.clamp(
                            psykey.healthPoints - handleDefend(
                                    psykey, cardType, cardDamage + psykey.egoProwessValue, otherCardType),
                            0, 1000);
                    break;

                case "Superego":
                    psykey.healthPoints = MathUtils.clamp(
                            psykey.healthPoints - handleDefend(
                                    psykey, cardType,  cardDamage + psykey.superegoProwessValue, otherCardType),
                            0, 1000);
                    break;
            }
        }
    }

    public int handleDefend(Psykey psykey, String cardType, int cardDamage, String otherCardType){
        int damage;

        if (Objects.equals(cardType, otherCardType)){
            damage = cardDamage - (psykey.block + 2);
        } else {
            damage = cardDamage - psykey.block;
        }


        switch (cardType){
            case "Id":
                if (psykey.idDefenseValue < damage){
                    return damage - psykey.idDefenseValue;
                } else {
                    return 0;
                }

            case "Ego":
                if (psykey.egoDefenseValue < damage){
                    return damage - psykey.egoDefenseValue;
                } else {
                    return 0;
                }

            case "Superego":
                if (psykey.superegoDefenseValue < damage){
                    return damage - psykey.superegoDefenseValue;
                } else {
                    return 0;
                }
        }

        return 0;
    }

    public void startFight(){
        card.setDrawPile();
        card.drawStartCards();
    }

    public void enemyTurn(){
        if (card.enemyHandPileCardTypesNames.size() == 0){
            return;
        }

        int randNum = (int)(Math.random() * 7);

        switch (randNum){
            case 0:
                findDefendCard();

                break;

            case 1:
                findAttackCard();

                break;

            case 2:
                findSpecialCard();

                break;

            case 3:
                randNum = (int)(Math.random() * 2);

                if (randNum== 0){
                    findSpecialCard();
                } else {
                    findAttackCard();
                }

                break;

            case 4:
            case 5:
            case 6:
                if (main.playerTurn == Main.DONE){
                    findDefendCard();
                } else {
                    findAttackCard();
                }

                break;
        }
    }

    public  void findSpecialCard(){
        for (int i = 0; i < card.enemyHandPileCardTypesNames.size(); i++) {
            if (Arrays.asList(Card.specialCards).contains(card.enemyHandPileCardTypesNames.get(i)[1])) {
                enemyCardPlayed = card.enemyHandPileCardTypesNames.get(i);
                card.enemyDiscardPileCardTypesNames.add(card.enemyHandPileCardTypesNames.get(i));
                card.enemyHandPileCardTypesNames.remove(i);

                main.enemyPlayIcon = assets.enemySpecialPlayIcon;

                handleEnemyTurnEnd();

                return;
            }
        }

        enemyTurn();
    }

    public void findDefendCard() {
        for (int i = 0; i < card.enemyHandPileCardTypesNames.size(); i++) {
            if (Arrays.asList(Card.defendCards).contains(card.enemyHandPileCardTypesNames.get(i)[1])) {
                enemyCardPlayed = card.enemyHandPileCardTypesNames.get(i);
                card.enemyDiscardPileCardTypesNames.add(card.enemyHandPileCardTypesNames.get(i));
                card.enemyHandPileCardTypesNames.remove(i);

                main.enemyPlayIcon = assets.enemyDefendPlayIcon;

                if (Arrays.asList(Card.defendCards).contains(enemyCardPlayed[1])){
                    enemyPsykey.block += Integer.parseInt(enemyCardPlayed[2]);
                }

                handleEnemyTurnEnd();

                return;
            }
        }

        enemyTurn();
    }

    public void findAttackCard(){
        for(int i = 0; i < card.enemyHandPileCardTypesNames.size(); i++){
            if (Arrays.asList(Card.attackCards).contains(card.enemyHandPileCardTypesNames.get(i)[1])){
                enemyCardPlayed = card.enemyHandPileCardTypesNames.get(i);
                card.enemyDiscardPileCardTypesNames.add(card.enemyHandPileCardTypesNames.get(i));
                card.enemyHandPileCardTypesNames.remove(i);

                main.enemyPlayIcon = assets.enemyAttackPlayIcon;

                handleEnemyTurnEnd();

                return;
            }
        }

        enemyTurn();
    }

    public void handleEnemyTurnEnd(){
        main.enemyTurn = Main.DONE;

        if (main.playerTurn == Main.DONE) {
            main.enemyTurn = Main.NOT_DONE;
            main.playerTurn = Main.NOT_DONE;
            main.turnState = Main.PRE_TURN;

        } else {
            main.turnState = Main.PLAYER_TURN;
            main.currentTurn = Main.PLAYER_TURN;
            main.showTurnDisplay = true;
            main.overlayTimer = 0;
        }
    }
}
