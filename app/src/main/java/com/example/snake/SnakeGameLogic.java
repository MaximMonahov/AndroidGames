package com.example.snake;

import java.util.ArrayList;

/**
 * Created by Максим on 12.08.2017.
 */
public class SnakeGameLogic {


    private static final int INITIAL_SNAKE_LENGTH = 3;

    class pos
    {
        public pos(int x, int y)
        {
            this.x = x;
            this.y = y;
        }
        int x;
        int y;
    };

    // Snake directions
    public static final int DIR_EAST  = 1 ;
    public static final int DIR_WEST  = 2 ;
    public static final int DIR_NORTH = 3 ;
    public static final int DIR_SOUTH = 4 ;

    // Sonething on fields
    public static final int FRUIT = 1 ;
    public static final int SNAKE = -1 ;
    public static final int NOTHING = 0 ;

    // Field dimensions
    public static final int FIELD_WIDTH = 18 ;
    public static final int FIELD_HEIGHT = 30 ;

    int isGrowing = 0;
    int mScore =0;
    int mDirection = DIR_SOUTH;
    ArrayList<pos> mSnake = new ArrayList<pos>();
    public int[][] mField = new int[FIELD_WIDTH][FIELD_HEIGHT];

    SnakeGameLogic()
    {
        for(int i = 0; i<mField.length;i++)
             for(int j = 0; j<mField[i].length; j++)
             {
                 mField[i][j] = NOTHING;
             }

        for(int  i=0; i<INITIAL_SNAKE_LENGTH; i++)
        {
            int msnakeX = FIELD_WIDTH/2 ;
            int msnakeY = FIELD_HEIGHT/2 + i;
            mSnake.add(new pos(msnakeX, msnakeY));
            mField[msnakeX][msnakeY] = SNAKE;
            // The head of the snake is the last element of mSnake
        }
        addFruite();
    }

    public void addFruite()
    {
       boolean exit = false;
       while(!exit)
            {
                int x = (int)(Math.random()*FIELD_WIDTH);
                int y = (int)(Math.random()*FIELD_HEIGHT);
                if(mField[x][y] == NOTHING)
                {
                    mField[x][y] = FRUIT;
                    exit = true;
                }
            }
    }

    public boolean NextStep()
    {
        int detlaX = 0;
        int detlaY = 0;
        switch(this.mDirection)
        {
            case DIR_NORTH:
                detlaX = 0;
                detlaY = -1;
                break;
            case DIR_SOUTH:
                detlaX = 0;
                detlaY = 1;
                break;
            case DIR_EAST:
                detlaX = -1;
                detlaY = 0;
                break;
            case DIR_WEST:
                detlaX = 1;
                detlaY = 0;
                break;
        };

        int nextX = mSnake.get(mSnake.size()-1).x + detlaX;
        int nextY = mSnake.get(mSnake.size()-1).y + detlaY;

        if(nextX>= 0 && nextY>= 0&& nextX<FIELD_WIDTH && nextY < FIELD_HEIGHT && mField[nextX][nextY] == NOTHING)
        {
            if(isGrowing>0)
                isGrowing--;
            else
            {
                mField[mSnake.get(0).x][mSnake.get(0).y] = NOTHING;
                mSnake.remove(0);
            }
            mField[nextX][nextY] = SNAKE;
            mSnake.add(new pos(nextX,nextY));
            return true;
        }
        else if(nextX>= 0 && nextY>= 0&& nextX<FIELD_WIDTH && nextY < FIELD_HEIGHT && mField[nextX][nextY] == FRUIT)
        {
            mScore+= getFruitScore();
            isGrowing+=2;
            mSnake.add(new pos(nextX,nextY));
            mField[nextX][nextY] = SNAKE;
            addFruite();
            return true;
        }
        else
        {
            return false;
        }
    }

    public void clearScore(){
        this.mScore=0;
    }

    public int getDirection() {
        return mDirection;
    }

    public void setDirection(int direction) {
        this.mDirection = direction;
    }

    public int[][] getmField() {
        return mField;
    }

    public int getSnakeLength() {
        return  mSnake.size();
    }

    public ArrayList<pos> getmSnake() {
        return mSnake;
    }

    public int getFruitScore() {
        return 10;//fruitScore;
    }
}
