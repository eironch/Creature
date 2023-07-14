package com.ficuno.creature;

import com.badlogic.gdx.math.Polygon;

import java.util.ArrayList;
import java.util.List;

public class TouchRegion {
    Main main;
    ArrayList<ArrayList<Polygon>> cardTouchRegionPolys__;
    List<Polygon> uiTouchRegionPolys = new ArrayList<>();
    Polygon enemyPsykeyPoly;
    Polygon playerPsykeyPoly;
    Polygon[] starterPsykeyPolys;
    Polygon chooseButtonPoly;
    Polygon retryButtonPoly;
    public TouchRegion(Main main){
        this.main = main;

        for (int x = 0; x < 4; x++){
            uiTouchRegionPolys.add(new Polygon(new float[]{0, 0, 96, 0, 96, 96, 0, 96}));
        }

        enemyPsykeyPoly = new Polygon(new float[]{0, 0, 160, 0, 160, 160, 0, 160});
        playerPsykeyPoly = new Polygon(new float[]{0, 0, 160, 0, 160, 160, 0, 160});
        cardTouchRegionPolys__ = new ArrayList<ArrayList<Polygon>>();
        cardTouchRegionPolys__.add(new ArrayList<Polygon>());
        cardTouchRegionPolys__.add(new ArrayList<Polygon>());

        starterPsykeyPolys = new Polygon[]{new Polygon(new float[]{0, 0, 160, 0, 160, 160, 0, 160}),
                new Polygon(new float[]{0, 0, 160, 0, 160, 160, 0, 160}),
                new Polygon(new float[]{0, 0, 160, 0, 160, 160, 0, 160})
        };

        chooseButtonPoly = new Polygon(new float[]{0, 0, 320, 0, 320, 80, 0, 80});

        retryButtonPoly = new Polygon(new float[]{0, 0, Main.SCREEN_WIDTH, 0, Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT, 0, Main.SCREEN_HEIGHT});
    }
}
