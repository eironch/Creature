package com.ficuno.PSYKIES;

import com.badlogic.gdx.math.Polygon;
import java.util.ArrayList;
import java.util.Arrays;

public class Cards {
    final static String[] attackCards = new String[]{
            "Maul",
            "Strike",
            "Slash",
            "Arcane Blast"
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
    ArrayList<ArrayList<String[]>> playerDrawPileCardTypesNames;
    ArrayList<ArrayList<String[]>> playerHandPileCardTypesNames;
    ArrayList<ArrayList<String[]>> playerDiscardPileCardTypesNames;
    ArrayList<ArrayList<String[]>> enemyDrawPileCardTypesNames;
    ArrayList<ArrayList<String[]>> enemyHandPileCardTypesNames;
    ArrayList<ArrayList<String[]>> enemyDiscardPileCardTypesNames;
    Assets  assets;
    TouchRegion touchRegion;
    public Cards(Main main){
        this.main = main;
        this.playerPsykey = main.playerPsykey;
        this.enemyPsykey = main.enemyPsykey;
        this.touchRegion = main.touchRegion;
        this.assets = main.assets;

        playerDrawPileCardTypesNames = new ArrayList<ArrayList<String[]>>();
        playerHandPileCardTypesNames = new ArrayList<ArrayList<String[]>>();
        playerDiscardPileCardTypesNames = new ArrayList<ArrayList<String[]>>();
        enemyDrawPileCardTypesNames = new ArrayList<ArrayList<String[]>>();
        enemyHandPileCardTypesNames = new ArrayList<ArrayList<String[]>>();
        enemyDiscardPileCardTypesNames = new ArrayList<ArrayList<String[]>>();

        for (int i = 0; i < 2; i++){
            playerDrawPileCardTypesNames.add(new ArrayList<String[]>());
            playerHandPileCardTypesNames.add(new ArrayList<String[]>());
            playerDiscardPileCardTypesNames.add(new ArrayList<String[]>());
            enemyDrawPileCardTypesNames.add(new ArrayList<String[]>());
            enemyHandPileCardTypesNames.add(new ArrayList<String[]>());
            enemyDiscardPileCardTypesNames.add(new ArrayList<String[]>());
        }
    }

    public void clearCardPiles(){
        enemyDrawPileCardTypesNames.get(Main.FIRST).clear();
        enemyHandPileCardTypesNames.get(Main.FIRST).clear();
        enemyDiscardPileCardTypesNames.get(Main.FIRST).clear();
        enemyDrawPileCardTypesNames.get(Main.SECOND).clear();
        enemyHandPileCardTypesNames.get(Main.SECOND).clear();
        enemyDiscardPileCardTypesNames.get(Main.SECOND).clear();

        playerDrawPileCardTypesNames.get(Main.FIRST).clear();
        playerHandPileCardTypesNames.get(Main.FIRST).clear();
        playerDiscardPileCardTypesNames.get(Main.FIRST).clear();
        playerDrawPileCardTypesNames.get(Main.SECOND).clear();
        playerHandPileCardTypesNames.get(Main.SECOND).clear();
        playerDiscardPileCardTypesNames.get(Main.SECOND).clear();

        touchRegion.cardTouchRegionPolys.get(Main.FIRST).clear();
        touchRegion.cardTouchRegionPolys.get(Main.SECOND).clear();
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
        playerDrawPileCardTypesNames.get(index).addAll(Arrays.asList(playerPsykey[index].deck));

        assets.cardSlideSound2.play(1.0f);
    }

    public void setEnemyDrawPile(int index){
        enemyDrawPileCardTypesNames.get(index).addAll(Arrays.asList(enemyPsykey[index].deck));
    }

    public void drawCard(int index){
        int randIndex = (int) (Math.random() * playerDrawPileCardTypesNames.get(index).size());

        playerHandPileCardTypesNames.get(index).add(playerDrawPileCardTypesNames.get(index).get(randIndex));
        touchRegion.cardTouchRegionPolys.get(index).add(new Polygon(new float[]{0, 0, 112, 0, 112, 192, 0, 192}));
        playerDrawPileCardTypesNames.get(index).remove(randIndex);

        assets.cardSlideSound1.play(1.0f);
    }

    public void reshuffleDrawPile(int index){
        playerDrawPileCardTypesNames.get(index).addAll(playerDiscardPileCardTypesNames.get(index));
        playerDiscardPileCardTypesNames.get(index).clear();
    }

    public void enemyDrawCard(int index){
        int randIndex = (int) (Math.random() * enemyDrawPileCardTypesNames.get(index).size());

        enemyHandPileCardTypesNames.get(index).add(enemyDrawPileCardTypesNames.get(index).get(randIndex));
        enemyDrawPileCardTypesNames.get(index).remove(randIndex);
    }

    public void enemyReshuffleDrawPile(int index){
        enemyDrawPileCardTypesNames.get(index).addAll(enemyDiscardPileCardTypesNames.get(index));
        enemyDiscardPileCardTypesNames.get(index).clear();
    }
}