package com.ficuno.creature;

import com.badlogic.gdx.math.Polygon;

import java.util.ArrayList;
import java.util.Arrays;

public class Card {
    final static String[] attackCards = new String[]{
            "Maul",
            "Strike",
    };

    final static String[] defendCards = new String[]{
            "Block"
    };

    final static String[] specialCards = new String[]{
            "Slander",
            "Battlecry"
    };

    Main main;
    Psykey[] playerPsykey;
    Psykey[] enemyPsykey;
    ArrayList<ArrayList<String[]>> playerDrawPileCardTypesNames__;
    ArrayList<ArrayList<String[]>> playerHandPileCardTypesNames__;
    ArrayList<ArrayList<String[]>> playerDiscardPileCardTypesNames__;
    ArrayList<ArrayList<String[]>> enemyDrawPileCardTypesNames__;
    ArrayList<ArrayList<String[]>> enemyHandPileCardTypesNames__;
    ArrayList<ArrayList<String[]>> enemyDiscardPileCardTypesNames__;
    Assets  assets;
    TouchRegion touchRegion;
    public Card(Main main){
        this.main = main;
        this.playerPsykey = main.playerPsykey;
        this.enemyPsykey = main.enemyPsykey;
        this.touchRegion = main.touchRegion;
        this.assets = main.assets;

        playerDrawPileCardTypesNames__ = new ArrayList<ArrayList<String[]>>();
        playerHandPileCardTypesNames__ = new ArrayList<ArrayList<String[]>>();
        playerDiscardPileCardTypesNames__ = new ArrayList<ArrayList<String[]>>();
        enemyDrawPileCardTypesNames__ = new ArrayList<ArrayList<String[]>>();
        enemyHandPileCardTypesNames__ = new ArrayList<ArrayList<String[]>>();
        enemyDiscardPileCardTypesNames__ = new ArrayList<ArrayList<String[]>>();

        for (int i = 0; i < 2; i++){
            playerDrawPileCardTypesNames__.add(new ArrayList<String[]>());
            playerHandPileCardTypesNames__.add(new ArrayList<String[]>());
            playerDiscardPileCardTypesNames__.add(new ArrayList<String[]>());
            enemyDrawPileCardTypesNames__.add(new ArrayList<String[]>());
            enemyHandPileCardTypesNames__.add(new ArrayList<String[]>());
            enemyDiscardPileCardTypesNames__.add(new ArrayList<String[]>());
        }


    }

    public void setHandCards(){
        for (int x = 0; x < 6; x++){
            if (playerPsykey[Main.FIRST] != null) {
                drawCard(Main.FIRST);
            }
            if (playerPsykey[Main.SECOND] != null){
                drawCard(Main.SECOND);
            }

            enemyDrawCard(main.enemyPsykeySelected);
        }
    }

    public void setDrawPile(int index){
        playerDrawPileCardTypesNames__.get(index).addAll(Arrays.asList(playerPsykey[index].deck));
    }

    public void setEnemyDrawPile(int index){
        enemyDrawPileCardTypesNames__.get(index).addAll(Arrays.asList(enemyPsykey[index].deck));
    }

    public void drawCard(int index){
        int randIndex = (int) (Math.random() * playerDrawPileCardTypesNames__.get(index).size());

        playerHandPileCardTypesNames__.get(index).add(playerDrawPileCardTypesNames__.get(index).get(randIndex));
        touchRegion.cardTouchRegionPolys__.get(index).add(new Polygon(new float[]{0, 0, 112, 0, 112, 192, 0, 192}));
        playerDrawPileCardTypesNames__.get(index).remove(randIndex);
    }

    public void reshuffleDrawPile(int index){
        playerDrawPileCardTypesNames__.get(index).addAll(playerDiscardPileCardTypesNames__.get(index));
        playerDiscardPileCardTypesNames__.get(index).clear();
    }

    public void enemyDrawCard(int index){
        int randIndex = (int) (Math.random() * enemyDrawPileCardTypesNames__.get(index).size());

        enemyHandPileCardTypesNames__.get(index).add(enemyDrawPileCardTypesNames__.get(index).get(randIndex));
        enemyDrawPileCardTypesNames__.get(index).remove(randIndex);
    }

    public void enemyReshuffleDrawPile(int index){
        enemyDrawPileCardTypesNames__.get(index).addAll(enemyDiscardPileCardTypesNames__.get(index));
        enemyDiscardPileCardTypesNames__.get(index).clear();
    }
}