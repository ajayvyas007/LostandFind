package com.example.ajay.lostandfind.model.address;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ajay on 19/3/17.
 */

public class State implements Parcelable{
    private String mID;
    private String mName;
    private String mCountryID;

    protected State(Parcel in) {
        mID = in.readString();
        mName = in.readString();
        mCountryID = in.readString();
    }

    public State(String id,String name,String countrycode)
    {
        setmID(id);
        setmName(name);
        setmCountryID(countrycode);
    }

    public static final Creator<State> CREATOR = new Creator<State>() {
        @Override
        public State createFromParcel(Parcel in) {
            return new State(in);
        }

        @Override
        public State[] newArray(int size) {
            return new State[size];
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
        dest.writeString(mCountryID);
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

    public String getmCountryID() {
        return mCountryID;
    }

    public void setmCountryID(String mCountryID) {
        this.mCountryID = mCountryID;
    }
}
