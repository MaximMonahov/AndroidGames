package com.example.packman;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final int CODE_SCORE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void OnClick(View view)
    {
        switch (view.getId())
        {
            case R.id.btn_start:
                Intent intent = new Intent(this,GameActivity.class);
                startActivityForResult(intent, CODE_SCORE );
                break;
            case R.id.btn_settings:
                break;
            case R.id.btn_best:
                break;
            case R.id.btn_exit:
                finish();
                break;
        }
    }
}
