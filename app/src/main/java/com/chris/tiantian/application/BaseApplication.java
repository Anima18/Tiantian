package com.chris.tiantian.application;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.anima.networkrequest.util.sharedprefs.UserInfoSharedPreferences;
import com.chris.tiantian.R;
import com.chris.tiantian.base.db.AppDatabase;
import com.chris.tiantian.entity.Constant;
import com.chris.tiantian.entity.PolicySignalTable;
import com.chris.tiantian.module.main.PolicyMonitorService;
import com.chris.tiantian.util.PreferencesUtil;
import com.fanjun.keeplive.KeepLive;
import com.fanjun.keeplive.config.ForegroundNotification;
import com.fanjun.keeplive.config.ForegroundNotificationClickListener;

/**
 * @author chenjieliang
 */
public class BaseApplication extends Application {
    private static final String TAG = "BaseApplication";
    public static Context mContext;

    public static AppDatabase appDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        appDatabase = Room.databaseBuilder(this, AppDatabase.class, "tiantian.db")
                .allowMainThreadQueries()
                //.fallbackToDestructiveMigrationFrom(1)
                .addMigrations(MIGRATION_3_4)
                //.fallbackToDestructiveMigration()
                .build();
        startForegroundKeepLive();
    }

    public AppDatabase getAppDatabase() {
        return appDatabase;
    }

    private void startForegroundKeepLive() {
        Log.i("PolicyMonitorService", "startForegroundKeepLive");
        //定义前台服务的默认样式。即标题、描述和图标
        ForegroundNotification foregroundNotification = new ForegroundNotification("数据同步服务","已开启", R.mipmap.ic_app,
                //定义前台服务的通知点击事件
                new ForegroundNotificationClickListener() {

                    @Override
                    public void foregroundNotificationClick(Context context, Intent intent) {
                    }
                });

        PolicyMonitorService policyMonitorService = PolicyMonitorService.getInstance(this);
        //启动保活服务
        KeepLive.startWork(this, KeepLive.RunMode.ROGUE, foregroundNotification,
                //你需要保活的服务，如socket连接、定时任务等，建议不用匿名内部类的方式在这里写
                policyMonitorService);
    }

    /**
     * 删除以前版本的表
     */
    public static final String DROP_TABLE_POLICY = "DROP TABLE IF EXISTS PolicyAction";
    public static final String DROP_TABLE_POLICYACTION = "DROP TABLE IF EXISTS PolicySignalAction";
    static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            Log.i("DBManager", "MIGRATION_3_4");
            database.execSQL(DROP_TABLE_POLICY);
            database.execSQL(DROP_TABLE_POLICYACTION);
            database.execSQL(PolicySignalTable.CREATE_TABLE);
            UserInfoSharedPreferences preferences = PreferencesUtil.getUserInfoPreference();
            preferences.putBooleanValue(Constant.SP_LOADING_POLICY_SIGNAL_DATABASE, false);
            preferences.putIntValue(Constant.SP_CURRENT_POLICY, -1);
        }

    };

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            Log.i("DBManager", "MIGRATION_1_2");
        }
    };

    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            Log.i("DBManager", "MIGRATION_2_3");
        }
    };

}
