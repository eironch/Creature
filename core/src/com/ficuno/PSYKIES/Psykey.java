package com.ficuno.PSYKIES;

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
            {2,2,2},
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
            {2,2,2},
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
            53,
            47,
            45,
            42,
            46,
            45,
            45,
            43,
    };
    String name;
    TextureRegion statusEffect;
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

    public Psykey(String name){
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

    public String getStats(String cardName){
        switch (cardName){
            case "Maul":
                return "15";
            case "Strike":
                return "15";
            case "Arcane Blast":
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
                        {"Ego", "Maul", getStats("Maul")},
                        {"Ego", "Maul", getStats("Maul")},
                        {"Id", "Block", getStats("Block")},
                        {"Id", "Block", getStats("Block")},
                        {"Ego", "Block", getStats("Block")},
                        {"Superego", "Block", getStats("Block")},
                        {"Superego", "Block", getStats("Block")},
                        {"Ego", "Slander", getStats("Slander")},
                        {"Ego", "Battlecry", getStats("Battlecry")}
                };

                break;

            case 1:
                deck = new String[][]{
                        {"Id", "Strike", getStats("Strike")},
                        {"Ego", "Strike", getStats("Strike")},
                        {"Superego", "Strike", getStats("Strike")},
                        {"Superego", "Strike", getStats("Strike")},
                        {"Superego", "Strike", getStats("Strike")},
                        {"Id", "Block", getStats("Block")},
                        {"Id", "Block", getStats("Block")},
                        {"Ego", "Block", getStats("Block")},
                        {"Superego", "Block", getStats("Block")},
                        {"Superego", "Block", getStats("Block")},
                        {"Superego", "Slander", getStats("Slander")},
                        {"Superego", "Battlecry", getStats("Battlecry")}
                };

                break;

            case 2:
                deck = new String[][]{
                        {"Id", "Maul", getStats("Maul")},
                        {"Id", "Maul", getStats("Maul")},
                        {"Id", "Maul", getStats("Maul")},
                        {"Id", "Maul", getStats("Maul")},
                        {"Id", "Maul", getStats("Maul")},
                        {"Id", "Block", getStats("Block")},
                        {"Ego", "Block", getStats("Block")},
                        {"Ego", "Block", getStats("Block")},
                        {"Superego", "Block", getStats("Block")},
                        {"Superego", "Block", getStats("Block")},
                        {"Id", "Slander", getStats("Slander")},
                        {"Id", "Battlecry", getStats("Battlecry")}
                };

                break;

            case 3:
                deck = new String[][]{
                        {"Ego", "Arcane Blast", getStats("Arcane Blast")},
                        {"Ego", "Arcane Blast", getStats("Arcane Blast")},
                        {"Ego", "Arcane Blast", getStats("Arcane Blast")},
                        {"Ego", "Arcane Blast", getStats("Arcane Blast")},
                        {"Ego", "Arcane Blast", getStats("Arcane Blast")},
                        {"Id", "Block", getStats("Block")},
                        {"Id", "Block", getStats("Block")},
                        {"Ego", "Block", getStats("Block")},
                        {"Superego", "Block", getStats("Block")},
                        {"Superego", "Block", getStats("Block")},
                        {"Ego", "Slander", getStats("Slander")},
                        {"Ego", "Battlecry", getStats("Battlecry")}
                };

                break;

            case 4:
                deck = new String[][]{
                        {"Superego", "Strike", getStats("Strike")},
                        {"Superego", "Strike", getStats("Strike")},
                        {"Superego", "Strike", getStats("Strike")},
                        {"Superego", "Strike", getStats("Strike")},
                        {"Superego", "Strike", getStats("Strike")},
                        {"Id", "Block", getStats("Block")},
                        {"Id", "Block", getStats("Block")},
                        {"Ego", "Block", getStats("Block")},
                        {"Ego", "Block", getStats("Block")},
                        {"Superego", "Block", getStats("Block")},
                        {"Superego", "Slander", getStats("Slander")},
                        {"Superego", "Battlecry", getStats("Battlecry")}
                };

                break;

            case 5:
                deck = new String[][]{
                        {"Id", "Strike", getStats("Strike")},
                        {"Id", "Strike", getStats("Strike")},
                        {"Ego", "Strike", getStats("Strike")},
                        {"Ego", "Strike", getStats("Strike")},
                        {"Ego", "Strike", getStats("Strike")},
                        {"Id", "Block", getStats("Block")},
                        {"Id", "Block", getStats("Block")},
                        {"Ego", "Block", getStats("Block")},
                        {"Superego", "Block", getStats("Block")},
                        {"Superego", "Block", getStats("Block")},
                        {"Ego", "Slander", getStats("Slander")},
                        {"Ego", "Battlecry", getStats("Battlecry")}
                };

                break;

            case 6:
                deck = new String[][]{
                        {"Id", "Arcane Blast", getStats("Arcane Blast")},
                        {"Id", "Arcane Blast", getStats("Arcane Blast")},
                        {"Ego", "Arcane Blast", getStats("Arcane Blast")},
                        {"Ego", "Arcane Blast", getStats("Arcane Blast")},
                        {"Superego", "Arcane Blast", getStats("Arcane Blast")},
                        {"Superego", "Arcane Blast", getStats("Arcane Blast")},
                        {"Id", "Block", getStats("Block")},
                        {"Id", "Block", getStats("Block")},
                        {"Ego", "Block", getStats("Block")},
                        {"Ego", "Block", getStats("Block")},
                        {"Superego", "Block", getStats("Block")},
                        {"Superego", "Block", getStats("Block")}
                };

                break;

            case 7:
                deck = new String[][]{
                        {"Id", "Strike", getStats("Strike")},
                        {"Id", "Strike", getStats("Strike")},
                        {"Ego", "Strike", getStats("Strike")},
                        {"Ego", "Strike", getStats("Strike")},
                        {"Superego", "Strike", getStats("Strike")},
                        {"Superego", "Strike", getStats("Strike")},
                        {"Id", "Block", getStats("Block")},
                        {"Id", "Block", getStats("Block")},
                        {"Ego", "Block", getStats("Block")},
                        {"Ego", "Block", getStats("Block")},
                        {"Superego", "Block", getStats("Block")},
                        {"Superego", "Battlecry", getStats("Battlecry")}
                };

                break;

            case 8:
                deck = new String[][]{
                        {"Id", "Strike", getStats("Strike")},
                        {"Ego", "Strike", getStats("Strike")},
                        {"Superego", "Strike", getStats("Strike")},
                        {"Superego", "Strike", getStats("Strike")},
                        {"Superego", "Strike", getStats("Strike")},
                        {"Id", "Block", getStats("Block")},
                        {"Id", "Block", getStats("Block")},
                        {"Ego", "Block", getStats("Block")},
                        {"Superego", "Block", getStats("Block")},
                        {"Superego", "Block", getStats("Block")},
                        {"Superego", "Slander", getStats("Slander")},
                        {"Superego", "Battlecry", getStats("Battlecry")}
                };

                break;

            case 9:
                deck = new String[][]{
                        {"Id", "Strike", getStats("Strike")},
                        {"Id", "Strike", getStats("Strike")},
                        {"Id", "Strike", getStats("Strike")},
                        {"Ego", "Strike", getStats("Strike")},
                        {"Superego", "Strike", getStats("Strike")},
                        {"Id", "Block", getStats("Block")},
                        {"Ego", "Block", getStats("Block")},
                        {"Ego", "Block", getStats("Block")},
                        {"Superego", "Block", getStats("Block")},
                        {"Superego", "Block", getStats("Block")},
                        {"Id", "Slander", getStats("Slander")},
                        {"Id", "Battlecry", getStats("Battlecry")}
                };

                break;

            case 10:
                deck = new String[][]{
                        {"Id", "Strike", getStats("Strike")},
                        {"Ego", "Strike", getStats("Strike")},
                        {"Ego", "Strike", getStats("Strike")},
                        {"Ego", "Strike", getStats("Strike")},
                        {"Superego", "Strike", getStats("Strike")},
                        {"Id", "Block", getStats("Block")},
                        {"Id", "Block", getStats("Block")},
                        {"Ego", "Block", getStats("Block")},
                        {"Ego", "Block", getStats("Block")},
                        {"Superego", "Block", getStats("Block")},
                        {"Superego", "Block", getStats("Block")},
                        {"Ego", "Slander", getStats("Slander")}
                };

                break;

            case 11:
                deck = new String[][]{
                        {"Id", "Arcane Blast", getStats("Arcane Blast")},
                        {"Id", "Arcane Blast", getStats("Arcane Blast")},
                        {"Id", "Arcane Blast", getStats("Arcane Blast")},
                        {"Id", "Arcane Blast", getStats("Arcane Blast")},
                        {"Ego", "Arcane Blast", getStats("Arcane Blast")},
                        {"Id", "Block", getStats("Block")},
                        {"Ego", "Block", getStats("Block")},
                        {"Ego", "Block", getStats("Block")},
                        {"Superego", "Block", getStats("Block")},
                        {"Superego", "Block", getStats("Block")},
                        {"Id", "Slander", getStats("Slander")},
                        {"Id", "Battlecry", getStats("Battlecry")}
                };

                break;

            case 12:
                deck = new String[][]{
                        {"Id", "Arcane Blast", getStats("Arcane Blast")},
                        {"Superego", "Arcane Blast", getStats("Arcane Blast")},
                        {"Superego", "Arcane Blast", getStats("Arcane Blast")},
                        {"Superego", "Arcane Blast", getStats("Arcane Blast")},
                        {"Superego", "Arcane Blast", getStats("Arcane Blast")},
                        {"Id", "Block", getStats("Block")},
                        {"Id", "Block", getStats("Block")},
                        {"Ego", "Block", getStats("Block")},
                        {"Ego", "Block", getStats("Block")},
                        {"Superego", "Block", getStats("Block")},
                        {"Superego", "Slander", getStats("Slander")},
                        {"Superego", "Battlecry", getStats("Battlecry")}
                };

                break;

            case 13:
                deck = new String[][]{
                        {"Id", "Arcane Blast", getStats("Arcane Blast")},
                        {"Ego", "Arcane Blast", getStats("Arcane Blast")},
                        {"Superego", "Arcane Blast", getStats("Arcane Blast")},
                        {"Superego", "Arcane Blast", getStats("Arcane Blast")},
                        {"Superego", "Arcane Blast", getStats("Arcane Blast")},
                        {"Id", "Block", getStats("Block")},
                        {"Id", "Block", getStats("Block")},
                        {"Ego", "Block", getStats("Block")},
                        {"Ego", "Block", getStats("Block")},
                        {"Superego", "Block", getStats("Block")},
                        {"Superego", "Slander", getStats("Slander")},
                        {"Superego", "Battlecry", getStats("Battlecry")}
                };

                break;

            case 14:
                deck = new String[][]{
                        {"Id", "Maul", getStats("Maul")},
                        {"Id", "Maul", getStats("Maul")},
                        {"Id", "Maul", getStats("Maul")},
                        {"Ego", "Maul", getStats("Maul")},
                        {"Ego", "Maul", getStats("Maul")},
                        {"Id", "Block", getStats("Block")},
                        {"Id", "Block", getStats("Block")},
                        {"Ego", "Block", getStats("Block")},
                        {"Superego", "Block", getStats("Block")},
                        {"Superego", "Block", getStats("Block")},
                        {"Id", "Slander", getStats("Slander")},
                        {"Id", "Battlecry", getStats("Battlecry")}
                };

                break;
        }
    }
}
