package com.ficuno.creature;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;
import java.util.List;

public class Main {
    Psykey currentPsykey;
    Psykey enemyPsykey;
    Card card;
    Controller controller;
    Renderer renderer;
    TouchRegion touchRegion;
    Encounter encounter;
    Assets assets;
    static int SCREEN_WIDTH = Gdx.graphics.getWidth();
    final static int SCREEN_HEIGHT = Gdx.graphics.getHeight();
    final static float aspectRatio = (float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight();
    final static int maxCards = 32;
    List<Integer> handCardSelected = new ArrayList<>();
    Psykey psykeyRef;
    Psykey enemyPsykeyRef;

    public Main () {loadGame();}

    public void loadGame(){
        handCardSelected.add(40);
        for (int x = 0; x < 5; x++){
            handCardSelected.add(0);
        }

        assets = new Assets();
        touchRegion = new TouchRegion(this);
        currentPsykey = new Psykey(this, "Sigmastone");
        psykeyRef = new Psykey(this, "Sigmastone");
        enemyPsykey = new Psykey(this, "Vainhound");
        enemyPsykeyRef = new Psykey(this, "Vainhound");
        card = new Card(this);
        renderer = new Renderer(this);
        controller = new Controller(this);
        encounter = new Encounter(this);

        card.setDrawPile();
        card.drawStartCards();
    }

    public void update(float deltaTime){
        renderer.render(deltaTime);
        controller.processKeys(deltaTime);
    }
}
