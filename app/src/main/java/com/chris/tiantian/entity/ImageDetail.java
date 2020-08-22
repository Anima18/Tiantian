package com.chris.tiantian.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jianjianhong on 20-8-22
 */
public class ImageDetail implements Parcelable {
    private int id;
    private String title;
    private String imgFileName;
    private String img;
    private String detail;

    protected ImageDetail(Parcel in) {
        id = in.readInt();
        title = in.readString();
        imgFileName = in.readString();
        img = in.readString();
        detail = in.readString();
    }

    public static final Creator<ImageDetail> CREATOR = new Creator<ImageDetail>() {
        @Override
        public ImageDetail createFromParcel(Parcel in) {
            return new ImageDetail(in);
        }

        @Override
        public ImageDetail[] newArray(int size) {
            return new ImageDetail[size];
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

    public String getImgFileName() {
        return imgFileName;
    }

    public void setImgFileName(String imgFileName) {
        this.imgFileName = imgFileName;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(imgFileName);
        dest.writeString(img);
        dest.writeString(detail);
    }
}
