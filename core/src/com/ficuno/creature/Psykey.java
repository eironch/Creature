package com.ficuno.creature;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Psykey {
    //
    //
    //

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
    //
    //
    //
    final static int[] healthPointsValues= new int[]{
            40,
            0,
            0,
            0,
            0,
            0,
            0,
            55,
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
    int healthPoints;
    String oddity;
    String[][] deck;
    TextureRegion texture;
    TextureRegion textureMirror;
    Assets assets;

    public Psykey(Main main, String name){
        this.main = main;
        this.card = main.card;
        this.assets = new Assets();

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

                healthPoints = healthPointsValues[x];

                texture = assets.psykeyTextures[x];
                textureMirror = assets.psykeyTexturesMirror[x];

                instantiatePsykeyCards(x);

                break;
            }
        }
    }

    public void instantiatePsykeyCards(int index){
        switch (index){
            case 0:
                deck = new String[][]{
                        {"Ego", "Maul"},
                        {"Ego", "Maul"},
                        {"Ego", "Maul"},
                        {"Superego", "Maul"},
                        {"Id", "Maul"},
                        {"Ego", "Block"},
                        {"Ego", "Block"},
                        {"Ego", "Block"},
                        {"Superego", "Block"},
                        {"Id", "Block"},
                        {"Ego", "Slander"},
                        {"Ego", "Battlecry"}
                };

                break;

            case 7:
                deck = new String[][]{
                        {"Superego", "Strike"},
                        {"Superego", "Strike"},
                        {"Ego", "Strike"},
                        {"Ego", "Strike"},
                        {"Id", "Strike"},
                        {"Superego", "Block"},
                        {"Superego", "Block"},
                        {"Ego", "Block"},
                        {"Ego", "Block"},
                        {"Id", "Block"},
                        {"Superego", "Slander"},
                        {"Ego", "Battlecry"}
                };

                break;

        }
    }
}
