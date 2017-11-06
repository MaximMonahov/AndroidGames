package com.example.packman;

import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by Максим on 02.09.2017.
 */
public class GameLogic {

    // AFTER STEP
    public final static int STEP_FAIL = 0;
    public final static int STEP_GHOST_ACHIVED = 1;
    public final static int STEP_SUCCESS = 2;

    // DIRECTION
    public final static int DIR_WEST = 0;
    public final static int DIR_NORTH = 1;
    public final static int DIR_EAST = 2;
    public final static int DIR_SOUTH = 3;

    // OBJECTS ON FIELD
    public final static int NONE = 0;
    public final static int PACKMAN = 1;
    public final static int WALL = 2;
    public final static int GHOST = 3;
    public final static int FRUIT = 4;
    public final static int WHITE_BALL = 5;
    public final static int BOOM = 6;

    public final static char cNONE = ' ';
    public final static char cNONE_BOARDER = '*';
    public final static char cPACKMAN = 'P';
    public final static char cWALL_HOR = '-';
    public final static char cWALL_VER = '|';
    public final static char cGHOST = 'G';
    public final static char cFRUIT = 'F';
    public final static char cWHITE_BALL = 'B';

    public final static long SCORE_SMALL_WHITE_BALL = 20;

    protected int[][] mField;
    protected Object mPackMan = new Object();
    protected ArrayList<Object> mGhost = new ArrayList<Object>();
    private int ghostDirection;
    private long m_score = 0;


    public int getmDirection() {
        return mDirection;
    }

    protected int mDirection = DIR_EAST;
    boolean m_is_game_over = false;
    MediaPlayer sound_player;


    public static int getTypeByChar(char c) {
        switch(c)
        {
            case cNONE:
            case cNONE_BOARDER: return NONE;
            case cPACKMAN:      return PACKMAN;
            case cWALL_VER:
            case cWALL_HOR:     return WALL;
            case cGHOST:        return GHOST;
            case cFRUIT:        return FRUIT;
            case cWHITE_BALL:   return WHITE_BALL;
        }
        return NONE;
    }

    public void setDirection(int direction) {
        this.mDirection = direction;
    }

    public int NextStep() {

        int Result = NextStep(mPackMan.getPos() ,mDirection, PACKMAN );
        for(Object ghosts :mGhost)
        {
            if(NextStep(ghosts.getPos(), getGhostDirection(ghosts.getPos()), GHOST) == STEP_GHOST_ACHIVED)
                return STEP_GHOST_ACHIVED;
        }
        return Result;
    }

    public  long getScore() {
        return m_score;
    }

    public int NextStep(Pos pos, int direction, int type) {

        if(m_is_game_over)
            return STEP_FAIL;
        int nextX = 0, nextY =0;

        Pos deltaPos = getDeltaByDir(direction);

        nextX = pos.x + deltaPos.x;
        nextY = pos.y + deltaPos.y;

        if(nextX>-1 &&  nextX < mField[0].length && nextY>-1 &&  nextY<mField.length )
        {
            if( mField[nextY][nextX] != WALL &&( mField[nextY][nextX] != GHOST && type== PACKMAN || mField[nextY][nextX] != PACKMAN && type== GHOST && mField[nextY][nextX] != GHOST))
            {
                if(type== PACKMAN && mField[nextY][nextX] == WHITE_BALL)
                {
                    m_score +=SCORE_SMALL_WHITE_BALL;
                }
                mField[pos.y][pos.x]  =NONE;
                mField[nextY][nextX]  =type;
                pos.x = nextX ;
                pos.y = nextY ;
            }
            else if(mField[nextY][nextX] == GHOST && type== PACKMAN || mField[nextY][nextX] == PACKMAN && type== GHOST)
            {
                mField[pos.y][pos.x]  = NONE;
                mField[nextY][nextX] = BOOM;
                m_is_game_over = true;
                return STEP_GHOST_ACHIVED;
            }

            return STEP_SUCCESS;
        }
        return STEP_FAIL;
    }

    public int getGhostIndex(int y, int x) {
        for(int i=0; i <mGhost.size();i++)
        {
            if(y == mGhost.get(i).getPos().y && mGhost.get(i).getPos().x == x)
            {
                return i;
            }
        }
        return -1;
    }

    public int getGhostDirection(Pos ghost_pos) {

        boolean is_x_dir_main= false;
        Vector<Integer> x_dirs = new Vector<Integer>(), y_dirs = new Vector<Integer>(), result_dirs = new Vector<Integer>();
        ArrayList<Pos> deltaPosForCheck = new ArrayList<Pos>();

        //1 Define position of ghost relative to Packman
        if(mPackMan.getPos().x > ghost_pos.x) {
            x_dirs.add( DIR_EAST);
            x_dirs.add( DIR_WEST);
        }
        else {
            x_dirs.add(DIR_WEST);
            x_dirs.add(DIR_EAST);
        }

        if(mPackMan.getPos().y > ghost_pos.y) {
            y_dirs.add( DIR_SOUTH);
            y_dirs.add( DIR_NORTH);
        }
        else {
            y_dirs.add( DIR_NORTH);
            y_dirs.add( DIR_SOUTH);
        }

        is_x_dir_main = Math.abs(mPackMan.getPos().x - ghost_pos.x) > Math.abs(mPackMan.getPos().y - ghost_pos.y);

        for(int i = 0; i < x_dirs.size() && i< y_dirs.size() ; i++)
        {
             result_dirs.add(is_x_dir_main ? x_dirs.get(i) : y_dirs.get(i));
             result_dirs.add(is_x_dir_main ? y_dirs.get(i) : x_dirs.get(i));
        }

        Log.d("[GHOST DIR]", "Current ghost Pos x = " + ghost_pos.x + " y = " + ghost_pos.y);
        for(Integer i:result_dirs) {
            Log.d("[GHOST DIR]", "dir[" + result_dirs.indexOf(i) +"] = "  + (i));
            deltaPosForCheck.add(getDeltaByDir(i));
        }

        //2 Direction correction
        for(Pos i:deltaPosForCheck)
        {
            if(mField[ghost_pos.y + i.y][ghost_pos.x + i.x] != WALL && mField[ghost_pos.y + i.y][ghost_pos.x + i.x] != GHOST)
            {
                Log.d("[GHOST DIR]", "result dir = " + result_dirs.get(deltaPosForCheck.indexOf(i)));
                return result_dirs.get(deltaPosForCheck.indexOf(i));
            }
        }
        return 0;
    }

    private Pos getDeltaByDir(Integer i) {
        switch (i)
        {
            case DIR_NORTH: return new Pos(0,-1);
            case DIR_SOUTH: return new Pos(0,1);
            case DIR_EAST:  return new Pos(1,0);
            case DIR_WEST:  return new Pos(-1,0);
        }
        return null;
    }


    public GameLogic() {

    }

    public int getMapWidth()
    {
        return mField[0].length;
    }

    public int getMapHeight()
    {
        return mField.length;
    }

    public void setMap(int[][] map) {
        this.mField = map;
        for(int i = 0; i<mField.length; i++)
            for(int j = 0; j<mField[0].length; j++)
            {
                switch(mField[i][j])
                {
                    case PACKMAN:
                        Log.d("[setMap]", "Packman pos i = " + i + " j = " + j);
                        mPackMan.setPos(new Pos(j, i));
                        mPackMan.setType(PACKMAN);
                        break;
                    case GHOST:
                        mGhost.add( new Object(j,i));
                        mGhost.get(mGhost.size()-1).setType(PACKMAN);
                        break;
                }
            }
    }

    public int[][] getMap() {
        return this.mField ;
    }
}
