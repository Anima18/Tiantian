package com.chris.tiantian.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jianjianhong on 20-1-7
 */
public class New implements Parcelable {

    /**
     * id : 1
     * title : 标题1
     * comefrom : 新闻来源
     * time : 发布时间
     * text : 新闻正文新闻正文
     */

    private int id;
    private String title;
    private String comefrom;
    private String time;
    private String text;

    protected New(Parcel in) {
        id = in.readInt();
        title = in.readString();
        comefrom = in.readString();
        time = in.readString();
        text = in.readString();
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(comefrom);
        dest.writeString(time);
        dest.writeString(text);
    }
}
