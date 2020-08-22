package com.chris.tiantian.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jianjianhong on 20-1-7
 */
public class New extends ImageDetail implements Parcelable {

    /**
     * id : 13
     * title : 测试
     * comefrom : 天机1
     * time : 2020-05-17 22:20:00
     * text :
     * imgFileName : 1598041153416_a1.png
     * detailFileName : 1598041153416_detail_detail_a1.png
     * img : http://114.67.204.96:8080/upload/news/1598041153416_a1.png
     * detail : http://114.67.204.96:8080/upload/news/1598041153416_detail_detail_a1.png
     */

    private String comefrom;
    private String time;
    private String text;
    private String detailFileName;

    public New(Parcel in) {
        super(in);
        comefrom = in.readString();
        time = in.readString();
        text = in.readString();
        detailFileName = in.readString();
    }

    public static final Creator<New> CREATOR = new Creator<New>() {
        @Override
        public New createFromParcel(Parcel in) {
            return new New(in);
        }

        @Override
        public New[] newArray(int size) {
            return new New[size];
        }
    };

    public String getComefrom() {
        return comefrom;
    }

    public void setComefrom(String comefrom) {
        this.comefrom = comefrom;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDetailFileName() {
        return detailFileName;
    }

    public void setDetailFileName(String detailFileName) {
        this.detailFileName = detailFileName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(comefrom);
        dest.writeString(time);
        dest.writeString(text);
        dest.writeString(detailFileName);
    }
}
