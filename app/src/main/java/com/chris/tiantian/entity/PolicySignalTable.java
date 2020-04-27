package com.chris.tiantian.entity;

import android.provider.BaseColumns;

/**
 * Created by jianjianhong on 19-10-11
 */
public class PolicySignalTable {
    public static final String TABLE_NAME = "PolicySignal_Bean";


    /**
     * 创建表的语句
     * */
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
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
            + PolicySignalColumns.policyTimeLevel + " TEXT"
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

    }
}
