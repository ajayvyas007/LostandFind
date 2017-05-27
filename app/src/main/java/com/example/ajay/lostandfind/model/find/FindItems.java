package com.example.ajay.lostandfind.model.find;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.StrictMode;

/**
 * Created by ajay on 9/3/17.
 */

public class FindItems implements Parcelable {
    private int mID;
    private String mName;
    private String mDescription;
    private String mContactName;
    private String mImageUrl;
    private String mDate;

    /**
     * A constructor that initializes the FindItem object
     **/
    public FindItems(int sID,String sName,String sContactName ,String sDescription, String sImageURL,String sDate){
        this.mID = sID;
        this.mName=sName;
        this.mDescription=sDescription;
        this.mContactName=sContactName;
        this.mImageUrl=sImageURL;
        this.mDate=sDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Storing the FindItem data to Parcel object
     **/
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mID);
        dest.writeString(mName);
        dest.writeString(mDescription);
        dest.writeString(mContactName);
        dest.writeString(mImageUrl);
        dest.writeString(mDate);
    }

    /**
     * Retrieving Student data from Parcel object
     * This constructor is invoked by the method createFromParcel(Parcel source) of
     * the object CREATOR
     **/
    private FindItems(Parcel in){
        this.mID = in.readInt();
        this.mName = in.readString();
        this.mDescription = in.readString();
        this.mContactName = in.readString();
        this.mImageUrl=in.readString();
        this.mDate=in.readString();
    }

    public static final Parcelable.Creator<FindItems> CREATOR = new Parcelable.Creator<FindItems>() {

        @Override
        public FindItems createFromParcel(Parcel source) {
            return new FindItems(source);
        }

        @Override
        public FindItems[] newArray(int size) {
            return new FindItems[size];
        }
    };

    public int getmID() {
        return mID;
    }

    public void setmID(int mID) {
        this.mID = mID;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getmContactName() {
        return mContactName;
    }

    public void setmContactName(String mContactName) {
        this.mContactName = mContactName;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }
}
