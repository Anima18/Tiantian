package com.chris.tiantian.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jianjianhong on 20-1-7
 */
public class Advertise extends ImageDetail implements Parcelable {

    /**
     * id : 1
     * title : 暑假5折
     * imgFileName : 1495353501938_ad_1.png
     * link : http://www.imooc.com/wap/index
     * weight : 6
     * img : http://114.67.65.199:8880/upload/ad/1495353501938_ad_1.png
     */


    private String link;
    private int weight;

    protected Advertise(Parcel in) {
        super(in);
        link = in.readString();
        weight = in.readInt();
    }

    @Override
    public int describeContents() {
        return super.describeContents();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(link);
        dest.writeInt(weight);
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
}
