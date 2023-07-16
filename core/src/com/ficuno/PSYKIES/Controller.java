package com.ficuno.PSYKIES;


import com.badlogic.gdx.Gdx;

import java.util.Arrays;
import java.util.Objects;

public class Controller {
    Main main;
    Cards cards;
    TouchRegion touchRegion;
    Renderer renderer;
    Psykey[] enemyPsykey;
    Psykey[] playerPsykey;
    Psykey[] playerPsykeyRef;
    Psykey[] enemyPsykeyRef;
    Encounter encounter;
    Assets assets;
    Choose choose;
    Change change;
    public Controller(Main main){
        this.main = main;
        this.cards = main.cards;
        this.assets = main.assets;
        this.renderer = main.renderer;
        this.encounter = main.encounter;
        this.touchRegion = main.touchRegion;
        this.playerPsykey = main.playerPsykey;
        this.enemyPsykey = main.enemyPsykey;
        this.playerPsykeyRef = main.playerPsykeyRef;
        this.enemyPsykeyRef = main.enemyPsykeyRef;
        this.choose = main.choose;
        this.change = main.change;
    }

    public void processKeys(){
        if (GameScreen.gameState == GameScreen.LOST){
            if (Gdx.input.justTouched()){
                GameScreen.retry = true;
                assets.uiSound.play(1);
            }

            return;
        } else if (GameScreen.gameState == GameScreen.WON){
            if (Gdx.input.justTouched()){
                assets.uiSound.play(1);
                assets.encounterMusic.play();
                GameScreen.gameState = GameScreen.WAIT;
                touchRegion.createChoosePolys();
                choose.setPolyPos();
                main.choicePsykeyState = Main.NOT_CHOSEN;
                choose.newPsykies();
                main.choiceSpotlightPos.set(-404, -404);
                choose.psykeyIndex = 404;
                main.choicePsykeyState = Main.NOT_CHOSEN;
            }

            return;
        }

        if (Gdx.input.justTouched()){
            if (main.choicePsykeyState == Main.CHOSEN){
                checkTouchRegions();
            } else if (main.choicePsykeyState == Main.NOT_CHOSEN) {
                checkChooseTouchRegion();
            } else {
                checkChangeTouchRegion();
            }
        }
    }

    public void checkTouchRegions(){
        for (int x = 0; x < touchRegion.cardTouchRegionPolys.get(main.playerPsykeySelected).size(); x++){
            if (touchRegion.cardTouchRegionPolys.get(main.playerPsykeySelected).get(x).contains(renderer.touchPos.x, renderer.touchPos.y)){
                if (main.handCardSelected.get(x) == 40
                        && main.turnState == Main.PLAYER_TURN
                        && main.playerTurn == Main.NOT_DONE){
                    encounter.playerCardPlayed = cards.playerHandPileCardTypesNames.get(main.playerPsykeySelected).get(x);

                    if (Arrays.asList(Cards.attackCards).contains(encounter.playerCardPlayed[1])){
                        main.playerPlayIcon[main.playerPsykeySelected] = assets.playerAttackPlayIcon;
                    } else if (Arrays.asList(Cards.defendCards).contains(encounter.playerCardPlayed[1])){
                        playerPsykey[main.playerPsykeySelected].block += Integer.parseInt(encounter.playerCardPlayed[2]);
                        main.playerPlayIcon[main.playerPsykeySelected] = assets.playerDefendPlayIcon;
                    } else if (Arrays.asList(Cards.specialCards).contains(encounter.playerCardPlayed[1])){
                        main.playerPlayIcon[main.playerPsykeySelected] = assets.playerSpecialPlayIcon;

                        playSpecialCard();
                    }

                    cards.playerDiscardPileCardTypesNames.get(main.playerPsykeySelected).add(cards.playerHandPileCardTypesNames.get(main.playerPsykeySelected).get(x));

                    cards.playerHandPileCardTypesNames.get(main.playerPsykeySelected).remove(x);
                    touchRegion.cardTouchRegionPolys.get(main.playerPsykeySelected).remove(x);

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
                    assets.cardSlideSound2.play(1f);

                    return;
                }

                assets.cardSlideSound1.play(1f);

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

                assets.uiSound.play(1);

                return;
            }
        }

        if (touchRegion.enemyPsykeyPoly.contains(renderer.touchPos.x, renderer.touchPos.y)){
            main.showStatsEnemy = !main.showStatsEnemy;
            assets.uiSound.play(1);

        } else if (touchRegion.playerPsykeyPoly.contains(renderer.touchPos.x, renderer.touchPos.y)){
            main.showStatsPlayer = !main.showStatsPlayer;
            assets.uiSound.play(1);
        }

        if (touchRegion.switchPsykeyPoly.contains(renderer.touchPos.x, renderer.touchPos.y) && main.currentTurn == Main.PLAYER_TURN){
            if (playerPsykey[Main.FIRST] != null && playerPsykey[Main.SECOND] != null){
                if (main.playerPsykeySelected == Main.FIRST && playerPsykey[Main.SECOND].healthPoints != 0){
                    main.playerPsykeySelected = Main.SECOND;
                    assets.uiSound.play(1);
                } else if (main.playerPsykeySelected == Main.SECOND && playerPsykey[Main.FIRST].healthPoints != 0){
                    main.playerPsykeySelected = Main.FIRST;
                    assets.uiSound.play(1);
                }
            }
        }
    }

