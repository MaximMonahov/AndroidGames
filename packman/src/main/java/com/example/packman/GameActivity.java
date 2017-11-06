package com.example.packman;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;

import java.util.Timer;

/**
 * Created by Максим on 30.08.2017.
 */
public class GameActivity extends AppCompatActivity implements View.OnTouchListener{

    MediaPlayer sound_player;
    MySurfaceView main_view;
    Timer timer ;
    MapXMLParserHandler map_handler;
    float X0=0;
    float Y0=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        main_view = new MySurfaceView(this);
        main_view.setOnTouchListener(this);
        this.setContentView(main_view);
        timer = new Timer();
        map_handler = new MapXMLParserHandler(this);
        main_view.getGame().setMap(map_handler.getMapByXMLId(R.xml.level_1));
    }

    @Override
    protected void onStart() {
        super.onStart();
        timer.scheduleAtFixedRate(new GraphUpdater(main_view), 0 , 100);
        timer.scheduleAtFixedRate(new StepUpdater(this), 0, 400);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                X0 = x;
                Y0 = y;
                main_view.setIsActivityTouched(true);
                main_view.setXY0(x, y);
                Log.d("onTouch", "ACTION DOWN!!!");

                break;
            case MotionEvent.ACTION_UP:
                main_view.setIsActivityTouched(false);
                Log.d("onTouch", "ACTION UP!!!");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("onTouch", "ACTION MOVE!!!");
                float max_radius = main_view.getFullAreaRadius()- main_view.getTouchAreaRadius();

                double newX = X0, newY = Y0;
                double deltaX = Math.sin(Math.atan(Math.abs((x-X0)/(y-Y0))))*max_radius;
                double deltaY = Math.cos(Math.atan(Math.abs((x - X0) / (y-Y0))))*max_radius;
                deltaX = X0> x ? deltaX*(-1):deltaX ;
                deltaY = Y0> y ? deltaY*(-1):deltaY ;

                newX = (Math.abs(X0 - x) > max_radius)? X0 + deltaX : x;
                newY = (Math.abs(Y0 - y) > max_radius)? Y0 + deltaY : y;
                Log.d("onTouch", "newX = " + (float)newX + "  newY = " + (float)newY);
                // mSurface.setXY(x, y);
                main_view.setXY((float)newX,(float)newY);
                main_view.getGame().setDirection(this.getDirection((float) newX, (float) newY));
                break;
        }
        return true;
    }

    private int getDirection(float newX, float newY) {
        if(Math.abs(newX-X0) > Math.abs(newY-Y0))
        {
            if(newX>X0)
                 return GameLogic.DIR_EAST;
            else return GameLogic.DIR_WEST;
        }
        else
        {
            if(newY>Y0)
                return GameLogic.DIR_SOUTH;
            else return GameLogic.DIR_NORTH;
        }
    }

    public void Step() {
        main_view.OnStep();
        switch (main_view.getGame().NextStep())
        {
            case GameLogic.STEP_SUCCESS:
                break;
            case GameLogic.STEP_FAIL:
                break;
            case GameLogic.STEP_GHOST_ACHIVED:
                sound_player = MediaPlayer.create(this, R.raw.sound_lose);
                sound_player.start();
            break;
        }
    }

    public void onStop() {
        super.onStop();
        timer.cancel();
        timer.purge();
    }
}
