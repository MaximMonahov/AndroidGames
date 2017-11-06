package com.example.handlers;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    Handler handler;
    ProgressBar progressBar ;
    TextView tv;
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);
        tv = (TextView) findViewById(R.id.textView);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setProgress(0);
        handler = new Handler(){
            public void handleMessage(android.os.Message msg)
            {
                tv.setText("Amount of handled files is " + msg.what);
                progressBar.setProgress(msg.what *10);
                if(msg.what == 10)
                {
                    button.setEnabled(true);
                }
            }
        };

    }

    public void OnClick(View view)
    {
        switch (view.getId())
        {
            case R.id.button: {
                button.setEnabled(false);
                Thread thread = new Thread( new Runnable() {
                    @Override
                    public void run() {
                        for(int i =0;i<=10;i++) {
                            try {
                                TimeUnit.SECONDS.sleep(1);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            //handler.ob
                            Message msg = handler.obtainMessage(i, 10-i);
                            handler.sendMessage(msg);
                        }
                    }
                });
                thread.start();
                break;
            }
            case R.id.buttontest: {
                Toast.makeText(this, "Test", Toast.LENGTH_SHORT);
                Log.d("[test]","testovui test");
                break;
            }
        }
    }
}


