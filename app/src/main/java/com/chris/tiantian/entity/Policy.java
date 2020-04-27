package com.chris.tiantian.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jianjianhong on 20-1-8
 */
public class Policy implements Parcelable {

    /**
     * id : 1
     * name : 策略名称
     * developerId : 设计者ID
     * fileName : 策略文件
     * kind : 策略分类
     * market : 适合的市场
     * timeLevel : 适合的时间级别
     * direction : 适合的方向
     * accuracy : 87
     * profit : 66
     * verifiedLevel : 2
     * price : 199
     * publishDate : 发布日期
     * refineTimes : 6
     * subscribeNumber : 100
     * weight : 0
     * file : http://114.67.65.199:8880/upload/po/策略文件
     */

    public int id;
    public String name;
    public String developerId;
    public String fileName;
    public String kind;
    public String market;
    public String timeLevel;
    public String direction;

    public int accuracy;
    public int profit;
    public int verifiedLevel;
    public int price;
    public String publishDate;
    public int refineTimes;
    public int subscribeNumber;
    public int weight;
    public String file;

    public Policy() {}

    protected Policy(Parcel in) {
        id = in.readInt();
        name = in.readString();
        developerId = in.readString();
        fileName = in.readString();
        kind = in.readString();
        market = in.readString();
        timeLevel = in.readString();
        direction = in.readString();
        accuracy = in.readInt();
        profit = in.readInt();
        verifiedLevel = in.readInt();
        price = in.readInt();
        publishDate = in.readString();
        refineTimes = in.readInt();
        subscribeNumber = in.readInt();
        weight = in.readInt();
        file = in.readString();
    }

    public static final Creator<Policy> CREATOR = new Creator<Policy>() {
        @Override
        public Policy createFromParcel(Parcel in) {
            return new Policy(in);
        }

        @Override
        public Policy[] newArray(int size) {
            return new Policy[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeveloperId() {
        return developerId;
    }

    public void setDeveloperId(String developerId) {
        this.developerId = developerId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getTimeLevel() {
        return timeLevel;
    }

    public void setTimeLevel(String timeLevel) {
        this.timeLevel = timeLevel;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    public int getProfit() {
        return profit;
    }

    public void setProfit(int profit) {
        this.profit = profit;
    }

    public int getVerifiedLevel() {
        return verifiedLevel;
    }

    public void setVerifiedLevel(int verifiedLevel) {
        this.verifiedLevel = verifiedLevel;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public int getRefineTimes() {
        return refineTimes;
    }

    public void setRefineTimes(int refineTimes) {
        this.refineTimes = refineTimes;
    }

    public int getSubscribeNumber() {
        return subscribeNumber;
    }

    public void setSubscribeNumber(int subscribeNumber) {
        this.subscribeNumber = subscribeNumber;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(developerId);
        dest.writeString(fileName);
        dest.writeString(kind);
        dest.writeString(market);
        dest.writeString(timeLevel);
        dest.writeString(direction);
        dest.writeInt(accuracy);
        dest.writeInt(profit);
        dest.writeInt(verifiedLevel);
        dest.writeInt(price);
        dest.writeString(publishDate);
        dest.writeInt(refineTimes);
        dest.writeInt(subscribeNumber);
        dest.writeInt(weight);
        dest.writeString(file);
    }

    @Override
    public String toString() {
        return "Policy{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", developerId='" + developerId + '\'' +
                ", fileName='" + fileName + '\'' +
                ", kind='" + kind + '\'' +
                ", market='" + market + '\'' +
                ", timeLevel='" + timeLevel + '\'' +
                ", direction='" + direction + '\'' +
                ", accuracy=" + accuracy +
                ", profit=" + profit +
                ", verifiedLevel=" + verifiedLevel +
                ", price=" + price +
                ", publishDate='" + publishDate + '\'' +
                ", refineTimes=" + refineTimes +
                ", subscribeNumber=" + subscribeNumber +
                ", weight=" + weight +
                ", file='" + file + '\'' +
                '}';
    }
}
