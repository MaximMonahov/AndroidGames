package com.example.packman;

import android.content.Context;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Максим on 02.09.2017.
 */
public class MapXMLParserHandler {

    Context m_context;

    MapXMLParserHandler(Context context)
    {
        m_context = context;
    }

    int[][] getMapByXMLId(int id)
    {
        int[][] map ;
        ArrayList<String> CharMap = new ArrayList<String>();

        try {
            XmlPullParser parser =  m_context.getResources().getXml(id);
            Log.d("[TAG]", "Begin Parsing");
            boolean is_row = false;
            while(parser.getEventType() != XmlPullParser.END_DOCUMENT)
            {
                switch(parser.getEventType())
                {
                    case XmlPullParser.START_TAG:
                        Log.d("[TAG]", "START_TAG " + parser.getName());
                        is_row = true;
                            Log.d("[TAG]", "START_TAG is_row =" + is_row +" const string = "+ m_context.getString(R.string.tag_name_row));
                        break;
                    case  XmlPullParser.END_TAG:

                        break;
                    case XmlPullParser.TEXT:
                        Log.d("[TAG]", "TEXT " + parser.getText());
                        if(is_row) {
                            CharMap.add(parser.getText());
                            Log.d("[TAG]", "TEXT added to CharMap" );
                        }
                        break;
                }
                parser.next();
            }
            Log.d("[TAG]", "CharMap size = " + CharMap.size());
            if(CharMap.size()>0) {
                Log.d("[TAG]", "Almost returned map");
                map = new int[CharMap.size()][CharMap.get(0).length()];
                Log.d("[TAG]", "Almost returned map2");
                Log.d("[TAG]", "MAP size [" + map.length + "] [" + map[0].length + "]");
                for(int i = 0; i<map.length; i++) {
                    for (int j = 0; j < map[i].length; j++) {
                        map[i][j] = GameLogic.getTypeByChar(CharMap.get(i).charAt(j));
                        Log.d("[TAG]", "int value for i =" + i + "  j = " + j + " is " + map[i][j]);
                    }
                }
                return map;
            }

        }
        catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new int[0][0];
    }
}