    public void checkChooseTouchRegion(){
        if (touchRegion.choicePsykeyPolys[0].contains(renderer.touchPos.x, renderer.touchPos.y)){
            choose.psykeyIndex = 0;
            main.choiceSpotlightPos.set(Main.SCREEN_WIDTH/4f - choose.psykeyChoices[0].texture.getRegionWidth()/2f,
                    432 - choose.psykeyChoices[0].texture.getRegionHeight()/2f);
            assets.chooseSound.play(0.3f);

        } else if (touchRegion.choicePsykeyPolys[1].contains(renderer.touchPos.x, renderer.touchPos.y)) {
            choose.psykeyIndex = 1;
            main.choiceSpotlightPos.set(Main.SCREEN_WIDTH/2f - choose.psykeyChoices[1].texture.getRegionWidth()/2f,
                    432 - choose.psykeyChoices[1].texture.getRegionHeight()/2f);
            assets.chooseSound.play(0.3f);

        }  else if (touchRegion.choicePsykeyPolys[2].contains(renderer.touchPos.x, renderer.touchPos.y)){
            choose.psykeyIndex = 2;
            main.choiceSpotlightPos.set(((Main.SCREEN_WIDTH/4f) * 3) - choose.psykeyChoices[2].texture.getRegionWidth()/2f,
                    432 - choose.psykeyChoices[2].texture.getRegionHeight()/2f);
            assets.chooseSound.play(0.3f);
        }

        if (touchRegion.choiceButtonPoly.contains(renderer.touchPos.x, renderer.touchPos.y)){
            if (main.playerPsykey[Main.FIRST] == null){
                main.playerPsykey[Main.FIRST] = new Psykey(choose.psykeyChoices[choose.psykeyIndex].name);
                main.playerPsykeyRef[Main.FIRST] = new Psykey(main.playerPsykey[Main.FIRST].name);
                main.choicePsykeyState = Main.CHOSEN;
                touchRegion.choiceButtonPoly = null;
                touchRegion.choicePsykeyPolys = null;

            } else if (main.playerPsykey[Main.SECOND] == null){
                main.playerPsykey[Main.SECOND] = new Psykey(choose.psykeyChoices[choose.psykeyIndex].name);
                main.playerPsykeyRef[Main.SECOND] = new Psykey(main.playerPsykey[Main.SECOND].name);
                main.choicePsykeyState = Main.CHOSEN;
                touchRegion.choiceButtonPoly = null;
                touchRegion.choicePsykeyPolys = null;

            } else {
                choose.chosenPsykey = new Psykey(choose.psykeyChoices[choose.psykeyIndex].name);
                change.loadPsykies();
                touchRegion.createChangePolys();
                change.setPolyPos();
                main.choiceSpotlightPos.set(-404, -404);
                main.choicePsykeyState = Main.CHANGE;

                return;
            }

            assets.uiSound.play(1);
            main.randomizeEnemy();
            encounter.startFight();
            GameScreen.gameState = GameScreen.NEITHER;
        }
    }

