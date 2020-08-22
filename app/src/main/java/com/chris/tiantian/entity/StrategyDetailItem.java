package com.chris.tiantian.entity;

/**
 * Created by jianjianhong on 20-8-20
 */
public class StrategyDetailItem implements Comparable<StrategyDetailItem> {

    /**
     * id : 220
     * name : MACD全信号
     * developerId : 峥嵘岁月
     * kind : 信号全解
     * market : BTC
     * timeLevel : 15min
     * direction : 信号
     * accuracy : 80
     * profit : 80
     * verifiedLevel : 10
     * price : 80
     * publishDate : 2020.06.17 18:55:37
     * refineTimes : 0
     * subscribeNumber : 0
     * weight : 80
     * choosed : true
     */

    private int id;
    private String name;
    private String developerId;
    private String kind;
    private String market;
    private String timeLevel;
    private String direction;
    private float accuracy;
    private float profit;
    private float verifiedLevel;
    private float price;
    private String publishDate;
    private float refineTimes;
    private float subscribeNumber;
    private float weight;
    private boolean choosed;

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

    public float getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }

    public float getProfit() {
        return profit;
    }

    public void setProfit(float profit) {
        this.profit = profit;
    }

    public float getVerifiedLevel() {
        return verifiedLevel;
    }

    public void setVerifiedLevel(float verifiedLevel) {
        this.verifiedLevel = verifiedLevel;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public float getRefineTimes() {
        return refineTimes;
    }

    public void setRefineTimes(float refineTimes) {
        this.refineTimes = refineTimes;
    }

    public float getSubscribeNumber() {
        return subscribeNumber;
    }

    public void setSubscribeNumber(float subscribeNumber) {
        this.subscribeNumber = subscribeNumber;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public boolean isChoosed() {
        return choosed;
    }

    public void setChoosed(boolean choosed) {
        this.choosed = choosed;
    }

    @Override
    public int compareTo(StrategyDetailItem o) {
        return name.compareTo(o.getName());
    }
}
