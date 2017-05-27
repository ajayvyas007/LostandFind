package com.example.ajay.lostandfind.model.itemproperty;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ajay on 18/3/17.
 */

public class Gender implements Parcelable {
    private String id;
    private String type;

    public Gender(String sid,String stype)
    {
        this.id=sid;
        this.type=stype;
    }

    protected Gender(Parcel in) {
        id = in.readString();
        type = in.readString();
    }

    public static final Creator<Gender> CREATOR = new Creator<Gender>() {
        @Override
        public Gender createFromParcel(Parcel in) {
            return new Gender(in);
        }

        @Override
        public Gender[] newArray(int size) {
            return new Gender[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(type);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
