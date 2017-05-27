package com.example.ajay.lostandfind.model.itemproperty;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ajay on 14/3/17.
 */

public class Complexion implements Parcelable {
    private String mID;
    private String mType;

    public Complexion(String id,String type)
    {
        this.mID=id;
        this.mType=type;
    }


    protected Complexion(Parcel in) {
        mID = in.readString();
        mType = in.readString();
    }

    public static final Creator<Complexion> CREATOR = new Creator<Complexion>() {
        @Override
        public Complexion createFromParcel(Parcel in) {
            return new Complexion(in);
        }

        @Override
        public Complexion[] newArray(int size) {
            return new Complexion[size];
        }
    };

    public String getmID() {
        return mID;
    }

    public void setmID(String mID) {
        this.mID = mID;
    }

    public String getmType() {
        return mType;
    }

    public void setmType(String mType) {
        this.mType = mType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mID);
        dest.writeString(mType);
    }
}
