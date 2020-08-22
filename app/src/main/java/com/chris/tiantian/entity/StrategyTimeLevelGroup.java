package com.chris.tiantian.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jianjianhong on 20-8-20
 */
public class StrategyTimeLevelGroup implements Comparable<StrategyTimeLevelGroup> {

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

    private String name;
    private String developerId;
    private String kind;
    private String market;
    private String direction;
    private float accuracy;
    private float profit;
    private float verifiedLevel;
    private float price;
    private String publishDate;
    private float refineTimes;
    private float subscribeNumber;
    private float weight;
    private Boolean choosed = false;
    private List<TimeLevel> timeLevels;

    public StrategyTimeLevelGroup(StrategyDetailItem item) {
        this.name = item.getName();
        this.developerId = item.getDeveloperId();
        this.kind = item.getKind();
        this.market = item.getMarket();
        this.direction = item.getDirection();
        this.accuracy = item.getAccuracy();
        this.profit = item.getProfit();
        this.publishDate = item.getPublishDate();
        this.refineTimes = item.getRefineTimes();
        this.subscribeNumber = item.getSubscribeNumber();
        this.weight = item.getWeight();
        this.timeLevels = new ArrayList<>();
    }

    public void addTimeLevel(TimeLevel timeLevel) {
        this.timeLevels.add(timeLevel);
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

    public Boolean getChoosed() {
        return choosed;
    }

    public void setChoosed(Boolean choosed) {
        this.choosed = choosed;
    }

    public List<TimeLevel> getTimeLevels() {
        return timeLevels;
    }

    public void setTimeLevels(List<TimeLevel> timeLevels) {
        this.timeLevels = timeLevels;
    }

    @Override
    public int compareTo(StrategyTimeLevelGroup o) {
        return o.choosed.compareTo(choosed);
    }

    public static class TimeLevel {
        private String timeLevel;
        private int id;
        private boolean choosed;

        public TimeLevel(int id, String timeLevel, boolean choosed) {
            this.timeLevel = timeLevel;
            this.id = id;
            this.choosed = choosed;
        }

        public String getTimeLevel() {
            return timeLevel;
        }

        public void setTimeLevel(String timeLevel) {
            this.timeLevel = timeLevel;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public boolean isChoosed() {
            return choosed;
        }

        public void setChoosed(boolean choosed) {
            this.choosed = choosed;
        }
    }
}
