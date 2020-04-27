package com.chris.tiantian.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by jianjianhong on 20-1-10
 */
@Entity(tableName = PolicySignalTable.TABLE_NAME)
public class PolicySignal {

    /**
     * id : 1
     * policyId : 1
     * policyName : 策略名称
     * direction : 操作方向
     * description : 信号描述
     * currPrice : 143.5
     * stopPrice : 145
     * time : 2019.12.31 11:00:00
     * policyKind : 趋势
     * policyMarket : 适于的市场
     * policyTimeLevel : 15min
     */

    @PrimaryKey
    public int id;
    public int policyId;
    public String policyName;
    public String direction;
    public String description;
    public double currPrice;
    public double stopPrice;
    public String time;
    public String policyKind;
    public String policyMarket;
    public String policyTimeLevel;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPolicyId() {
        return policyId;
    }

    public void setPolicyId(int policyId) {
        this.policyId = policyId;
    }

    public String getPolicyName() {
        return policyName;
    }

    public void setPolicyName(String policyName) {
        this.policyName = policyName;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getCurrPrice() {
        return currPrice;
    }

    public void setCurrPrice(double currPrice) {
        this.currPrice = currPrice;
    }

    public double getStopPrice() {
        return stopPrice;
    }

    public void setStopPrice(double stopPrice) {
        this.stopPrice = stopPrice;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPolicyKind() {
        return policyKind;
    }

    public void setPolicyKind(String policyKind) {
        this.policyKind = policyKind;
    }

    public String getPolicyMarket() {
        return policyMarket;
    }

    public void setPolicyMarket(String policyMarket) {
        this.policyMarket = policyMarket;
    }

    public String getPolicyTimeLevel() {
        return policyTimeLevel;
    }

    public void setPolicyTimeLevel(String policyTimeLevel) {
        this.policyTimeLevel = policyTimeLevel;
    }
}
