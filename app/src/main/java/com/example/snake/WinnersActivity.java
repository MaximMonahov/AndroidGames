package com.example.snake;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Максим on 20.08.2017.
 */
public class WinnersActivity extends AppCompatActivity  {

    ListView lv;
    Button btn_clear_all;
    SimpleAdapter adapter;
    ArrayList<Map<String, Object> > list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winners);


        lv =(ListView) findViewById(R.id.listView);
        btn_clear_all = (Button) findViewById(R.id.btn_clearall);
        ArrayList<WinnerItem> winners_list = getIntent().getParcelableArrayListExtra(WinnerItem.class.getCanonicalName());
         list = new ArrayList<Map<String, Object> >();
        String[] from = {getString(R.string.db_param_name), getString(R.string.db_param_score), getString(R.string.db_param_time),getString(R.string.param_name_photo)};
        int[] to = {R.id.tv_name , R.id.tv_score, R.id.tv_time, R.id.iv_photo};
        for(WinnerItem i:winners_list)
        {
            Map m = new HashMap<String, Object>();
            m.put(getString(R.string.db_param_name), i.getName());
            m.put(getString(R.string.db_param_score), i.getScore());
            m.put(getString(R.string.db_param_time), i.getTime());
            m.put(getString(R.string.param_name_photo), i.getPhoto());
            list.add(m);
        }
        adapter = new SimpleAdapter(this, list, R.layout.activity_best_winners_item, from, to);
        adapter.setViewBinder(new BitmapViewBinder());
        lv.setAdapter(adapter);


        btn_clear_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.clear();
                adapter.notifyDataSetChanged();
            }
        });
    }

    class BitmapViewBinder implements SimpleAdapter.ViewBinder
    {
        @Override
        public boolean setViewValue(View view, Object data, String textRepresentation) {

            switch(view.getId()) {
                case R.id.iv_photo:
                    Log.d("[setViewValue]","view is imageview");
                    if (data instanceof Bitmap) {
                        Log.d("[setViewValue]","data is Bitmap");
                        Bitmap bitmap = (Bitmap) data;
                        if(view instanceof ImageView) {
                            ImageView im_view = (ImageView) view;
                            im_view.setImageBitmap(bitmap);
                        }
                    }
                    return true;

            }
            return false;
        }
    };
}
