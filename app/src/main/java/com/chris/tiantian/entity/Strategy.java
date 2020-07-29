package com.chris.tiantian.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jianjianhong on 20-7-29
 */
public class Strategy implements Parcelable {
    private String code;
    private String name;
    private int imageRes;

    public Strategy(String code, String name, int imageRes) {
        this.code = code;
        this.name = name;
        this.imageRes = imageRes;
    }

    protected Strategy(Parcel in) {
        code = in.readString();
        name = in.readString();
        imageRes = in.readInt();
    }

    public static final Creator<Strategy> CREATOR = new Creator<Strategy>() {
        @Override
        public Strategy createFromParcel(Parcel in) {
            return new Strategy(in);
        }

        @Override
        public Strategy[] newArray(int size) {
            return new Strategy[size];
        }
    };

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageRes() {
        return imageRes;
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(code);
        dest.writeString(name);
        dest.writeInt(imageRes);
    }
}
