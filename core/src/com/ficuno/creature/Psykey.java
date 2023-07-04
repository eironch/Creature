package com.ficuno.creature;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Objects;

public class Psykey {
    final static String[] names= new String[]{
            "Vainhound",
            "Scaredcrow",
            "Upper Munchom",
            "Nihilgeist",
            "Helmetacle",
            "Snarkberry",
            "Obscurion",
            "Sigmastone",
            "Heymud",
            "Ditzard",
            "Fluffierce",
            "Seduira",
            "Mournsoon",
            "Owlgorithm",
            "Devourax"
    };
    //
    //
    //
    final static int[][] defenseTraitValues= new int[][]{
            {3,3,3},
            {0,0,0},
            {0,0,0},
            {0,0,0},
            {0,0,0},
            {0,0,0},
            {0,0,0},
            {3,4,5},
            {0,0,0},
            {3,3,3},
            {0,0,0},
            {0,0,0},
            {0,0,0},
            {3,3,3},
            {0,0,0},
    };
    //
    //
    //
    final static int[][] prowessTraitValues= new int[][]{
            {0,3,0},
            {0,0,0},
            {0,0,0},
            {0,0,0},
            {0,0,0},
            {0,0,0},
            {0,0,0},
            {0,0,0},
            {0,0,0},
            {3,0,0},
            {0,0,0},
            {0,0,0},
            {0,0,0},
            {0,0,3},
            {0,0,0},
    };

    final static int[] zenPointsValues= new int[]{
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
    };

    Main main;
    Card card;
    String name;
    int idDefenseValue;
    int egoDefenseValue;
    int superegoDefenseValue;
    int idProwessValue;
    int egoProwessValue;
    int superegoProwessValue;
    int zenPoints;
    String oddity;
    String[][] deck;
    static TextureRegion[] psykeyTextures = new TextureRegion[Creature.maxPsykies];
    static TextureRegion[] psykeyTexturesMirror = new TextureRegion[Creature.maxPsykies];
    TextureRegion texture;
    TextureRegion textureMirror;

    public Psykey(Main main, String name){
        this.main = main;
        this.card = main.card;
        instantiatePsykey(name);
    }

    public void instantiatePsykey(String PsykeyName){
        for (int x = 0; x < Creature.maxPsykies; x++){
            if (PsykeyName.equals(names[x])){
                name = names[x];
                idDefenseValue = defenseTraitValues[x][0];
                egoDefenseValue = defenseTraitValues[x][1];
                superegoDefenseValue = defenseTraitValues[x][2];

                idProwessValue = prowessTraitValues[x][0];
                egoProwessValue = prowessTraitValues[x][1];
                superegoProwessValue = prowessTraitValues[x][2];

                zenPoints = zenPointsValues[x];

                texture = psykeyTextures[x];
                textureMirror = psykeyTexturesMirror[x];

                instantiatePsykeyCards(x);

                break;
            }
        }
    }

    public static void loadAndCreateAssets(){
        Texture psykiesTexture = new Texture(Gdx.files.internal("psykiesTexture.png"));

        for (int x = 0; x < Creature.maxPsykies; x++){
            psykeyTextures[x] = new TextureRegion(splitTexture(psykiesTexture, 0, 160, 160)[x]);
            psykeyTexturesMirror[x] = new TextureRegion(mirrorTexture(psykiesTexture, 0, 160, 160)[x]);
        }
    }

    public static TextureRegion[] splitTexture(Texture texture, int index, int width, int height){
        return new TextureRegion(texture).split(width,height)[index];
    }
    public static TextureRegion[] mirrorTexture(Texture texture, int index, int width, int height){
        TextureRegion[] mirror = new TextureRegion(texture).split(width,height)[index];
        for (TextureRegion region: mirror){
            region.flip(true, false);
        }

        return mirror;
    }

    public void instantiatePsykeyCards(int index){
        switch (index){
            case 0:
                deck = new String[][]{
                        {"Maul", "Ego"},
                        {"Maul", "Ego"},
                        {"Maul", "Ego"},
                        {"Maul", "Superego"},
                        {"Maul", "Id"},
                        {"Block", "Ego"},
                        {"Block", "Ego"},
                        {"Block", "Ego"},
                        {"Block", "Superego"},
                        {"Block", "Id"},
                        {"Slander", "Ego"},
                        {"Battlecry", "Ego"}
                };

                break;

            case 7:
                deck = new String[][]{
                        {"Strike", "Superego"},
                        {"Strike", "Superego"},
                        {"Strike", "Ego"},
                        {"Strike", "Ego"},
                        {"Strike", "Id"},
                        {"Block", "Superego"},
                        {"Block", "Superego"},
                        {"Block", "Ego"},
                        {"Block", "Ego"},
                        {"Block", "Id"},
                        {"Slander", "Superego"},
                        {"Battlecry", "Ego"}
                };

                break;

        }
    }
}
