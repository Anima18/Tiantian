package com.chris.tiantian.entity;

/**
 * Created by jianjianhong on 20-8-23
 */
public class MarketTick {

    /**
     * best_ask : 391.6
     * best_bid : 391.59
     * instrument_id : ETH-USDT
     * product_id : ETH-USDT
     * last : 391.59
     * last_qty : 0.004351
     * ask : 391.6
     * best_ask_size : 3.84
     * bid : 391.59
     * best_bid_size : 70.534978
     * open_24h : 389.81
     * high_24h : 396.99
     * low_24h : 387.39
     * base_volume_24h : 146740.610909
     * timestamp : 2020-08-23T07:38:36.413Z
     * quote_volume_24h : 57463847.842263
     */

    private String best_ask;
    private String best_bid;
    private String instrument_id;
    private String product_id;
    private String last;
    private String last_qty;
    private String ask;
    private String best_ask_size;
    private String bid;
    private String best_bid_size;
    private String open_24h;
    private String high_24h;
    private String low_24h;
    private String base_volume_24h;
    private String timestamp;
    private String quote_volume_24h;

    public String getBest_ask() {
        return best_ask;
    }

    public void setBest_ask(String best_ask) {
        this.best_ask = best_ask;
    }

    public String getBest_bid() {
        return best_bid;
    }

    public void setBest_bid(String best_bid) {
        this.best_bid = best_bid;
    }

    public String getInstrument_id() {
        return instrument_id;
    }

    public void setInstrument_id(String instrument_id) {
        this.instrument_id = instrument_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getLast_qty() {
        return last_qty;
    }

    public void setLast_qty(String last_qty) {
        this.last_qty = last_qty;
    }

    public String getAsk() {
        return ask;
    }

    public void setAsk(String ask) {
        this.ask = ask;
    }

    public String getBest_ask_size() {
        return best_ask_size;
    }

    public void setBest_ask_size(String best_ask_size) {
        this.best_ask_size = best_ask_size;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getBest_bid_size() {
        return best_bid_size;
    }

    public void setBest_bid_size(String best_bid_size) {
        this.best_bid_size = best_bid_size;
    }

    public String getOpen_24h() {
        return open_24h;
    }

    public void setOpen_24h(String open_24h) {
        this.open_24h = open_24h;
    }

    public String getHigh_24h() {
        return high_24h;
    }

    public void setHigh_24h(String high_24h) {
        this.high_24h = high_24h;
    }

    public String getLow_24h() {
        return low_24h;
    }

    public void setLow_24h(String low_24h) {
        this.low_24h = low_24h;
    }

    public String getBase_volume_24h() {
        return base_volume_24h;
    }

    public void setBase_volume_24h(String base_volume_24h) {
        this.base_volume_24h = base_volume_24h;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getQuote_volume_24h() {
        return quote_volume_24h;
    }

    public void setQuote_volume_24h(String quote_volume_24h) {
        this.quote_volume_24h = quote_volume_24h;
    }
}
