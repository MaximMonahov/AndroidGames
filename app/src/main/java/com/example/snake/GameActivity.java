package com.example.snake;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import java.util.List;
import java.util.Timer;

/**
 * Created by Максим on 13.08.2017.
 */
public class GameActivity extends AppCompatActivity implements SensorEventListener, View.OnTouchListener {

    GameSurface mSurface;
    Timer timer;
    SensorManager sensor_manager;
    Sensor accelerometr;
    int width, height;
    boolean firstTime;
    float SSX = 0, SSY = 0;
    float SX = 0, SY = 0;
    float X0 = 0, Y0 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSurface = new GameSurface(this);
        mSurface.setOnTouchListener(this);
        setContentView(mSurface);

        timer = new Timer();
        width = this.getWindowManager().getDefaultDisplay().getWidth();
        height = this.getWindowManager().getDefaultDisplay().getHeight();

        sensor_manager = (SensorManager) getSystemService(Activity.SENSOR_SERVICE);
        List<Sensor> sensors = sensor_manager.getSensorList(Sensor.TYPE_ALL);
        if (sensors.size() > 0) {
            for (Sensor sensor : sensors) {
                if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                    if (accelerometr == null)
                        accelerometr = sensor;
                }
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Запускаем таймер обновления картинки на экране
        timer.scheduleAtFixedRate(new GraphUpdater(mSurface), 0, 100);
        // Запускаем таймер обновления положения змейки
        timer.scheduleAtFixedRate(new StepUpdater(this), 0, 500);

        sensor_manager.registerListener(this, accelerometr,  SensorManager.SENSOR_DELAY_GAME);
        this.firstTime = true;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    public void onStop() {
        super.onStop();
        timer.cancel();
        timer.purge();
        sensor_manager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        //mSurface.setSomeText("Your score is: "+SimpleSnakeActivity.GAME_SCORE);

        // получаем показания датчика
        SX = event.values[0];
        SY = event.values[1];

        // Если игра уже идет, то
        if (!this.firstTime) {
            // получаем положение телефона в пространстве
            // с поправкой на его начальное положение
            float dirX = SX - SSX;
            float dirY = SY - SSY;
            // Устанавливаем для змеи новое направление
//            if(getDirection(dirX, dirY) == mSurface.mGame.DIR_EAST && mSurface.mGame.getDirection() != mSurface.mGame.DIR_WEST
//            || getDirection(dirX, dirY) == mSurface.mGame.DIR_WEST && mSurface.mGame.getDirection() != mSurface.mGame.DIR_EAST
//            || getDirection(dirX, dirY) == mSurface.mGame.DIR_NORTH && mSurface.mGame.getDirection() != mSurface.mGame.DIR_SOUTH
//            || getDirection(dirX, dirY) == mSurface.mGame.DIR_SOUTH && mSurface.mGame.getDirection() != mSurface.mGame.DIR_NORTH)
//                mSurface.mGame.setDirection(this.getDirection(dirX, dirY));
            // передаем в нашу повержность координаты телефона в пространстве
           // mSurface.setXY(dirX, dirY);
        } else {
            // Если игра только началась делаем поправку на начальное
            // положение телефона
            this.firstTime = false;
            SSX = SX;
            SSY = SY;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void Step() {
        mSurface.IncrementStepCounter();
        if (!mSurface.mGame.NextStep()) {
            MainActivity.GAME_MODE= MainActivity.ADD_WINNER_CODE;
            this.finish();
        }
        else{
            MainActivity.GAME_SCORE = this.mSurface.mGame.mScore;
        }
    }


    private int getDirection(float x, float y) {
//        if (Math.abs(x) > Math.abs(y)) {
//            if (x > 0) {
//                return SnakeGameLogic.DIR_WEST;
//            } else {
//                return SnakeGameLogic.DIR_EAST;
//            }
//        } else {
//            if (y > 0) {
//                return SnakeGameLogic.DIR_SOUTH;
//            } else {
//                return SnakeGameLogic.DIR_NORTH;
//            }
//        }
        if (Math.abs(x-X0) > Math.abs(y-Y0)) {
            if(x>X0)
            {
                return SnakeGameLogic.DIR_WEST;
            }
            else
            {
                return SnakeGameLogic.DIR_EAST;
            }
        }
        else
        {
            if(y>Y0)
            {
                return SnakeGameLogic.DIR_SOUTH;
            }
            else
            {
                return SnakeGameLogic.DIR_NORTH;
            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                X0 = x;
                Y0 = y;
                mSurface.setIsActivityTouched(true);
                mSurface.setXY0(x, y);
                Log.d("onTouch", "ACTION DOWN!!!");

                break;
            case MotionEvent.ACTION_UP:
                mSurface.setIsActivityTouched(false);
                Log.d("onTouch", "ACTION UP!!!");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("onTouch", "ACTION MOVE!!!");
                float max_radius = mSurface.getFullAreaRadius()- mSurface.getTouchAreaRadius();

                double newX = X0, newY = Y0;
                double deltaX = Math.sin(Math.atan(Math.abs((x-X0)/(y-Y0))))*max_radius;
                double deltaY = Math.cos(Math.atan(Math.abs((x - X0) / (y-Y0))))*max_radius;
                deltaX = X0> x ? deltaX*(-1):deltaX ;
                deltaY = Y0> y ? deltaY*(-1):deltaY ;

                newX = (Math.abs(X0 - x) > max_radius)? X0 + deltaX : x;
                newY = (Math.abs(Y0 - y) > max_radius)? Y0 + deltaY : y;
                Log.d("onTouch", "newX = " + (float)newX + "  newY = " + (float)newY);
               // mSurface.setXY(x, y);
                mSurface.setXY((float)newX,(float)newY);
                mSurface.mGame.setDirection(this.getDirection((float) newX,(float)newY));
                break;
        }
        return true;
    }
}
