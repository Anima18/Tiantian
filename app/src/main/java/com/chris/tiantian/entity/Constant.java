package com.chris.tiantian.entity;

/**
 * Created by jianjianhong on 20-1-9
 */
public interface Constant {

    String APP_ID = "wx5b3645c2d251e349";
    String APP_SECRET = "4aaa89f771236e985ff99f072f0aefc0";
    String PARTNER_ID = "1592531701";
    String wx_pay_key= "wMHwrjyr4vzuRajr2X5hB1coGDQryFVm";

    int FREEDOM_POINT_PRICE = 2;
    int LOCKED_POINT_PRICE = 1;
    double SMS_PRICE = 0.1;


    /**
     * 用户登录信息
     */
    String SP_USER_DATA = "SP_USER_DATA";
    String SP_USER_ID = "SP_USER_ID";
    String SP_TOKEN = "SP_TOKEN";
    String SP_OPENID = "SP_OPENID";

    //策略列表是否要更新
    String SP_STRATEGY_LOADED = "SP_STRATEGY_LOADED";
    //获取上次更新时间
    String SP_MESSAGE_TIME_STAMP = "SP_LASTTIME_POLICY_SIGNAL_NETWORK";


    //网络请求时间戳格式
    String DATA_TIME_FORMAT = "yyyy.MM.dd HH:mm:ss";

    //是否已经提示自动启动设置
    String SHOW_AUTO_STARTUP_MAKER = "SHOW_AUTO_STARTUP_MAKER";
}
