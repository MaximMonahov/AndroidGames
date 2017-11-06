package com.example.snake;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Максим on 20.08.2017.
 */
public class AddWinnerActivity extends AppCompatActivity implements View.OnClickListener{

    File directory;
    TextView tv_score;
    EditText edt_name;
    Button btn_Ok;
    Button btn_Cancel;
    Button btn_photo;
    int score;
    ImageView  ivPhoto;
    Bitmap photo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_winner);
        edt_name = (EditText) findViewById(R.id.editText);
        tv_score = (TextView) findViewById(R.id.tv_your_score);
        btn_Ok = (Button) findViewById(R.id.btn_ok);
        btn_Cancel = (Button) findViewById(R.id.btn_cancel);
        btn_Ok.setOnClickListener(this);
        btn_Cancel.setOnClickListener(this);
        btn_photo = (Button) findViewById(R.id.btn_photo);
        btn_photo.setOnClickListener(this);
        ivPhoto = (ImageView) findViewById(R.id.imageView);
        Intent intent = getIntent();
        score = intent.getIntExtra(getString(R.string.extra_score), 0);
        tv_score.setText(getString(R.string.yor_score_string) + score );
        createDirectory();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.btn_ok:
                Date date = new Date();
                WinnerItem winner = new WinnerItem( edt_name.getText().toString(), score, date.toString(), photo );
                Intent intent = new Intent();
                intent.putExtra(WinnerItem.class.getCanonicalName(), winner);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.btn_cancel:
                setResult(RESULT_CANCELED);
                finish();
                break;
            case R.id.btn_photo:
                Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent1.putExtra(MediaStore.EXTRA_OUTPUT, generateFileUri());
                Log.d("[PHOTO NAME]", "Send intent");
                startActivityForResult(intent1, MainActivity.TAKE_PHOTO_CODE);

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        Log.d("[PHOTO NAME]", "onActivityResult");
        Log.d("[PHOTO NAME]", "request = " + requestCode);
        if (requestCode == MainActivity.TAKE_PHOTO_CODE) {
            if (resultCode == RESULT_OK) {
                if (intent == null) {
                    Log.d("[PHOTO NAME]", "Intent is null");
                } else {
                    Log.d("[PHOTO NAME]", "Photo uri: " + intent.getData());
                    Bundle bndl = intent.getExtras();
                    if (bndl != null) {
                        Object obj = intent.getExtras().get("data");
                        if (obj instanceof Bitmap) {
                            Bitmap bitmap = (Bitmap) obj;
                            Log.d("[PHOTO NAME]", "bitmap " + bitmap.getWidth() + " x "
                                    + bitmap.getHeight());
                            ivPhoto.setImageBitmap(bitmap);
                            photo = bitmap;
                        }
                    }
                }
            } else if (resultCode == RESULT_CANCELED) {
                Log.d("[PHOTO NAME]", "Canceled");
            }
        }
    }

    private Uri generateFileUri() {
        File file = new File(directory.getPath() + "/" + "photo_"
                + edt_name.getText() + ".jpg");

        Log.d("[PHOTO NAME]", "fileName = " + file);
        return Uri.fromFile(file);
    }

    private void createDirectory() {
        directory = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "MyFolder");
        if (!directory.exists())
            directory.mkdirs();
    }
}

