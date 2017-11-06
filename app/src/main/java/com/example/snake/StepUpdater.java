package com.example.snake;

import java.util.TimerTask;

/**
 * Created by Максим on 13.08.2017.
 */
public class StepUpdater extends TimerTask {

    GameActivity act;

    public StepUpdater(GameActivity gameActivity) {
        this.act = gameActivity;
    }

    @Override
    public void run() {
        act.Step();
    }
}
