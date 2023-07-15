package com.ficuno.PSYKIES;

import com.badlogic.gdx.math.Polygon;

import java.util.ArrayList;
import java.util.List;

public class TouchRegion {
    Main main;
    ArrayList<ArrayList<Polygon>> cardTouchRegionPolys;
    List<Polygon> uiTouchRegionPolys = new ArrayList<>();
    Polygon enemyPsykeyPoly;
    Polygon playerPsykeyPoly;
    Polygon[] choicePsykeyPolys;
    Polygon choiceButtonPoly;
    Polygon retryButtonPoly;
    Polygon switchPsykeyPoly;
    public TouchRegion(Main main){
        this.main = main;

        for (int x = 0; x < 4; x++){
            uiTouchRegionPolys.add(new Polygon(new float[]{0, 0, 96, 0, 96, 96, 0, 96}));
        }

        switchPsykeyPoly = new Polygon(new float[]{0, 0, 96, 0, 96, 96, 0, 96});

        enemyPsykeyPoly = new Polygon(new float[]{0, 0, 160, 0, 160, 160, 0, 160});
        playerPsykeyPoly = new Polygon(new float[]{0, 0, 160, 0, 160, 160, 0, 160});
        cardTouchRegionPolys = new ArrayList<ArrayList<Polygon>>();
        cardTouchRegionPolys.add(new ArrayList<Polygon>());
        cardTouchRegionPolys.add(new ArrayList<Polygon>());

        createChoosePolys();

        retryButtonPoly = new Polygon(new float[]{0, 0, Main.SCREEN_WIDTH, 0, Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT, 0, Main.SCREEN_HEIGHT});
    }

    public void createChoosePolys(){
        choicePsykeyPolys = new Polygon[]{new Polygon(new float[]{0, 0, 160, 0, 160, 160, 0, 160}),
                new Polygon(new float[]{0, 0, 160, 0, 160, 160, 0, 160}),
                new Polygon(new float[]{0, 0, 160, 0, 160, 160, 0, 160})
        };

        choiceButtonPoly = new Polygon(new float[]{0, 0, 320, 0, 320, 80, 0, 80});
    }

    public void createChangePolys(){
        choicePsykeyPolys = new Polygon[]{new Polygon(new float[]{0, 0, 160, 0, 160, 160, 0, 160}),
                new Polygon(new float[]{0, 0, 160, 0, 160, 160, 0, 160}),
        };

        choiceButtonPoly = new Polygon(new float[]{0, 0, 320, 0, 320, 80, 0, 80});
    }
}
