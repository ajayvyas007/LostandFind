package com.example.ajay.lostandfind.model.itemproperty;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ajay on 14/3/17.
 */

public class CardType implements Parcelable{
    private String mID;
    private String mType;

    protected CardType(Parcel in) {
        mID = in.readString();
        mType = in.readString();
    }

    public CardType(String id,String type)
    {
        this.mID=id;
        this.mType=type;
    }

    public static final Creator<CardType> CREATOR = new Creator<CardType>() {
        @Override
        public CardType createFromParcel(Parcel in) {
            return new CardType(in);
        }

        @Override
        public CardType[] newArray(int size) {
            return new CardType[size];
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
