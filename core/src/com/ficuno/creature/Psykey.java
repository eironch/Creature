package com.ficuno.creature;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Psykey {
    //
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
    static int[][] prowessTraitValues= new int[][]{
            {0,3,0},
            {0,0,4},
            {5,0,0},
            {0,8,0},
            {0,0,5},
            {3,4,0},
            {0,0,0},
            {0,0,0},
            {0,0,3},
            {3,0,0},
            {2,6,2},
            {5,2,0},
            {1,0,4},
            {0,0,3},
            {4,3,0},
    };
    //
    //
    //
    static int[][] defenseTraitValues= new int[][]{
            {2,3,2},
            {1,3,2},
            {5,0,0},
            {0,2,0},
            {0,2,3},
            {1,2,0},
            {0,0,0},
            {3,3,4},
            {2,3,2},
            {3,2,2},
            {0,0,0},
            {3,0,0},
            {1,0,4},
            {2,2,3},
            {1,2,0},
    };
    //
    //
    //
    static int[] healthPointsValues= new int[]{
            45,
            47,
            49,
            40,
            56,
            43,
            50,
            58,
            47,
            45,
            42,
            46,
            45,
            45,
            43,
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
    int block;

    public Psykey(Main main, String name){
        this.main = main;
        this.card = main.card;
        this.assets = new Assets();

        instantiatePsykey(name);

        int idAttack = 0, egoAttack = 0, superegoAttack = 0;
        int idDefense = 0, egoDefense = 0, superegoDefense = 0;

        for (int i = 0; i < 15; i++){
            idAttack += prowessTraitValues[i][0];
            idDefense += defenseTraitValues[i][0];

            egoAttack += prowessTraitValues[i][1];
            egoDefense += defenseTraitValues[i][1];

            superegoAttack += prowessTraitValues[i][2];
            superegoDefense += defenseTraitValues[i][2];
        }

        System.out.println("Prowess");
        System.out.println("Id: " + idAttack);
        System.out.println("Ego: " + egoAttack);
        System.out.println("Superego: " + superegoAttack);
        System.out.println(idAttack + egoAttack + superegoAttack);
        System.out.println("Defense");
        System.out.println("Id: " + idDefense);
        System.out.println("Ego: " + egoDefense);
        System.out.println("Superego: " + superegoDefense);
        System.out.println(idDefense + egoDefense + superegoDefense);
        System.out.println(idDefense + egoDefense + superegoDefense + idAttack + egoAttack + superegoAttack);
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

    public String getStats(String cardName){
        switch (cardName){
            case "Maul":
                return "15";
            case "Strike":
                return "15";
            case "Block":
                return "5";
            case "Slander":
                return "0";
            case "Battlecry":
                return "0";
        }

        return "0";
    }

    public void instantiatePsykeyCards(int index){
        switch (index){
            case 0:
                deck = new String[][]{
                        {"Ego", "Maul", getStats("Maul")},
                        {"Ego", "Maul", getStats("Maul")},
                        {"Ego", "Maul", getStats("Maul")},
                        {"Superego", "Maul", getStats("Maul")},
                        {"Id", "Maul", getStats("Maul")},
                        {"Ego", "Block", getStats("Block")},
                        {"Ego", "Block", getStats("Block")},
                        {"Ego", "Block", getStats("Block")},
                        {"Superego", "Block", getStats("Block")},
                        {"Id", "Block", getStats("Block")},
                        {"Ego", "Slander", getStats("Slander")},
                        {"Ego", "Battlecry", getStats("Battlecry")}
                };

                break;

            case 7:
                deck = new String[][]{
                        {"Superego", "Strike", getStats("Strike")},
                        {"Superego", "Strike", getStats("Strike")},
                        {"Ego", "Strike", getStats("Strike")},
                        {"Ego", "Strike", getStats("Strike")},
                        {"Id", "Strike", getStats("Strike")},
                        {"Superego", "Block", getStats("Block")},
                        {"Superego", "Block", getStats("Block")},
                        {"Ego", "Block", getStats("Block")},
                        {"Ego", "Block", getStats("Block")},
                        {"Id", "Block", getStats("Block")},
                        {"Superego", "Slander", getStats("Slander")},
                        {"Ego", "Battlecry", getStats("Battlecry")}
                };

                break;

        }
    }
}
