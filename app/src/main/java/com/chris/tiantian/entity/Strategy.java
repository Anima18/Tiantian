package com.chris.tiantian.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jianjianhong on 20-7-29
 */
public class Strategy implements Parcelable {

    /**
     * market : ETH
     * marketIconUrl : https://static.aicoinstorge.com/coin/20180613/152885626571451.png?x-oss-process=image/resize,m_fill,h_108,w_108,limit_0
     * policyChoosed : null
     * signalChoosed : null
     */

    private String market;
    private String marketIconUrl;
    private String policyChoosed;
    private String signalChoosed;

    protected Strategy(Parcel in) {
        market = in.readString();
        marketIconUrl = in.readString();
        policyChoosed = in.readString();
        signalChoosed = in.readString();
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

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getMarketIconUrl() {
        return marketIconUrl;
    }

    public void setMarketIconUrl(String marketIconUrl) {
        this.marketIconUrl = marketIconUrl;
    }

    public String getPolicyChoosed() {
        return policyChoosed;
    }

    public void setPolicyChoosed(String policyChoosed) {
        this.policyChoosed = policyChoosed;
    }

    public String getSignalChoosed() {
        return signalChoosed;
    }

    public void setSignalChoosed(String signalChoosed) {
        this.signalChoosed = signalChoosed;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(market);
        dest.writeString(marketIconUrl);
        dest.writeString(policyChoosed);
        dest.writeString(signalChoosed);
    }
}
