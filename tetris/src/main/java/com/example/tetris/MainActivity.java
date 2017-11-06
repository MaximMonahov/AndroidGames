package com.example.tetris;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public final static int PLAY_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view)
    {
        switch(view.getId())
        {
            case R.id.btn_play:
                Intent intent = new Intent(this, GameActivity.class);
                startActivityForResult( intent ,PLAY_REQUEST_CODE );
                break;
            case R.id.btn_best:
                break;
            case R.id.btn_settings:
                break;
            case R.id.btn_exit:
                finish();
            break;

        }

    }
}
