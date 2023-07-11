package com.ficuno.creature;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Polygon;

import java.util.Arrays;

public class Controller {
    Main main;
    Card card;
    TouchRegion touchRegion;
    Renderer renderer;
    Psykey enemyPsykey;
    Psykey currentPsykey;
    Encounter encounter;
    Assets assets;
    public Controller(Main main){
        this.main = main;
        this.card = main.card;
        this.assets = main.assets;
        this.renderer = main.renderer;
        this.touchRegion = main.touchRegion;
        this.currentPsykey = main.currentPsykey;
        this.encounter = main.encounter;
        this.enemyPsykey = main.enemyPsykey;
    }

    public void processKeys(float deltaTime){
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)
                && card.playerDrawPileCardTypesNames.size() > 0
                && card.playerHandPileCardTypesNames.size() < 6){
            card.drawCard();

        } else if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)
                && card.playerHandPileCardTypesNames.size() < 6){
            card.reshuffleDrawPile();
            card.drawCard();

            touchRegion.cardTouchRegionPolys.add(new Polygon(new float[]{0, 0, 112, 0, 112, 192, 0, 192}));
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.Q)){
            main.currentPsykey.healthPoints -= 2;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.E)){
            main.currentPsykey.healthPoints += 2;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.I)){
            main.enemyPsykey.healthPoints -= 2;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.P)){
            main.enemyPsykey.healthPoints += 2;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.G)){
            main.turnState = Main.NEXT_TURN;
        }

        if (Gdx.input.justTouched()){
            for (int x = 0; x < touchRegion.cardTouchRegionPolys.size(); x++){
                if (touchRegion.cardTouchRegionPolys.get(x).contains(renderer.touchPos.x, renderer.touchPos.y)){
                    if (main.handCardSelected.get(x) == 40
                            && main.turnState == Main.PLAYER_TURN
                            && main.playerTurn == Main.NOT_DONE){
                        encounter.playerCardPlayed = card.playerHandPileCardTypesNames.get(x);

                        if (Arrays.asList(Card.attackCards).contains(encounter.playerCardPlayed[1])){
                            main.playerPlayIcon = assets.playerAttackPlayIcon;
                        } else if (Arrays.asList(Card.defendCards).contains(encounter.playerCardPlayed[1])){
                            currentPsykey.block += Integer.parseInt(encounter.playerCardPlayed[2]);
                            main.playerPlayIcon = assets.playerDefendPlayIcon;
                        } else if (Arrays.asList(Card.specialCards).contains(encounter.playerCardPlayed[1])){
                            main.playerPlayIcon = assets.playerSpecialPlayIcon;
                        }

                        card.playerDiscardPileCardTypesNames.add(card.playerHandPileCardTypesNames.get(x));

                        card.playerHandPileCardTypesNames.remove(x);
                        touchRegion.cardTouchRegionPolys.remove(x);

                        main.playerTurn = Main.DONE;

                        if (main.enemyTurn == Main.DONE){
                            main.enemyTurn = Main.NOT_DONE;
                            main.playerTurn = Main.NOT_DONE;
                            main.turnState = Main.PRE_TURN;

                        } else {
                            main.turnState = Main.ENEMY_TURN;
                            main.currentTurn = Main.ENEMY_TURN;
                            main.showTurnDisplay = true;
                            main.overlayTimer = 0;
                        }

                        break;
                    }

                    for (int i = 0; i < 6; i++){
                        main.handCardSelected.set(i, 0);
                    }

                    main.handCardSelected.set(x, 40);

                    break;
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
                }
            }
        }
    }
}