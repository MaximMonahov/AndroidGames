package com.example.tetris;

/**
 * Created by Максим on 09.09.2017.
 */
public class GameLogic {

    public final static int FIELD_WIDTH = 18;
    public final static int FIELD_HEIGHT = 25;

    int [][] mField;
    public GameLogic()
    {
        mField = new int[FIELD_WIDTH][FIELD_HEIGHT];
    }
}
