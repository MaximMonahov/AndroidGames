package com.example.packman;

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceView;

import java.util.TimerTask;

/**
 * Created by Максим on 02.09.2017.
 */
public class GraphUpdater extends TimerTask {

    MySurfaceView surface;
    public GraphUpdater(MySurfaceView surface) {
        this.surface = surface;
    }

    @Override
    public void run() {
        Canvas canvas = surface.getHolder().lockCanvas();
        if(canvas != null)
        {
            canvas.drawColor(Color.BLACK);
            surface.Draw(canvas);
            surface.getHolder().unlockCanvasAndPost(canvas);
        }
    }
}
