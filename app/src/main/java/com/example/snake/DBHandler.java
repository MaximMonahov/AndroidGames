package com.example.snake;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by Максим on 20.08.2017.
 */
public class DBHandler extends SQLiteOpenHelper {

    protected  Context ctx;

    public DBHandler(Context context)
    {
        super(context, context.getString(R.string.db_name), null, 1);
        this.ctx = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + ctx.getString(R.string.winners_table_name) + " ( " +
        "id integer primary key autoincrement" +
   ", " + ctx.getString(R.string.db_param_name)   + " " + "text"+
   ", " + ctx.getString(R.string.db_param_score)  + " " + "integer" +
   ", " + ctx.getString(R.string.db_param_time)   + " " + "text" +
   ", " + ctx.getString(R.string.param_name_photo)+ " " + "text" + " );" );

//        ClearAll();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public ArrayList<WinnerItem> Read()
    {
        SQLiteDatabase db = getWritableDatabase();
        ArrayList<WinnerItem> db_content = new ArrayList<WinnerItem>();
        Log.d("[LOG]", "Before Cursor get");
        Cursor cur = db.query(ctx.getString(R.string.winners_table_name ), null, null, null, null, null, null);
        Log.d("[LOG]", "After Cursor get");
        if (cur.moveToFirst()) {

            do {
                WinnerItem db_item = new WinnerItem();
                db_item.setName(cur.getString( cur.getColumnIndex(ctx.getString(R.string.db_param_name))));
                db_item.setScore(cur.getInt(cur.getColumnIndex(ctx.getString(R.string.db_param_score))));
                db_item.setTime(cur.getString(cur.getColumnIndex(ctx.getString(R.string.db_param_time))));
                db_item.setPhoto(convertToBitmap(cur.getString(cur.getColumnIndex(ctx.getString(R.string.param_name_photo)))));
                db_content.add(db_item);
            } while (cur.moveToNext());
        }
        Log.d("[LOG]", "After db_content fill");
        cur.close();
        Log.d("[LOG]", "After cur close");
        return db_content;
    }

    public void Add(WinnerItem newItem)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ctx.getString(R.string.db_param_name),newItem.getName() );
        cv.put(ctx.getString(R.string.db_param_score),newItem.getScore() );
        cv.put(ctx.getString(R.string.db_param_time),newItem.getTime() );
        cv.put(ctx.getString(R.string.param_name_photo), convertToBase64(newItem.getPhoto()));
        db.insert(ctx.getString(R.string.winners_table_name), null ,cv);
    }

    //конвертируем Bitmap на Base64
    public String convertToBase64(Bitmap bitmap) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
        byte[] byteArray = os.toByteArray();
        return Base64.encodeToString(byteArray, 0);
    }

    //и наоборот
    public Bitmap convertToBitmap(String base64String) {
        byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
        Bitmap bitmapResult = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return bitmapResult;
    }

    public void ClearAll()
    {
         SQLiteDatabase db = getWritableDatabase();
         db.delete( ctx.getString(R.string.winners_table_name), null, null);
    }

    public void Remove()
    {

    }
}
