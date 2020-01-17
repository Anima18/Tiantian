package com.chris.tiantian.base.database;

import android.database.sqlite.SQLiteOpenHelper;

import com.chris.tiantian.entity.PolicySignal;

import java.util.List;

/**
 * Created by jianjianhong on 19-10-11
 */
public class PolicySignalManager {
    private SQLiteOpenHelper dbHelper;

    public PolicySignalManager(SQLiteOpenHelper dbHelper)
    {
        this.dbHelper = dbHelper;
    }

    public List<PolicySignal> query()
    {
        return PolicySignalAction.query(dbHelper);
    }

    public void insert(PolicySignal policySignal){
        if(policySignal == null) {
            return;
        }
        PolicySignalAction.insert(dbHelper, policySignal);
    }

    public void insertList(List<PolicySignal> policySignals){
        if(policySignals == null || policySignals.size() == 0) {
            return;
        }
        for(PolicySignal signal : policySignals) {
            PolicySignalAction.insert(dbHelper, signal);
        }
    }

    public void clear()
    {
        PolicySignalAction.clear(dbHelper);
    }
}
