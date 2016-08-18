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
    private boolean sucess = true;

    public BaseModel() {

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSucess() {
        return sucess;
    }

    public void setSucess(boolean sucess) {
        this.sucess = sucess;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(message);
        dest.writeByte((byte) (sucess ? 1 : 0));
    }

    protected BaseModel(Parcel in) {
        message = in.readString();
        sucess = in.readByte() != 0;
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
