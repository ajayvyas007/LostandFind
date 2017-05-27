package com.example.ajay.lostandfind.model.itemproperty;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ajay on 14/3/17.
 */

public class BodyType implements Parcelable{
    private String mID;
    private String mType;


    protected BodyType(Parcel in) {
        mID = in.readString();
        mType = in.readString();
    }

    public BodyType(String id,String type)
    {
        this.mID=id;
        this.mType=type;
    }

    public static final Creator<BodyType> CREATOR = new Creator<BodyType>() {
        @Override
        public BodyType createFromParcel(Parcel in) {
            return new BodyType(in);
        }

        @Override
        public BodyType[] newArray(int size) {
            return new BodyType[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mID);
        dest.writeString(mType);
    }

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
}
