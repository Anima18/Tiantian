package com.chris.tiantian.entity;

import android.provider.BaseColumns;

/**
 * Created by jianjianhong on 19-10-11
 */
public class PolicySignalTable {
    //策略提醒表
    public static final String STRATEGY_TABLE_NAME = "PolicySignal_Bean";


    /**
     * 创建策略提醒表的语句
     * */
    public static final String CREATE_STRATEGY_TABLE = "CREATE TABLE " + STRATEGY_TABLE_NAME + "("
            + PolicySignalColumns.id + " INTEGER PRIMARY KEY NOT NULL,"
            + PolicySignalColumns.policyId + " INTEGER NOT NULL,"
            + PolicySignalColumns.policyName + " TEXT,"
            + PolicySignalColumns.direction + " TEXT,"
            + PolicySignalColumns.description + " TEXT,"
            + PolicySignalColumns.currPrice + " REAL NOT NULL,"
            + PolicySignalColumns.stopPrice + " REAL NOT NULL,"
            + PolicySignalColumns.time + " TEXT,"
            + PolicySignalColumns.policyKind + " TEXT,"
            + PolicySignalColumns.policyMarket + " TEXT,"
            + PolicySignalColumns.marketIconUrl + " TEXT,"
            + PolicySignalColumns.policyTimeLevel + " TEXT,"
            + PolicySignalColumns.userId + " INTEGER NOT NULL,"
            + ")";

    interface PolicySignalColumns extends BaseColumns {
        String id = "id";
        String policyId = "policyId";
        String policyName = "policyName";
        String direction = "direction";
        String description = "description";
        String currPrice = "currPrice";
        String stopPrice = "stopPrice";
        String time = "time";
        String policyKind = "policyKind";
        String policyMarket = "policyMarket";
        String policyTimeLevel = "policyTimeLevel";
        String marketIconUrl = "marketIconUrl";
        String userId = "userId";
    }
}
