package com.chris.tiantian.base.database;

import android.database.sqlite.SQLiteOpenHelper;

import com.chris.tiantian.entity.Policy;

import java.util.List;

/**
 * Created by jianjianhong on 19-10-11
 */
public class PolicyManager {
    private SQLiteOpenHelper dbHelper;

    public PolicyManager(SQLiteOpenHelper dbHelper)
    {
        this.dbHelper = dbHelper;
    }

    public List<Policy> query()
    {
        return PolicyAction.query(dbHelper);
    }

    public void insert(Policy Policy){
        if(Policy == null) {
            return;
        }
        PolicyAction.insert(dbHelper, Policy);
    }

    public void insertList(List<Policy> Policys){
        if(Policys == null || Policys.size() == 0) {
            return;
        }
        for(Policy signal : Policys) {
            PolicyAction.insert(dbHelper, signal);
        }
    }

    public void updateList(List<Policy> Policys) {
        if(Policys == null || Policys.size() == 0) {
            return;
        }
        for(Policy signal : Policys) {
            PolicyAction.updateOrInsert(dbHelper, signal);
        }
    }

    public void clear()
    {
        PolicyAction.clear(dbHelper);
    }
}
