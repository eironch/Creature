package com.ficuno.PSYKIES;

import com.badlogic.gdx.math.MathUtils;
import java.util.Arrays;
import java.util.Objects;

public class Encounter {
    Main main;
    Cards cards;
    String[] enemyCardPlayed;
    String[] playerCardPlayed;
    String[] enemyCardPlayedPrev;
    String[] playerCardPlayedPrev;
    Assets assets;
    Psykey[] playerPsykey;
    Psykey[] enemyPsykey;
    Psykey[] playerPsykeyRef;
    Psykey[] enemyPsykeyRef;
    Renderer renderer;

    public Encounter(Main main){
        this.main = main;
        this.cards = main.cards;
        this.playerPsykey = main.playerPsykey;
        this.enemyPsykey = main.enemyPsykey;
        this.playerPsykeyRef = main.playerPsykeyRef;
        this.enemyPsykeyRef = main.enemyPsykeyRef;
        this.renderer = main.renderer;
        this.assets = main.assets;
    }

    public void update(){
        if (main.turnState != Main.MID_TURN && main.turnState != Main.NEXT_TURN && main.turnState != Main.PRE_TURN || GameScreen.gameState == GameScreen.WAIT){
            return;
        }

        if (main.turnState == Main.NEXT_TURN){
            handleAttack(enemyPsykey[main.enemyPsykeySelected], playerCardPlayed[0], playerCardPlayed[1], Integer.parseInt(playerCardPlayed[2]), enemyCardPlayed[0]);
            handleAttack(playerPsykey[main.playerPsykeySelected], enemyCardPlayed[0], enemyCardPlayed[1], Integer.parseInt(enemyCardPlayed[2]), playerCardPlayed[0]);

            playerPsykey[main.playerPsykeySelected].block = 0;
            enemyPsykey[main.enemyPsykeySelected].block = 0;
            enemyCardPlayedPrev = enemyCardPlayed;
            playerCardPlayedPrev = playerCardPlayed;

            playEnemySpecialCard();

            if (cards.playerDrawPileCardTypesNames.get(main.playerPsykeySelected).size() == 0){
                cards.reshuffleDrawPile(main.playerPsykeySelected);
            }
            cards.drawCard(main.playerPsykeySelected);

            if (cards.enemyDrawPileCardTypesNames.get(main.enemyPsykeySelected).size() == 0){
                cards.enemyReshuffleDrawPile(main.enemyPsykeySelected);
            }
            cards.enemyDrawCard(main.enemyPsykeySelected);

            main.showOverlay = true;
            main.showTurnDisplay = true;
            main.showCardPlayed = true;
            main.overlayTimer = 0;

            main.turnState = Main.PRE_TURN;
        }
    }

    public void startFight(){
        cards.clearCardPiles();

        if (playerPsykey[Main.FIRST] != null) {
            cards.setDrawPile(Main.FIRST);
        }

        if (playerPsykey[Main.SECOND] != null) {
            cards.setDrawPile(Main.SECOND);
        }

        cards.setEnemyDrawPile(Main.FIRST);
        cards.setHandCards();
        enemyTurn();
    }

