package com.example.ajay.lostandfind.model.address;

import android.os.Parcel;
import android.os.Parcelable;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ajay on 19/3/17.
 */

public class City implements Parcelable{
    private String mID;
    private String mName;
    private String mCityId;

    protected City(Parcel in) {
        mID = in.readString();
        mName = in.readString();
        mCityId = in.readString();
    }

    public City(String id,String name,String cityid)
    {
        setmID(id);
        setmName(name);
        setmCityId(cityid);
    }

    public static final Creator<City> CREATOR = new Creator<City>() {
        @Override
        public City createFromParcel(Parcel in) {
            return new City(in);
        }

        @Override
        public City[] newArray(int size) {
            return new City[size];
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
        dest.writeString(mCityId);
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

    public String getmCityId() {
        return mCityId;
    }

    public void setmCityId(String mCityId) {
        this.mCityId = mCityId;
    }
}
