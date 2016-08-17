package com.az.airzoon.dataobjects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by siddharth on 8/17/2016.
 * "message": "User created successfully",
 * "sucess": true,
 */
public class BaseModel implements Parcelable {
    private String message;
    private String sucess;

    public BaseModel() {

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSucess() {
        return sucess;
    }

    public void setSucess(String sucess) {
        this.sucess = sucess;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(message);
        dest.writeString(sucess);
    }

    protected BaseModel(Parcel in) {
        message = in.readString();
        sucess = in.readString();
    }

    public static final Creator<BaseModel> CREATOR = new Creator<BaseModel>() {
        @Override
        public BaseModel createFromParcel(Parcel in) {
            return new BaseModel(in);
        }

        @Override
        public BaseModel[] newArray(int size) {
            return new BaseModel[size];
        }
    };
}