    public void handleAttack(Psykey psykey, String cardType, String cardName, int cardDamage, String otherCardType){
        if (Arrays.asList(Cards.attackCards).contains(cardName)){
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


    public void enemyTurn(){
        if (cards.enemyHandPileCardTypesNames.get(main.enemyPsykeySelected).size() == 0){
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
        for (int i = 0; i < cards.enemyHandPileCardTypesNames.get(main.enemyPsykeySelected).size(); i++) {
            if (Arrays.asList(Cards.specialCards).contains(cards.enemyHandPileCardTypesNames.get(main.enemyPsykeySelected).get(i)[1])) {
                enemyCardPlayed = cards.enemyHandPileCardTypesNames.get(main.enemyPsykeySelected).get(i);
                cards.enemyDiscardPileCardTypesNames.get(main.enemyPsykeySelected).add(cards.enemyHandPileCardTypesNames.get(main.enemyPsykeySelected).get(i));
                cards.enemyHandPileCardTypesNames.get(main.enemyPsykeySelected).remove(i);

                main.enemyPlayIcon[main.enemyPsykeySelected] = assets.enemySpecialPlayIcon;

                handleEnemyTurnEnd();

                return;
            }
        }

        enemyTurn();
    }

    public void findDefendCard() {
        for (int i = 0; i < cards.enemyHandPileCardTypesNames.get(main.enemyPsykeySelected).size(); i++) {
            if (Arrays.asList(Cards.defendCards).contains(cards.enemyHandPileCardTypesNames.get(main.enemyPsykeySelected).get(i)[1])) {
                enemyCardPlayed = cards.enemyHandPileCardTypesNames.get(main.enemyPsykeySelected).get(i);
                cards.enemyDiscardPileCardTypesNames.get(main.enemyPsykeySelected).add(cards.enemyHandPileCardTypesNames.get(main.enemyPsykeySelected).get(i));
                cards.enemyHandPileCardTypesNames.get(main.enemyPsykeySelected).remove(i);

                main.enemyPlayIcon[main.enemyPsykeySelected] = assets.enemyDefendPlayIcon;

                if (Arrays.asList(Cards.defendCards).contains(enemyCardPlayed[1])){
                    enemyPsykey[main.enemyPsykeySelected].block += Integer.parseInt(enemyCardPlayed[2]);
                }

                handleEnemyTurnEnd();

                return;
            }
        }

        enemyTurn();
    }

    public void findAttackCard(){
        for(int i = 0; i < cards.enemyHandPileCardTypesNames.get(main.enemyPsykeySelected).size(); i++){
            if (Arrays.asList(Cards.attackCards).contains(cards.enemyHandPileCardTypesNames.get(main.enemyPsykeySelected).get(i)[1])){
                enemyCardPlayed = cards.enemyHandPileCardTypesNames.get(main.enemyPsykeySelected).get(i);
                cards.enemyDiscardPileCardTypesNames.get(main.enemyPsykeySelected).add(cards.enemyHandPileCardTypesNames.get(main.enemyPsykeySelected).get(i));
                cards.enemyHandPileCardTypesNames.get(main.enemyPsykeySelected).remove(i);

                main.enemyPlayIcon[main.enemyPsykeySelected] = assets.enemyAttackPlayIcon;

                handleEnemyTurnEnd();

                return;
            }
        }

        enemyTurn();
    }
    public void resetStats(Psykey[] psykey, Psykey[] psykeyRef, int psykeySelected){
        psykey[psykeySelected].idProwessValue = psykeyRef[psykeySelected].idProwessValue;
        psykey[psykeySelected].egoProwessValue = psykeyRef[psykeySelected].egoProwessValue;
        psykey[psykeySelected].superegoProwessValue = psykeyRef[psykeySelected].superegoProwessValue;

        psykey[psykeySelected].idDefenseValue = psykeyRef[psykeySelected].idDefenseValue;
        psykey[psykeySelected].egoDefenseValue = psykeyRef[psykeySelected].egoDefenseValue;
        psykey[psykeySelected].superegoDefenseValue = psykeyRef[psykeySelected].superegoDefenseValue;
    }


    public void playEnemySpecialCard(){
        if (Objects.equals(enemyCardPlayed[1], "Slander")){
            resetStats(playerPsykey, playerPsykeyRef, main.playerPsykeySelected);

            playerPsykey[main.playerPsykeySelected].statusEffect = assets.playerDefDownIcon;

            switch (enemyCardPlayed[0]){
                case "Id":
                    playerPsykey[main.playerPsykeySelected].idDefenseValue =
                            playerPsykeyRef[main.playerPsykeySelected].idDefenseValue - 3;

                    break;

                case "Ego":
                    playerPsykey[main.playerPsykeySelected].egoDefenseValue =
                            playerPsykey[main.playerPsykeySelected].egoDefenseValue - 3;

                    break;

                case "Superego":
                    playerPsykey[main.playerPsykeySelected].superegoDefenseValue =
                            playerPsykey[main.playerPsykeySelected].superegoDefenseValue - 3;

                    break;
            }

        } else if (Objects.equals(enemyCardPlayed[1], "Battlecry")){
            resetStats(enemyPsykey, enemyPsykeyRef, main.enemyPsykeySelected);

            enemyPsykey[main.enemyPsykeySelected].statusEffect = assets.enemyProwUpIcon;

            switch (enemyCardPlayed[0]){
                case "Id":
                    enemyPsykey[main.enemyPsykeySelected].idProwessValue =
                            enemyPsykeyRef[main.enemyPsykeySelected].idProwessValue + 3;

                    break;

                case "Ego":
                    enemyPsykey[main.enemyPsykeySelected].egoProwessValue =
                            enemyPsykeyRef[main.enemyPsykeySelected].egoProwessValue  + 3;

                    break;

                case "Superego":
                    enemyPsykey[main.enemyPsykeySelected].superegoProwessValue =
                            enemyPsykeyRef[main.enemyPsykeySelected].superegoProwessValue + 3;

                    break;
            }
        }
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
