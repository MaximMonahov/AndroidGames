package com.example.tetris;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

/**
 * Created by Максим on 09.09.2017.
 */
public class GameActivity extends AppCompatActivity {

    GameView game_view ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        game_view = new GameView(this);
        setContentView(game_view);
    }
}
