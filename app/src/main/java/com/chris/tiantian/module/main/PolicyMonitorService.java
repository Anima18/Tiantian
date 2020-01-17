package com.chris.tiantian.module.main;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.chris.tiantian.entity.Policy;
import com.chris.tiantian.entity.PolicySignal;
import com.chris.tiantian.module.main.presenter.PolicySignalPresenter;
import com.chris.tiantian.module.main.presenter.PolicySignalPresenterImpl;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by jianjianhong on 19-9-27
 */
public class PolicyMonitorService extends Service {

    private static final String TAG = "PolicyMonitorService";
    private PolicySignalPresenter signalPresenter;
    private MonitorThread monitorThread;
    private boolean monitorFlag = true;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate");

        signalPresenter = new PolicySignalPresenterImpl(this);
        monitorThread = new MonitorThread();
        monitorThread.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand");
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
        monitorFlag = false;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.i(TAG, "onLowMemory");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private class MonitorThread extends Thread {
        @Override
        public void run() {
            while (monitorFlag) {
                Log.i(TAG, "Monitor...");
                try {
                    Thread.sleep(10000);
                    EventBus.getDefault().post(new Policy());
                    signalPresenter.monitorPolicySignal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
