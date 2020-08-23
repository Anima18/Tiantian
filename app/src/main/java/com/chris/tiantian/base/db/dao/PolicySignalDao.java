package com.chris.tiantian.base.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.chris.tiantian.entity.PolicySignal;

import java.util.List;

/**
 * Created by jianjianhong on 20-4-27
 */
@Dao
public interface PolicySignalDao {

    @Query("select * from PolicySignal_Bean order by time desc")
    List<PolicySignal> query();

    @RawQuery
    List<PolicySignal> query(SupportSQLiteQuery query);

    @Query("select * from PolicySignal_Bean where id=:id")
    List<PolicySignal> findPolicySignal(int id);

    @Insert
    void insertList(List<PolicySignal> policySignals);

    @Insert
    void insert(PolicySignal policySignal);

    @Query("delete from PolicySignal_Bean")
    void clear();
}
