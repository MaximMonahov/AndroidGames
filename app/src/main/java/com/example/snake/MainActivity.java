package com.example.snake;

import android.content.Intent;
import android.os.Parcel;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int ADD_WINNER_CODE = 1;
    public static final int TAKE_PHOTO_CODE = 2;
    public static final int NONE_MODE = 0;
    public static int GAME_MODE=0;
    public static int GAME_SCORE=0;

    Button btn_exit;
    Button btn_start;
    Button btn_show_winners;
    DBHandler db_handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);
        btn_start = (Button) this.findViewById(R.id.button);
        btn_start.setOnClickListener(this);
        btn_show_winners = (Button) this.findViewById(R.id.button_best);
        btn_show_winners.setOnClickListener(this);
        db_handler = new DBHandler(this);
        btn_exit = (Button) findViewById(R.id.button_exit);
        btn_exit.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onStart(){
        super.onStart();
        if (GAME_MODE == ADD_WINNER_CODE){

            Intent i = new Intent(this, AddWinnerActivity.class);
            i.putExtra(getString(R.string.extra_score),GAME_SCORE);
            this.startActivityForResult(i, ADD_WINNER_CODE);
        }
        GAME_MODE = NONE_MODE;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null || resultCode == RESULT_CANCELED) {return;}
        WinnerItem new_winner = data.getParcelableExtra(WinnerItem.class.getCanonicalName());
        db_handler.Add(new_winner);
    }

    public void onClick(View v) {

        switch(v.getId())
        {
            case R.id.button: {
                Intent i = new Intent(this, GameActivity.class);
                GAME_MODE = 0;
                GAME_SCORE = 0;
                this.startActivity(i);
            }
                break;
            case R.id.button_best:
            {
                Intent i = new Intent(this, WinnersActivity.class);
                ArrayList<WinnerItem> winners_list = db_handler.Read();
                i.putExtra(WinnerItem.class.getCanonicalName(), winners_list);
                this.startActivity(i);
            }
             break;
            case R.id.button_exit:
                finish();
                break;
        }
    }
}