    public void checkChangeTouchRegion(){
        if (touchRegion.choicePsykeyPolys[0].contains(renderer.touchPos.x, renderer.touchPos.y)){
            change.psykeyIndex = 0;
            main.choiceSpotlightPos.set(Main.SCREEN_WIDTH/3f - change.psykeyChoices[0].texture.getRegionWidth()/2f,
                    432 - change.psykeyChoices[0].texture.getRegionHeight()/2f);
            assets.chooseSound.play(0.3f);

        } else if (touchRegion.choicePsykeyPolys[1].contains(renderer.touchPos.x, renderer.touchPos.y)) {
            change.psykeyIndex = 1;
            main.choiceSpotlightPos.set((Main.SCREEN_WIDTH - Main.SCREEN_WIDTH/3f)- change.psykeyChoices[1].texture.getRegionWidth()/2f,
                    432 - change.psykeyChoices[1].texture.getRegionHeight()/2f);
            assets.chooseSound.play(0.3f);
        }

        if (touchRegion.choiceButtonPoly.contains(renderer.touchPos.x, renderer.touchPos.y)){
            main.playerPsykey[change.psykeyIndex] = choose.chosenPsykey;
            main.playerPsykeyRef[change.psykeyIndex] = new Psykey(main.playerPsykey[change.psykeyIndex].name);
            main.choicePsykeyState = Main.CHOSEN;
            touchRegion.choiceButtonPoly = null;
            touchRegion.choicePsykeyPolys = null;
        }

        if (main.choicePsykeyState == Main.CHOSEN) {
            assets.uiSound.play(1);
            main.randomizeEnemy();
            encounter.startFight();
            GameScreen.gameState = GameScreen.NEITHER;
        }
    }

    public void playSpecialCard(){
        if (Objects.equals(encounter.playerCardPlayed[1], "Slander")){
            encounter.resetStats(enemyPsykey, enemyPsykeyRef, main.enemyPsykeySelected);

            enemyPsykey[main.enemyPsykeySelected].statusEffect = assets.enemyDefDownIcon;

            switch (encounter.playerCardPlayed[0]){
                case "Id":
                    enemyPsykey[main.enemyPsykeySelected].idDefenseValue =
                            enemyPsykeyRef[main.enemyPsykeySelected].idDefenseValue - 3;

                    break;

                case "Ego":
                    enemyPsykey[main.enemyPsykeySelected].egoDefenseValue =
                            enemyPsykeyRef[main.enemyPsykeySelected].egoDefenseValue - 3;

                    break;

                case "Superego":
                    enemyPsykey[main.enemyPsykeySelected].superegoDefenseValue =
                            enemyPsykeyRef[main.enemyPsykeySelected].superegoDefenseValue - 3;

                    break;
            }

        } else if (Objects.equals(encounter.playerCardPlayed[1], "Battlecry")){
            encounter.resetStats(playerPsykey, playerPsykeyRef, main.playerPsykeySelected);

            playerPsykey[main.playerPsykeySelected].statusEffect = assets.playerProwUpIcon;

            switch (encounter.playerCardPlayed[0]){
                case "Id":
                    playerPsykey[main.playerPsykeySelected].idProwessValue =
                            playerPsykeyRef[main.playerPsykeySelected].idProwessValue + 3;

                    break;

                case "Ego":
                    playerPsykey[main.playerPsykeySelected].egoProwessValue =
                            playerPsykey[main.playerPsykeySelected].egoProwessValue + 3;

                    break;

                case "Superego":
                    playerPsykey[main.playerPsykeySelected].superegoProwessValue =
                            playerPsykey[main.playerPsykeySelected].superegoProwessValue + 3;

                    break;
            }
        }
    }
}