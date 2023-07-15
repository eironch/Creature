package com.ficuno.PSYKIES;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Choose {
    Main main;
    Assets assets;
    Renderer renderer;
    TouchRegion touchRegion;
    Psykey[] psykeyChoices;
    Psykey chosenPsykey;
    int psykeyIndex = 404;
    public Choose(Main main){
        this.main = main;
        this.assets = main.assets;
        this.renderer = main.renderer;
        this.touchRegion = main.touchRegion;

        psykeyChoices = new Psykey[]{new Psykey("Ditzard"),
                new Psykey("Vainhound"),
                new Psykey("Owlgorithm")
        };

        main.choiceSpotlightPos.set(-404, -404);

        setPolyPos();
    }

    public void setPolyPos(){
        touchRegion.choiceButtonPoly.setPosition(Main.SCREEN_WIDTH/2f - assets.choiceButtonIcon.getRegionWidth()/2f - 8,
                90 + assets.choiceButtonIcon.getRegionHeight()/2f + 8);

        touchRegion.choicePsykeyPolys[0].setPosition(
                Main.SCREEN_WIDTH/4f - psykeyChoices[0].texture.getRegionWidth()/2f,
                (360 - psykeyChoices[0].texture.getRegionHeight()/2f) - 4
        );

        touchRegion.choicePsykeyPolys[1].setPosition(
                Main.SCREEN_WIDTH/2f - psykeyChoices[1].texture.getRegionWidth()/2f,
                (360 - psykeyChoices[1].texture.getRegionHeight()/2f) - 4
        );

        touchRegion.choicePsykeyPolys[2].setPosition(
                ((Main.SCREEN_WIDTH/4f) * 3) - psykeyChoices[2].texture.getRegionWidth()/2f,
                (360 - psykeyChoices[2].texture.getRegionHeight()/2f) - 4
        );
    }

    public void newPsykies(){
        psykeyChoices = new Psykey[]{new Psykey(Psykey.names[(int)(Math.random() * Psykey.names.length)]),
                new Psykey(Psykey.names[(int)(Math.random() * Psykey.names.length)]),
                new Psykey(Psykey.names[(int)(Math.random() * Psykey.names.length)])
        };
    }

    public void render(SpriteBatch batch, BitmapFont font){
        batch.draw(assets.backgrounds[2], -668, -266);
        font.getData().setScale(1.5f);
        assets.setGlyphLayout("Which Psykey will you take on?");
        font.draw(batch, assets.glyphLayout, Main.SCREEN_WIDTH/2f - assets.w/2f, 600 - assets.h/2f);

        batch.draw(assets.spotlight, main.choiceSpotlightPos.x  - 28, main.choiceSpotlightPos.y - 40);

        if (psykeyIndex != 404){
            batch.draw(assets.choiceButtonBackground,
                    Main.SCREEN_WIDTH/2f - assets.choiceButtonIcon.getRegionWidth()/2f + 8,
                    90 + assets.choiceButtonIcon.getRegionHeight()/2f - 8);

            batch.draw(assets.choiceButtonIcon,
                    Main.SCREEN_WIDTH/2f - assets.choiceButtonIcon.getRegionWidth()/2f - 8,
                    90 + assets.choiceButtonIcon.getRegionHeight()/2f + 8);

            font.getData().setScale(1.5f);

            assets.setGlyphLayout("Let's go!");
            font.draw(batch, assets.glyphLayout, Main.SCREEN_WIDTH/2f - assets.w/2f - 8,
                    90 + (assets.choiceButtonIcon.getRegionHeight() + assets.h/2f) + 8
                    );
        }
        font.getData().setScale(1);
        assets.setGlyphLayout(psykeyChoices[0].name);
        font.draw(batch, assets.glyphLayout, Main.SCREEN_WIDTH/4f - assets.w/2,
                (432 - psykeyChoices[0].texture.getRegionHeight()/2f) - 70
        );
        batch.draw(psykeyChoices[0].texture,
                Main.SCREEN_WIDTH/4f - psykeyChoices[0].texture.getRegionWidth()/2f,
                (432 - psykeyChoices[0].texture.getRegionHeight()/2f) - 4
        );

        assets.setGlyphLayout(psykeyChoices[1].name);
        font.draw(batch, assets.glyphLayout,
                Main.SCREEN_WIDTH/2f - assets.w/2,
                (432 - psykeyChoices[0].texture.getRegionHeight()/2f) - 70
        );
        batch.draw(psykeyChoices[1].texture,
                Main.SCREEN_WIDTH/2f - psykeyChoices[1].texture.getRegionWidth()/2f,
                (432 - psykeyChoices[1].texture.getRegionHeight()/2f) - 4
        );

        assets.setGlyphLayout(psykeyChoices[2].name);
        font.draw(batch, assets.glyphLayout,
                ((Main.SCREEN_WIDTH/4f) * 3)  - assets.w/2,
                (432 - psykeyChoices[0].texture.getRegionHeight()/2f) - 70
        );
        batch.draw(psykeyChoices[2].texture,
                ((Main.SCREEN_WIDTH/4f) * 3) - psykeyChoices[2].texture.getRegionWidth()/2f,
                (432 - psykeyChoices[2].texture.getRegionHeight()/2f) - 4
        );
    }
}
