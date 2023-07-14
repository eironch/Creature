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
    Psykey[] playerPsykey;
    Psykey[] enemyPsykey;
    Renderer renderer;

    public Encounter(Main main){
        this.main = main;
        this.card = main.card;
        this.playerPsykey = main.playerPsykey;
        this.enemyPsykey = main.enemyPsykey;
        this.renderer = main.renderer;
        this.assets = main.assets;
    }

    public void update(float deltaTime){
        if (main.turnState != Main.MID_TURN && main.turnState != Main.NEXT_TURN && main.turnState != Main.PRE_TURN){
            return;
        }

        if (main.turnState == Main.NEXT_TURN){
            handleAttack(enemyPsykey[main.enemyPsykeySelected], playerCardPlayed[0], playerCardPlayed[1], Integer.parseInt(playerCardPlayed[2]), enemyCardPlayed[0]);
            handleAttack(playerPsykey[main.playerPsykeySelected], enemyCardPlayed[0], enemyCardPlayed[1], Integer.parseInt(enemyCardPlayed[2]), playerCardPlayed[0]);

            playerPsykey[main.playerPsykeySelected].block = 0;
            enemyPsykey[main.enemyPsykeySelected].block = 0;
            enemyCardPlayedPrev = enemyCardPlayed;
            playerCardPlayedPrev = playerCardPlayed;

            if (card.playerDrawPileCardTypesNames__.get(main.playerPsykeySelected).size() == 0){
                card.reshuffleDrawPile(main.playerPsykeySelected);
            }
            card.drawCard(main.playerPsykeySelected);

            if (card.enemyDrawPileCardTypesNames__.get(main.enemyPsykeySelected).size() == 0){
                card.enemyReshuffleDrawPile(main.enemyPsykeySelected);
            }
            card.enemyDrawCard(main.enemyPsykeySelected);

            main.showOverlay = true;
            main.showTurnDisplay = true;
            main.showCardPlayed = true;
            main.overlayTimer = 0;

            main.turnState = Main.PRE_TURN;
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
        if (playerPsykey[Main.FIRST] != null) {
            card.setDrawPile(Main.FIRST);
        }

        if (playerPsykey[Main.SECOND] != null) {
            card.setDrawPile(Main.SECOND);
        }
        card.setEnemyDrawPile(Main.FIRST);
        card.setHandCards();
        enemyTurn();
    }

    public void enemyTurn(){
        if (card.enemyHandPileCardTypesNames__.get(main.enemyPsykeySelected).size() == 0){
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
        for (int i = 0; i < card.enemyHandPileCardTypesNames__.get(main.enemyPsykeySelected).size(); i++) {
            if (Arrays.asList(Card.specialCards).contains(card.enemyHandPileCardTypesNames__.get(main.enemyPsykeySelected).get(i)[1])) {
                enemyCardPlayed = card.enemyHandPileCardTypesNames__.get(main.enemyPsykeySelected).get(i);
                card.enemyDiscardPileCardTypesNames__.get(main.enemyPsykeySelected).add(card.enemyHandPileCardTypesNames__.get(main.enemyPsykeySelected).get(i));
                card.enemyHandPileCardTypesNames__.get(main.enemyPsykeySelected).remove(i);

                main.enemyPlayIcon[main.enemyPsykeySelected] = assets.enemySpecialPlayIcon;

                handleEnemyTurnEnd();

                return;
            }
        }

        enemyTurn();
    }

    public void findDefendCard() {
        for (int i = 0; i < card.enemyHandPileCardTypesNames__.get(main.enemyPsykeySelected).size(); i++) {
            if (Arrays.asList(Card.defendCards).contains(card.enemyHandPileCardTypesNames__.get(main.enemyPsykeySelected).get(i)[1])) {
                enemyCardPlayed = card.enemyHandPileCardTypesNames__.get(main.enemyPsykeySelected).get(i);
                card.enemyDiscardPileCardTypesNames__.get(main.enemyPsykeySelected).add(card.enemyHandPileCardTypesNames__.get(main.enemyPsykeySelected).get(i));
                card.enemyHandPileCardTypesNames__.get(main.enemyPsykeySelected).remove(i);

                main.enemyPlayIcon[main.enemyPsykeySelected] = assets.enemyDefendPlayIcon;

                if (Arrays.asList(Card.defendCards).contains(enemyCardPlayed[1])){
                    enemyPsykey[main.enemyPsykeySelected].block += Integer.parseInt(enemyCardPlayed[2]);
                }

                handleEnemyTurnEnd();

                return;
            }
        }

        enemyTurn();
    }

    public void findAttackCard(){
        for(int i = 0; i < card.enemyHandPileCardTypesNames__.get(main.enemyPsykeySelected).size(); i++){
            if (Arrays.asList(Card.attackCards).contains(card.enemyHandPileCardTypesNames__.get(main.enemyPsykeySelected).get(i)[1])){
                enemyCardPlayed = card.enemyHandPileCardTypesNames__.get(main.enemyPsykeySelected).get(i);
                card.enemyDiscardPileCardTypesNames__.get(main.enemyPsykeySelected).add(card.enemyHandPileCardTypesNames__.get(main.enemyPsykeySelected).get(i));
                card.enemyHandPileCardTypesNames__.get(main.enemyPsykeySelected).remove(i);

                main.enemyPlayIcon[main.enemyPsykeySelected] = assets.enemyAttackPlayIcon;

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
            main.turnState = Main.NEXT_TURN;

        } else {
            main.turnState = Main.PLAYER_TURN;
            main.currentTurn = Main.PLAYER_TURN;

            main.showOverlay = true;
            main.showTurnDisplay = true;
            main.overlayTimer = 0;
        }
    }
}
