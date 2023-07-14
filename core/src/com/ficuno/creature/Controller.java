package com.ficuno.creature;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import java.util.Arrays;

public class Controller {
    Main main;
    Card card;
    TouchRegion touchRegion;
    Renderer renderer;
    Psykey[] enemyPsykey;
    Psykey[] playerPsykey;
    Encounter encounter;
    Assets assets;
    Starter starter;
    public Controller(Main main){
        this.main = main;
        this.card = main.card;
        this.assets = main.assets;
        this.renderer = main.renderer;
        this.touchRegion = main.touchRegion;
        this.playerPsykey = main.playerPsykey;
        this.encounter = main.encounter;
        this.enemyPsykey = main.enemyPsykey;
        this.starter = main.starter;
    }

    public void processKeys(float deltaTime){
        if (GameScreen.lost){
            if (Gdx.input.justTouched()){
                if (touchRegion.retryButtonPoly.contains(renderer.touchPos.x, renderer.touchPos.y)){
                    GameScreen.retry = true;
                }
            }

            return;
        }

        if (Gdx.input.justTouched()){
            if (main.chosePsykey){
                checkTouchRegions();
            } else {
                checkStarterTouchRegion();
            }
        }
    }

    public void checkTouchRegions(){
        for (int x = 0; x < touchRegion.cardTouchRegionPolys__.get(main.playerPsykeySelected).size(); x++){
            if (touchRegion.cardTouchRegionPolys__.get(main.playerPsykeySelected).get(x).contains(renderer.touchPos.x, renderer.touchPos.y)){
                if (main.handCardSelected.get(x) == 40
                        && main.turnState == Main.PLAYER_TURN
                        && main.playerTurn == Main.NOT_DONE){
                    encounter.playerCardPlayed = card.playerHandPileCardTypesNames__.get(main.playerPsykeySelected).get(x);

                    if (Arrays.asList(Card.attackCards).contains(encounter.playerCardPlayed[1])){
                        main.playerPlayIcon[main.playerPsykeySelected] = assets.playerAttackPlayIcon;
                    } else if (Arrays.asList(Card.defendCards).contains(encounter.playerCardPlayed[1])){
                        playerPsykey[main.playerPsykeySelected].block += Integer.parseInt(encounter.playerCardPlayed[2]);
                        main.playerPlayIcon[main.playerPsykeySelected] = assets.playerDefendPlayIcon;
                    } else if (Arrays.asList(Card.specialCards).contains(encounter.playerCardPlayed[1])){
                        main.playerPlayIcon[main.playerPsykeySelected] = assets.playerSpecialPlayIcon;
                    }

                    card.playerDiscardPileCardTypesNames__.get(main.playerPsykeySelected).add(card.playerHandPileCardTypesNames__.get(main.playerPsykeySelected).get(x));

                    card.playerHandPileCardTypesNames__.get(main.playerPsykeySelected).remove(x);
                    touchRegion.cardTouchRegionPolys__.get(main.playerPsykeySelected).remove(x);

                    main.playerTurn = Main.DONE;

                    if (main.enemyTurn == Main.DONE){
                        main.enemyTurn = Main.NOT_DONE;
                        main.playerTurn = Main.NOT_DONE;
                        main.turnState = Main.NEXT_TURN;

                    } else {
                        main.turnState = Main.ENEMY_TURN;
                        main.currentTurn = Main.ENEMY_TURN;

                        main.showOverlay = true;
                        main.showTurnDisplay = true;

                        main.overlayTimer = 0;
                    }

                    return;
                }

                for (int i = 0; i < 6; i++){
                    main.handCardSelected.set(i, 0);
                }

                main.handCardSelected.set(x, 40);

                return;
            }
        }

        for (int x = 0; x < touchRegion.uiTouchRegionPolys.size(); x++){
            if (touchRegion.uiTouchRegionPolys.get(x).contains(renderer.touchPos.x, renderer.touchPos.y)){
                if (x == 3){
                    if (main.actionsMenuState == assets.actionsIconClose){
                        main.actionsMenuState = assets.actionsIconOpen;
                    } else {
                        main.actionsMenuState = assets.actionsIconClose;
                    }
                }

                return;
            }
        }



        if (touchRegion.enemyPsykeyPoly.contains(renderer.touchPos.x, renderer.touchPos.y)){
            if (main.showStatsEnemy){
                main.showStatsEnemy = false;
            } else {
                main.showStatsEnemy = true;
            }

        } else if (touchRegion.playerPsykeyPoly.contains(renderer.touchPos.x, renderer.touchPos.y)){
            if (main.showStatsPlayer){
                main.showStatsPlayer = false;
            } else {
                main.showStatsPlayer = true;
            }
        }
    }

    public void checkStarterTouchRegion(){
        if (touchRegion.chooseButtonPoly.contains(renderer.touchPos.x, renderer.touchPos.y)){
            main.playerPsykey[0] = new Psykey(starter.starterPsykies[starter.chosenPsykey].name);
            main.playerPsykeyRef[0] = new Psykey(main.playerPsykey[0].name);
            main.chosePsykey = true;
            touchRegion.chooseButtonPoly = null;

            encounter.startFight();
        }

        if (touchRegion.starterPsykeyPolys[0].contains(renderer.touchPos.x, renderer.touchPos.y)){
            starter.chosenPsykey = 0;
            main.starterSpotlightPos.set(Main.SCREEN_WIDTH/4f - starter.starterPsykies[0].texture.getRegionWidth()/2f,
                    (Main.SCREEN_HEIGHT - Main.SCREEN_HEIGHT/2.5f) - starter.starterPsykies[0].texture.getRegionHeight()/2f);

        } else if (touchRegion.starterPsykeyPolys[1].contains(renderer.touchPos.x, renderer.touchPos.y)) {
            starter.chosenPsykey = 1;
            main.starterSpotlightPos.set(Main.SCREEN_WIDTH/2f - starter.starterPsykies[1].texture.getRegionWidth()/2f,
                    (Main.SCREEN_HEIGHT - Main.SCREEN_HEIGHT/2.5f) - starter.starterPsykies[1].texture.getRegionHeight()/2f);

        }  else if (touchRegion.starterPsykeyPolys[2].contains(renderer.touchPos.x, renderer.touchPos.y)){
            starter.chosenPsykey = 2;
            main.starterSpotlightPos.set(((Main.SCREEN_WIDTH/4f) * 3) - starter.starterPsykies[2].texture.getRegionWidth()/2f,
                    (Main.SCREEN_HEIGHT - Main.SCREEN_HEIGHT/2.5f) - starter.starterPsykies[2].texture.getRegionHeight()/2f);
        }
    }
}