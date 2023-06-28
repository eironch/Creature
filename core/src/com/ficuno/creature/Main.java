package com.ficuno.creature;

public class Main {
    static final int WIDTH = 320;
    static final int HEIGHT = 180;
    Card card;
    Controller controller;

    public Main () {loadGame();}

    public void loadGame(){
        card = new Card(this);
        controller = new Controller(this);
    }

    public void update(float deltaTime){
        controller.processKeys(deltaTime);
    }
}
