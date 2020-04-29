package com.chris.tiantian.entity;

/**
 * Created by jianjianhong on 20-1-9
 */
public interface Constant {

    String SP_USER_DATA = "SP_USER_DATA";
    String SP_USER_ID = "SP_USER_ID";
    String SP_TOKEN = "SP_TOKEN";

    //当前策略ID
    String SP_CURRENT_POLICY = "SP_CURRENT_POLICY";

    String SP_LASTTIME_POLICY_SIGNAL_NETWORK = "SP_LASTTIME_POLICY_SIGNAL_NETWORK";
    //刷新本地数据标志
    String SP_LOADING_POLICY_SIGNAL_DATABASE = "SP_LOADING_POLICY_SIGNAL_DATABASE";


    String SP_LASTTIME_POLICY_NETWORK = "SP_LASTTIME_POLICY_NETWORK";
    String SP_LOADING_POLICY_DATABASE = "SP_LOADING_POLICY_DATABASE";

    //网络请求时间戳格式
    String DATA_TIME_FORMAT = "yyyy.MM.dd HH:mm:ss";

    //是否已经提示自动启动设置
    String SHOW_AUTO_STARTUP_MAKER = "SHOW_AUTO_STARTUP_MAKER";
}
