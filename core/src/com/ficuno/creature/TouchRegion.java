package com.ficuno.creature;

import com.badlogic.gdx.math.Polygon;

import java.util.ArrayList;
import java.util.List;

public class TouchRegion {
    Main main;
    List<Polygon> cardTouchRegionPolys = new ArrayList<>();
    List<Polygon> uiTouchRegionPolys = new ArrayList<>();
    public TouchRegion(Main main){
        this.main = main;

        for (int x = 0; x < 4; x++){
            uiTouchRegionPolys.add(new Polygon(new float[]{0, 0, 96, 0, 96, 96, 0, 96}));
        }
    }
}
