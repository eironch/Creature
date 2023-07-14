package com.ficuno.creature;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class Main {
    Psykey[] playerPsykey;
    Psykey[] enemyPsykey;
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
    Psykey[] playerPsykeyRef;
    Psykey[] enemyPsykeyRef;
    final static int NOT_DONE = 0;
    final static int DONE = 1;
    final static int ENEMY_TURN = 0;
    final static int PLAYER_TURN = 1;
    final static int MID_TURN = 2;
    final static int NEXT_TURN = 3;
    final static int PRE_TURN = 4;
    final static int FIRST = 0;
    final static int SECOND = 1;
    int enemyTurn;
    int playerTurn;
    int turnState = ENEMY_TURN;
    int currentTurn = ENEMY_TURN;
    TextureRegion[] enemyPlayIcon;
    TextureRegion[] playerPlayIcon;
    TextureRegion actionsMenuState;
    boolean showOverlay = false;
    boolean showTurnDisplay = false;
    boolean showStatsEnemy = false;
    boolean showStatsPlayer = false;
    float overlayTimer;
    boolean showCardPlayed;
    int playerPsykeySelected;
    int enemyPsykeySelected;
    boolean chosePsykey;
    Starter starter;
    Vector2 starterSpotlightPos;

    public Main () {loadGame();}

    public void loadGame(){
        handCardSelected.add(40);
        for (int x = 0; x < 5; x++){
            handCardSelected.add(0);
        }

        playerPsykey = new Psykey[2];
        playerPsykeyRef = new Psykey[2];
        enemyPsykey = new Psykey[2];
        enemyPsykeyRef = new Psykey[2];
        starterSpotlightPos = new Vector2();
        playerPlayIcon = new TextureRegion[2];
        enemyPlayIcon = new TextureRegion[2];

        chosePsykey = false;

//        playerPsykey[1] = new Psykey("Seduira");
//        playerPsykeyRef[1] = new Psykey("Seduira");
        enemyPsykey[0] = new Psykey("Ditzard");
        enemyPsykeyRef[0] = new Psykey("Ditzard");

        assets = new Assets();
        touchRegion = new TouchRegion(this);
        card = new Card(this);
        encounter = new Encounter(this);
        starter = new Starter(this);
        renderer = new Renderer(this);
        controller = new Controller(this);

        actionsMenuState = assets.actionsIconClose;
    }

    public void update(float deltaTime){
        renderer.render(deltaTime);
        controller.processKeys(deltaTime);
        encounter.update(deltaTime);
    }
}
