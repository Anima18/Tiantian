package com.chris.tiantian.base.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.chris.tiantian.base.db.dao.PolicySignalDao;
import com.chris.tiantian.entity.PolicySignal;

/**
 * Created by jianjianhong on 20-4-24
 */
@Database(entities = {PolicySignal.class}, version = 4)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PolicySignalDao policySignalDao();
}
