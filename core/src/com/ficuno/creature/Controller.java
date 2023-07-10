package com.ficuno.creature;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Polygon;

public class Controller {
    Main main;
    Card card;
    TouchRegion touchRegion;
    Renderer renderer;
    public Controller(Main main){
        this.main = main;
        this.card = main.card;
        this.renderer = main.renderer;
        this.touchRegion = main.touchRegion;
    }

    public void processKeys(float deltaTime){
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)
                && card.yourDrawPileCardTypesNames.size() > 0
                && card.yourHandPileCardTypesNames.size() < 6){
            card.drawCard();

            touchRegion.cardTouchRegionPolys.add(new Polygon(new float[]{0, 0, 112, 0, 112, 192, 0, 192}));
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && card.yourHandPileCardTypesNames.size() < 6){
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


        if (Gdx.input.justTouched()){
            for (int x = 0; x < touchRegion.cardTouchRegionPolys.size(); x++){
                if (touchRegion.cardTouchRegionPolys.get(x).contains(renderer.touchPos.x,
                        renderer.touchPos.y)){
                    if (main.handCardSelected.get(x) == 40){
                        card.yourDiscardPileCardTypesNames.add(card.yourHandPileCardTypesNames.get(x));

                        card.yourHandPileCardTypesNames.remove(x);
                        touchRegion.cardTouchRegionPolys.remove(x);

                        break;
                    }

                    for (int i = 0; i < 6; i++){
                        main.handCardSelected.set(i, 0);
                    }

                    main.handCardSelected.set(x, 40);


                    break;
                }
            }
        }
//
//        if (Gdx.input.isKeyJustPressed(Input.Keys.A) && main.handCardSelectedIndex > 0){
//            main.handCardSelectedIndex--;
//            main.handCardSelected.set(main.handCardSelectedIndex + 1, 0);
//            main.handCardSelected.set(main.handCardSelectedIndex, 40);
//
//        } else if (Gdx.input.isKeyJustPressed(Input.Keys.D) && main.handCardSelectedIndex < 5 && main.handCardSelectedIndex < card.handPileCardNames.size() - 1){
//            main.handCardSelectedIndex++;
//            main.handCardSelected.set(main.handCardSelectedIndex - 1, 0);
//            main.handCardSelected.set(main.handCardSelectedIndex, 40);
//        }
//
//        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)){
//            card.discardPileCardNames.add(card.handPileCardNames.get(0));
//            card.discardPileCardTypes.add(card.handPileCardTypes.get(0));
//
//            card.handPileCardNames.remove(0);
//            card.handPileCardTypes.remove(0);
//
//        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)){
//            card.discardPileCardNames.add(card.handPileCardNames.get(1));
//            card.discardPileCardTypes.add(card.handPileCardTypes.get(1));
//
//            card.handPileCardNames.remove(1);
//            card.handPileCardTypes.remove(1);
//
//        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)){
//            card.discardPileCardNames.add(card.handPileCardNames.get(2));
//            card.discardPileCardTypes.add(card.handPileCardTypes.get(2));
//
//            card.handPileCardNames.remove(2);
//            card.handPileCardTypes.remove(2);
//
//        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)){
//            card.discardPileCardNames.add(card.handPileCardNames.get(3));
//            card.discardPileCardTypes.add(card.handPileCardTypes.get(3));
//
//            card.handPileCardNames.remove(3);
//            card.handPileCardTypes.remove(3);
//
//        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_5)){
//            card.discardPileCardNames.add(card.handPileCardNames.get(4));
//            card.discardPileCardTypes.add(card.handPileCardTypes.get(4));
//
//            card.handPileCardNames.remove(4);
//            card.handPileCardTypes.remove(4);
//
//        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_6)){
//            card.discardPileCardNames.add(card.handPileCardNames.get(5));
//            card.discardPileCardTypes.add(card.handPileCardTypes.get(5));
//
//            card.handPileCardNames.remove(5);
//            card.handPileCardTypes.remove(5);
//        }
    }
}