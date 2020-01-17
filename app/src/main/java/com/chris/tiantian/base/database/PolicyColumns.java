package com.chris.tiantian.base.database;

import android.provider.BaseColumns;

/**
 * Created by jianjianhong on 19-10-11
 */
public interface PolicyColumns extends BaseColumns {
    String id = "id";
    String name = "name";
    String developerId = "developerId";
    String fileName = "fileName";
    String kind = "kind";
    String market = "market";
    String timeLevel = "timeLevel";
    String direction = "direction";
    String accuracy = "accuracy";
    String profit = "profit";
    String verifiedLevel = "verifiedLevel";
    String price = "price";
    String publishDate = "publishDate";
    String refineTimes = "refineTimes";
    String subscribeNumber = "subscribeNumber";
    String weight = "weight";
    String file = "file";

}
