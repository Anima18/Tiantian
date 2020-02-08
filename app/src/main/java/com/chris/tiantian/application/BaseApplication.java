package com.chris.tiantian.application;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.chris.tiantian.R;
import com.chris.tiantian.module.main.PolicyMonitorService;
import com.fanjun.keeplive.KeepLive;
import com.fanjun.keeplive.config.ForegroundNotification;
import com.fanjun.keeplive.config.ForegroundNotificationClickListener;

/**
 * @author chenjieliang
 */
public class BaseApplication extends Application {
    private static final String TAG = "BaseApplication";
    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        startForegroundKeepLive();
    }


    private void startForegroundKeepLive() {
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
        KeepLive.startWork(this, KeepLive.RunMode.ENERGY, foregroundNotification,
                //你需要保活的服务，如socket连接、定时任务等，建议不用匿名内部类的方式在这里写
                policyMonitorService);
    }
}
