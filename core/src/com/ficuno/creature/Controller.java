package com.ficuno.creature;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class Controller {
    Main main;
    Card card;
    public Controller(Main main){
        this.main = main;
        this.card = main.card;
    }

    public void processKeys(float deltaTime){
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            card.shuffleDeck();
        }
    }
}