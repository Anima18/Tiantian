package com.chris.tiantian.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jianjianhong on 20-1-7
 */
public class Advertise implements Parcelable {

    /**
     * id : 1
     * title : 暑假5折
     * imgFileName : 1495353501938_ad_1.png
     * link : http://www.imooc.com/wap/index
     * weight : 6
     * img : http://114.67.65.199:8880/upload/ad/1495353501938_ad_1.png
     */

    private int id;
    private String title;
    private String imgFileName;
    private String link;
    private int weight;
    private String img;
    private String detail;

    protected Advertise(Parcel in) {
        id = in.readInt();
        title = in.readString();
        imgFileName = in.readString();
        link = in.readString();
        weight = in.readInt();
        img = in.readString();
        detail = in.readString();
    }

    public static final Creator<Advertise> CREATOR = new Creator<Advertise>() {
        @Override
        public Advertise createFromParcel(Parcel in) {
            return new Advertise(in);
        }

        @Override
        public Advertise[] newArray(int size) {
            return new Advertise[size];
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
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
        dest.writeString(link);
        dest.writeInt(weight);
        dest.writeString(img);
        dest.writeString(detail);
    }
}
