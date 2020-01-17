package com.chris.tiantian.base.database;

import android.content.Context;


public class DBManager {
    /**
     * 单例对象本身
     * */
    private static volatile DBManager dbManager;

    private PolicySignalManager policySignalManager;
    private PolicyManager policyManager;

    /**
     * 单例暴露给外部调用
     * */
    public static DBManager getInstance(Context context) {
        synchronized (DBManager.class) {
            if(dbManager == null) {
                dbManager = new DBManager(context);
            }
        }
        return dbManager;
    }

    private DBManager(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        policySignalManager = new PolicySignalManager(dbHelper);
        policyManager = new PolicyManager(dbHelper);
    }

    public PolicySignalManager getPolicySignalManager() {
        return this.policySignalManager;
    }

    public PolicyManager getPolicyManager() {
        return policyManager;
    }
}
