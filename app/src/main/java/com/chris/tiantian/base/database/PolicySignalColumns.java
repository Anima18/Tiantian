package com.chris.tiantian.base.database;

import android.provider.BaseColumns;

/**
 * Created by jianjianhong on 19-10-11
 */
public interface PolicySignalColumns extends BaseColumns {
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
