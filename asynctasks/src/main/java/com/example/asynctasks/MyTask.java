package com.example.asynctasks;

import android.content.Intent;
import android.os.AsyncTask;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

/**
 * Created by Максим on 26.08.2017.
 */
public class MyTask extends AsyncTask<Void, Integer, Void> {

    TextView tv;

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    ProgressBar progressBar;

    public TextView getTv() {
        return tv;
    }

    public void setTv(TextView tv) {
        this.tv = tv;
    }



    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

        tv.setText("Background " + values[0]);
        progressBar.setProgress(values[0]*10);
    }

    @Override
     protected Void doInBackground(Void... voids) {
        for(int i =0;i<10;i++) {
            publishProgress(i);
            pause(2);
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        tv.setText("Start");
        pause(5);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        tv.setText("End");
    }

    void pause(int time)
    {
        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
