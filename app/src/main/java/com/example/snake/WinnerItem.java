package com.example.snake;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Максим on 20.08.2017.
 */
public class WinnerItem implements Parcelable{

    private String name;
    private int score;
    private String time;
    private Bitmap photo;

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    WinnerItem()
    {
        name= "";
        score = 0;
        time = "00:00  00/00/00";

    }

    WinnerItem(String name, int score, String time , Bitmap photo)
    {
        this.name = name;
        this.score = score;
        this.time = time;
        this.photo = photo;
    }

    protected WinnerItem(Parcel in) {
        name = in.readString();
        score = in.readInt();
        time = in.readString();
        photo = in.readBundle().getParcelable("bitmap");

    }

    public static final Creator<WinnerItem> CREATOR = new Creator<WinnerItem>() {
        @Override
        public WinnerItem createFromParcel(Parcel in) {
            return new WinnerItem(in);
        }

        @Override
        public WinnerItem[] newArray(int size) {
            return new WinnerItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(score);
        dest.writeString(time);
        Bundle bundle = new Bundle();
        bundle.putParcelable("bitmap", photo);
        dest.writeBundle(bundle);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


}
