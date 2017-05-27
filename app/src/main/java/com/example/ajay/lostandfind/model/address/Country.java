package com.example.ajay.lostandfind.model.address;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ajay on 19/3/17.
 */

public class Country implements Parcelable{
    private String mID;
    private String mName;
    private String mShortName;
    private String mPhoneCode;

    protected Country(Parcel in) {
        mID = in.readString();
        mName = in.readString();
        mShortName = in.readString();
        mPhoneCode = in.readString();
    }

    public Country(String id,String name,String shortName,String phoneCode)
    {
        setmID(id);
        setmName(name);
        setmShortName(shortName);
        setmPhoneNumber(phoneCode);
    }

    public static final Creator<Country> CREATOR = new Creator<Country>() {
        @Override
        public Country createFromParcel(Parcel in) {
            return new Country(in);
        }

        @Override
        public Country[] newArray(int size) {
            return new Country[size];
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
        dest.writeString(mShortName);
        dest.writeString(mPhoneCode);
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

    public String getmShortName() {
        return mShortName;
    }

    public void setmShortName(String mShortName) {
        this.mShortName = mShortName;
    }

    public String getmPhoneNumber() {
        return mPhoneCode;
    }

    public void setmPhoneNumber(String mPhoneNumber) {
        this.mPhoneCode = mPhoneNumber;
    }
}
