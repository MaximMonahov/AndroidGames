package com.example.snake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceView;

/**
 * Created by Максим on 13.08.2017.
 */
public class GameSurface extends SurfaceView {

    SnakeGameLogic mGame ;
    Bitmap mBgr, mSnakeHead1,mSnakeHead2, mSnakeTail, mFruit;
    float x,y;
    private boolean isActivityTouched = false;
    private float x0 =0;
    private float y0 =0;
    private float touchAreaRadius = 40;
    private float fullAreaRadius = 120;
    int m_step_counter = 0;

    public GameSurface(Context context) {
        super(context);
        mGame = new SnakeGameLogic();
        mBgr = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg);
        mSnakeHead1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.snake_head1);
        mSnakeHead2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.snake_head2);
        mSnakeTail = BitmapFactory.decodeResource(context.getResources(), R.drawable.body);
        mFruit = BitmapFactory.decodeResource(context.getResources(), R.drawable.fruite);
    }

    public void Draw(Canvas canvas)
    {
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        int mx = width/mGame.FIELD_WIDTH;
        int my = height/mGame.FIELD_HEIGHT;


        Bitmap bgr = Bitmap.createScaledBitmap(mBgr, mx,my, true);
        Bitmap snake_head1 = Bitmap.createScaledBitmap(mSnakeHead1, mx,my, true);
        Bitmap snake_head2 = Bitmap.createScaledBitmap(mSnakeHead2, mx,my, true);
        Bitmap snake_tail = Bitmap.createScaledBitmap(mSnakeTail, mx,my, true);
        Bitmap fruit = Bitmap.createScaledBitmap(mFruit, mx, my, true);

        snake_head1 = rotateBitmap(snake_head1);
        snake_head2 = rotateBitmap(snake_head2);

        Paint paint = new Paint();
        paint.setColor(Color.CYAN);

        if(isActivityTouched) {
            canvas.drawCircle(x0, y0, fullAreaRadius, paint);
            paint.setColor(Color.BLUE);
            canvas.drawCircle(x, y, touchAreaRadius, paint);
            paint.setColor(Color.BLACK);
            paint.setAlpha(128);
        }


        //1 Paint bgr and fruit
        for(int i= 0; i<mGame.FIELD_WIDTH; i++)
            for(int j= 0; j<mGame.FIELD_HEIGHT; j++)
            {
                canvas.drawBitmap(bgr, mx*i, my*j, paint);
                if(mGame.getmField()[i][j] == mGame.FRUIT)
                    canvas.drawBitmap(fruit, mx*i, my*j, paint);
            }

        // 2. draw snake
        for(int i =0; i<mGame.getSnakeLength(); i++)
        {
            if(i== mGame.getSnakeLength()-1)
            {

                canvas.drawBitmap( (m_step_counter%2==0)? snake_head1:snake_head2, mGame.getmSnake().get(i).x*mx, mGame.getmSnake().get(i).y*my , paint);
            }
            else
            {
                canvas.drawBitmap(snake_tail, mGame.getmSnake().get(i).x*mx, mGame.getmSnake().get(i).y*my , paint);
            }
        }
    }

    private Bitmap rotateBitmap(Bitmap image) {

        Matrix matrix = new Matrix();
        if(mGame.getDirection() == mGame.DIR_EAST)
        {
            matrix.setScale(-1, 1);
        }
        else if(mGame.getDirection() == mGame.DIR_NORTH)
        {
            matrix.setRotate(-90);
        }
        else if(mGame.getDirection() == mGame.DIR_SOUTH)
        {
            matrix.setRotate(90);
        }
        else return image;

        Bitmap bitmap = Bitmap.createBitmap(image, 0,0, image.getWidth(), image.getHeight(),matrix, true);
       // bitmap.recycle();
        return bitmap;
    }

    public void setXY(float dirX, float dirY) {
        this.x = dirX;
        this.y = dirY;
    }

    public void setIsActivityTouched(boolean isActivityTouched) {
        this.isActivityTouched = isActivityTouched;
    }

    public void setXY0(float x, float y) {
        this.x0 = x;
        this.y0 = y;
    }

    public float getTouchAreaRadius() {
        return touchAreaRadius;
    }

    public float getFullAreaRadius() {
        return fullAreaRadius;
    }

    public void IncrementStepCounter() {
        if(m_step_counter < Integer.MAX_VALUE)
             m_step_counter++;
        else m_step_counter =0;
    }
}

