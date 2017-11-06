package com.example.asynctask;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.asynctasks.MyTask;

public class MainActivity extends AppCompatActivity {

    MyTask async_task;
    ProgressBar progress;
    TextView tv;
    TextView tv_push_btn;
    Button btn;
    Button btn_start;
    Button button_play;
    int i=0;
    MediaPlayer sound_player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         progress = (ProgressBar) findViewById(R.id.progressBar);
         tv  = (TextView) findViewById(R.id.textView);
         tv_push_btn  = (TextView) findViewById(R.id.textView2);
         btn = (Button) findViewById(R.id.button);
        btn_start = (Button) findViewById(R.id.button2);
        button_play= (Button) findViewById(R.id.button3);
        sound_player = MediaPlayer.create(this, R.raw.futurama);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_push_btn.setText("Push button time: " + i);
                //Toast.makeText(v.getContext(),"test " + i,Toast.LENGTH_SHORT).show();
                i++;
            }
        });

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                async_task = new MyTask();
                async_task.setTv(tv);
                async_task.setProgressBar(progress);
                async_task.execute();
            }
        });

        button_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sound_player!=null && !sound_player.isPlaying())
                sound_player.start();
            }
        });


    }
}
