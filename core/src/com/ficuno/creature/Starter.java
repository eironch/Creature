package com.ficuno.creature;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Starter {
    Main main;
    Assets assets;
    Renderer renderer;
    TouchRegion touchRegion;
    Psykey[] starterPsykies;
    int chosenPsykey = 404;
    public Starter(Main main){
        this.main = main;
        this.assets = main.assets;
        this.renderer = main.renderer;
        this.touchRegion = main.touchRegion;

        starterPsykies = new Psykey[]{new Psykey("Ditzard"),
                new Psykey("Vainhound"),
                new Psykey("Owlgorithm")
        };

        main.starterSpotlightPos.set(-404, -404);
        touchRegion.chooseButtonPoly.setPosition(Main.SCREEN_WIDTH/2f - assets.chooseButtonIcon.getRegionWidth()/2f - 8,
                (Main.SCREEN_HEIGHT/6f) + assets.chooseButtonIcon.getRegionHeight()/2f + 8);

        touchRegion.starterPsykeyPolys[0].setPosition(
                Main.SCREEN_WIDTH/4f - starterPsykies[0].texture.getRegionWidth()/2f,
                ((Main.SCREEN_HEIGHT/2f) - starterPsykies[0].texture.getRegionHeight()/2f) - 4
        );

        touchRegion.starterPsykeyPolys[1].setPosition(
                Main.SCREEN_WIDTH/2f - starterPsykies[1].texture.getRegionWidth()/2f,
                ((Main.SCREEN_HEIGHT/2f) - starterPsykies[1].texture.getRegionHeight()/2f) - 4
        );

        touchRegion.starterPsykeyPolys[2].setPosition(
                ((Main.SCREEN_WIDTH/4f) * 3) - starterPsykies[2].texture.getRegionWidth()/2f,
                ((Main.SCREEN_HEIGHT/2f) - starterPsykies[2].texture.getRegionHeight()/2f) - 4
        );
    }

    public void render(SpriteBatch batch, BitmapFont font){
        batch.draw(assets.backgrounds[2], -668, -266);
        font.getData().setScale(1.5f);
        assets.setGlyphLayout("Which Psykey will you take on?");
        font.draw(batch, assets.glyphLayout, Main.SCREEN_WIDTH/2f - assets.w/2f, Main.SCREEN_HEIGHT - (Main.SCREEN_HEIGHT/6f) - assets.h/2f);

        batch.draw(assets.spotlight, main.starterSpotlightPos.x  - 28, main.starterSpotlightPos.y - 40);

        if (chosenPsykey != 404){
            batch.draw(assets.chooseButtonBackground,
                    Main.SCREEN_WIDTH/2f - assets.chooseButtonIcon.getRegionWidth()/2f + 8,
                    (Main.SCREEN_HEIGHT/6f) + assets.chooseButtonIcon.getRegionHeight()/2f - 8);

            batch.draw(assets.chooseButtonIcon,
                    Main.SCREEN_WIDTH/2f - assets.chooseButtonIcon.getRegionWidth()/2f - 8,
                    (Main.SCREEN_HEIGHT/6f) + assets.chooseButtonIcon.getRegionHeight()/2f + 8);

            font.getData().setScale(1.5f);

            assets.setGlyphLayout("Let's go!");
            font.draw(batch, assets.glyphLayout, Main.SCREEN_WIDTH/2f - assets.w/2f - 8,
                    (Main.SCREEN_HEIGHT/6f) + (assets.chooseButtonIcon.getRegionHeight() + assets.h/2f) + 8
                    );
        }

        batch.draw(starterPsykies[0].texture,
                Main.SCREEN_WIDTH/4f - starterPsykies[0].texture.getRegionWidth()/2f,
                ((Main.SCREEN_HEIGHT - Main.SCREEN_HEIGHT/2.5f) - starterPsykies[0].texture.getRegionHeight()/2f) - 4
        );
        batch.draw(starterPsykies[1].texture,
                Main.SCREEN_WIDTH/2f - starterPsykies[1].texture.getRegionWidth()/2f,
                ((Main.SCREEN_HEIGHT - Main.SCREEN_HEIGHT/2.5f) - starterPsykies[1].texture.getRegionHeight()/2f) - 4
        );
        batch.draw(starterPsykies[2].texture,
                ((Main.SCREEN_WIDTH/4f) * 3) - starterPsykies[2].texture.getRegionWidth()/2f,
                ((Main.SCREEN_HEIGHT - Main.SCREEN_HEIGHT/2.5f) - starterPsykies[2].texture.getRegionHeight()/2f) - 4
        );
    }
}
