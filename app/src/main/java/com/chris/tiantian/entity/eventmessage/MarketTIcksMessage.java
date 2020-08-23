package com.chris.tiantian.entity.eventmessage;

import com.chris.tiantian.entity.MarketTick;

import java.util.List;

/**
 * Created by jianjianhong on 20-8-23
 */
public class MarketTIcksMessage {
    private List<? extends MarketTick> marketTicks;

    public MarketTIcksMessage(List<? extends MarketTick> marketTicks) {
        this.marketTicks = marketTicks;
    }

    public List<? extends MarketTick> getMarketTicks() {
        return marketTicks;
    }

    public void setMarketTicks(List<? extends MarketTick> marketTicks) {
        this.marketTicks = marketTicks;
    }
}
