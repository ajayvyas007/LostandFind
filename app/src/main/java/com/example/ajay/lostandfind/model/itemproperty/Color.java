package com.example.ajay.lostandfind.model.itemproperty;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ajay on 14/3/17.
 */

public class Color implements Parcelable{
    private String mID;
    private String mName;

    public Color(String id,String type)
    {
        this.mID=id;
        this.mName=type;
    }

    protected Color(Parcel in) {
        mID = in.readString();
        mName = in.readString();
    }

    public static final Creator<Color> CREATOR = new Creator<Color>() {
        @Override
        public Color createFromParcel(Parcel in) {
            return new Color(in);
        }

        @Override
        public Color[] newArray(int size) {
            return new Color[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mID);
        dest.writeString(mName);
    }

    public String getmID() {
        return mID;
    }

    public void setmID(String mID) {
        this.mID = mID;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }
}
