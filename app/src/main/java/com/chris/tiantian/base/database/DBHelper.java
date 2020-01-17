package com.chris.tiantian.base.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {
    /**
     * 数据库的名字
     * */
    private static final String DATABASE_NAME = "tiantian.db";
    /**
     * 版本号
     * */
    private static int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * 用户首次安装应用程序，会调用该方法
     * */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(PolicySignalAction.CREATE_TABLE);
        db.execSQL(PolicyAction.CREATE_TABLE);
    }

    /**
     * 版本号发生变化，会调用该方法
     * */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //通过版本号来决定如何升级数据库
    }
}
