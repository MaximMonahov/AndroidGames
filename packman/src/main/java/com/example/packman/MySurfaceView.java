package com.example.packman;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceView;

import java.util.ArrayList;

/**
 * Created by Максим on 01.09.2017.
 */
public class MySurfaceView extends SurfaceView {

    Bitmap m_bitmap_pack_man1;
    Bitmap m_bitmap_pack_man2;
    Bitmap m_bitmap_wall;
    Bitmap m_bitmap_ghost_1, m_bitmap_ghost_2, m_bitmap_ghost_3, m_bitmap_ghost_4;
    Bitmap m_bitmap_white_point;
    Bitmap m_bitmap_boom;
    boolean m_is_odd_step =false;
    private float x0 =0 , x = 0 ;
    private float y0 =0 , y = 0 ;
    private float whiteBallRadius = 10;
    private float touchAreaRadius = 40;
    private float fullAreaRadius = 120;

    GameLogic m_game;
    private int[][] map;
    private boolean isActivityTouched =false;

    public MySurfaceView(Context context) {
        super(context);
        m_game = new GameLogic();

        m_bitmap_pack_man1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.packman1);
        m_bitmap_pack_man2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.packman2);
        m_bitmap_wall = BitmapFactory.decodeResource(context.getResources(), R.drawable.wall);
        m_bitmap_ghost_1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.ghost_1);
        m_bitmap_ghost_2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.ghost_2);
        m_bitmap_ghost_3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.ghost_3);
        m_bitmap_ghost_4 = BitmapFactory.decodeResource(context.getResources(), R.drawable.ghost_4);
        //m_bitmap_white_point =  BitmapFactory.decodeResource(context.getResources(), R.drawable.white_ball);
        m_bitmap_boom =  BitmapFactory.decodeResource(context.getResources(), R.drawable.boom);
    }

    public void Draw(Canvas canvas)
    {
        int mx ,my;
        mx = canvas.getWidth()/getGame().getMapWidth();
        my = canvas.getHeight()/getGame().getMapHeight();
//        Log.d("[TAG]", "mx = " + mx);
//        Log.d("[TAG]", "my = " + my);
//        Log.d("[TAG]", "getMapWidth = " + getGame().getMapWidth());
//        Log.d("[TAG]", "getMapHeight = " + getGame().getMapHeight());


        Bitmap bitmap_pack_man1= Bitmap.createScaledBitmap(m_bitmap_pack_man1, mx, my, true);
        Bitmap bitmap_pack_man2= Bitmap.createScaledBitmap(m_bitmap_pack_man2, mx,my, true);
        Bitmap bitmap_wall= Bitmap.createScaledBitmap(m_bitmap_wall, mx,my, true);

        ArrayList<Bitmap> bitmap_ghost = new ArrayList<Bitmap>();
        bitmap_ghost.add(Bitmap.createScaledBitmap(m_bitmap_ghost_1, mx, my, true));
        bitmap_ghost.add(Bitmap.createScaledBitmap(m_bitmap_ghost_2, mx, my, true));
        bitmap_ghost.add(Bitmap.createScaledBitmap(m_bitmap_ghost_3, mx, my, true));
        bitmap_ghost.add(Bitmap.createScaledBitmap(m_bitmap_ghost_4, mx, my, true));

        //Bitmap bitmap_white_point = Bitmap.createScaledBitmap(m_bitmap_white_point, mx,my, true);
        Bitmap bitmap_boom= Bitmap.createScaledBitmap(m_bitmap_boom,mx, my, true);

        Paint paint = new Paint();

          for(int y=0 ; y <getGame().getMapHeight(); y++)
                for(int x= 0 ; x <getGame().getMapWidth();x++)
            {
                Bitmap current_bitmap = null;

                switch(getGame().getMap()[y][x])
                {
                    case GameLogic.GHOST: {
                       int index = getGame().getGhostIndex(y,x);
                        if(index != -1)
                            current_bitmap = bitmap_ghost.get(index);
                        break;
                    }
                    case GameLogic.PACKMAN: current_bitmap = m_is_odd_step? bitmap_pack_man1 : bitmap_pack_man2;
                        current_bitmap = RotateBitmap(current_bitmap, getGame().getmDirection());
                        break;
                    case GameLogic.WALL: current_bitmap = bitmap_wall;
                        break;
                    case GameLogic.WHITE_BALL:
//                        current_bitmap = bitmap_white_point;
                        paint.setColor(Color.WHITE);
                        canvas.drawCircle(x * mx + mx / 2, y * my + my / 2, whiteBallRadius, paint);
                        paint.setColor(Color.BLACK);
                        current_bitmap =null;

                        break;
                    case GameLogic.BOOM: current_bitmap = bitmap_boom;
                }
                if(current_bitmap != null)
                    canvas.drawBitmap(current_bitmap, x*mx, y*my ,paint);
            }
        paint.setColor(Color.YELLOW);
        paint.setTextSize(25);
        canvas.drawText("Your score is: " + getGame().getScore(), 20,20, paint);

        if(isActivityTouched)
        {
            paint.setColor(Color.CYAN);
            paint.setAlpha(128);
            canvas.drawCircle(x0, y0, fullAreaRadius, paint);
            paint.setColor(Color.BLUE);
            paint.setAlpha(128);
            canvas.drawCircle(x, y, touchAreaRadius, paint);
            paint.setColor(Color.BLACK);
        }
    }

    private Bitmap RotateBitmap(Bitmap current_bitmap, int dir) {
        Matrix matrix =new Matrix();
        switch(dir)
        {
            case GameLogic.DIR_NORTH: matrix.setRotate(-90); break;
            case GameLogic.DIR_SOUTH: matrix.setRotate(90); break;
            case GameLogic.DIR_WEST:  matrix.setScale(-1, 1); break;
            case GameLogic.DIR_EAST: break;
        }

        return Bitmap.createBitmap(current_bitmap,0,0, current_bitmap.getWidth(), current_bitmap.getHeight(),matrix, true);
    }

    public GameLogic getGame() {
        return m_game;
    }
    public void setXY0(float x, float y)
    {
        x0 = x;
        y0 = y;
    }

    public void setXY(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    public void OnStep()
    {
        m_is_odd_step = !m_is_odd_step;

    }


    public void setIsActivityTouched(boolean isActivityTouched) {
        this.isActivityTouched = isActivityTouched;
    }

    public float getFullAreaRadius() {
        return fullAreaRadius;
    }

    public float getTouchAreaRadius() {
        return touchAreaRadius;
    }
}

