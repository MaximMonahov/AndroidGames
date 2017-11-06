package com.example.snake;

import android.graphics.Canvas;
import android.graphics.Color;

import java.util.TimerTask;

/**
 * Created by Максим on 13.08.2017.
 */
public class GraphUpdater extends TimerTask {

    GameSurface mSurface;

    public GraphUpdater(GameSurface mSurface){
        this.mSurface = mSurface;
    }

    @Override
    public void run() {
        Canvas c = mSurface.getHolder().lockCanvas();
        if (c!=null){
            c.drawColor(Color.BLACK);
            mSurface.Draw(c);
            mSurface.getHolder().unlockCanvasAndPost(c);
        }
    }
}
