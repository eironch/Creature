package com.ficuno.PSYKIES;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;

public class Main {
    Psykey[] playerPsykey;
    Psykey[] enemyPsykey;
    Cards cards;
    Controller controller;
    Renderer renderer;
    TouchRegion touchRegion;
    Encounter encounter;
    Assets assets;
    static int SCREEN_WIDTH = Gdx.graphics.getWidth();
    final static int SCREEN_HEIGHT = Gdx.graphics.getHeight();
    final static float aspectRatio = (float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight();
    final static int maxCards = 36;
    ArrayList<Integer> handCardSelected = new ArrayList<>();
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
    final static int NOT_CHOSEN = 0;
    final  static int CHOSEN = 1;
    final static int CHANGE = 2;
    int choicePsykeyState;
    Choose choose;
    Vector2 choiceSpotlightPos;
    Change change;

    public Main () {loadGame();}

    public void loadGame(){
        handCardSelected.add(40);
        for (int x = 0; x < 5; x++){
            handCardSelected.add(0);
        }
        System.out.println(Main.aspectRatio);
        playerPsykey = new Psykey[2];
        playerPsykeyRef = new Psykey[2];
        enemyPsykey = new Psykey[2];
        enemyPsykeyRef = new Psykey[2];
        choiceSpotlightPos = new Vector2();
        playerPlayIcon = new TextureRegion[2];
        enemyPlayIcon = new TextureRegion[2];

        choicePsykeyState = NOT_CHOSEN;

        assets = new Assets();
        touchRegion = new TouchRegion(this);
        change = new Change(this);
        cards = new Cards(this);
        encounter = new Encounter(this);
        choose = new Choose(this);
        renderer = new Renderer(this);
        controller = new Controller(this);

        assets.encounterMusic.setVolume(0.15f);
        assets.encounterMusic.setLooping(true);
        assets.encounterMusic.play();
        actionsMenuState = assets.actionsIconClose;
    }

    public void update(float deltaTime){
        renderer.render(deltaTime);
        controller.processKeys();
        encounter.update();
    }

    public void randomizeEnemy(){
        enemyPsykey[0] = new Psykey(Psykey.names[(int)(Math.random() * Psykey.names.length)]);
        enemyPsykey[0].healthPoints -= 5;
        enemyPsykeyRef[0] = new Psykey(enemyPsykey[0].name);
        enemyPsykeyRef[0].healthPoints -= 5;
    }
}
