package com.example.packman;

import java.util.TimerTask;

/**
 * Created by Максим on 02.09.2017.
 */
public class StepUpdater extends TimerTask {

    GameActivity game_activity;

    public StepUpdater(GameActivity game_activity) {
        this.game_activity = game_activity;
    }

    @Override
    public void run() {
        game_activity.Step();
    }
}
