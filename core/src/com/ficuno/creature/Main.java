package com.ficuno.creature;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

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
    final static int maxCards = 36;
    List<Integer> handCardSelected = new ArrayList<>();
    Psykey psykeyRef;
    Psykey enemyPsykeyRef;
    final static int NOT_DONE = 0;
    final static int DONE = 1;
    final static int ENEMY_TURN = 0;
    final static int PLAYER_TURN = 1;
    final static int PRE_TURN = 2;
    final static int NEXT_TURN = 3;
    int enemyTurn;
    int playerTurn;
    int turnState = ENEMY_TURN;
    int currentTurn = ENEMY_TURN;
    TextureRegion enemyPlayIcon;
    TextureRegion playerPlayIcon;
    TextureRegion actionsMenuState;
    boolean showTurnDisplay = false;
    float overlayTimer;
    public Main () {loadGame();}

    public void loadGame(){
        handCardSelected.add(40);
        for (int x = 0; x < 5; x++){
            handCardSelected.add(0);
        }

        assets = new Assets();
        actionsMenuState = assets.actionsIconClose;
        touchRegion = new TouchRegion(this);
        currentPsykey = new Psykey(this, "Sigmastone");
        psykeyRef = new Psykey(this, "Sigmastone");
        enemyPsykey = new Psykey(this, "Vainhound");
        enemyPsykeyRef = new Psykey(this, "Vainhound");
        card = new Card(this);
        encounter = new Encounter(this);
        renderer = new Renderer(this);
        controller = new Controller(this);
    }

    public void update(float deltaTime){
        renderer.render(deltaTime);
        controller.processKeys(deltaTime);
        encounter.update(deltaTime);
    }
}
