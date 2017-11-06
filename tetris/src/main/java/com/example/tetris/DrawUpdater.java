package com.example.tetris;

import android.graphics.Canvas;
import android.graphics.Color;

import java.util.TimerTask;

/**
 * Created by Максим on 09.09.2017.
 */
public class DrawUpdater extends TimerTask {
    GameView game_view ;

    public DrawUpdater(GameView game_view) {
        this.game_view = game_view;
    }


    @Override
    public void run() {
        Canvas canvas = game_view.getHolder().lockCanvas();
        if(canvas != null)
        {
            canvas.drawColor(Color.BLACK);
            game_view.Draw(canvas);
            game_view.getHolder().unlockCanvasAndPost(canvas);
        }
    }
}
