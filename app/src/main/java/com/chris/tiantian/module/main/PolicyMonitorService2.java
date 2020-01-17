package com.chris.tiantian.module.main;

import android.content.Context;
import android.util.Log;

import com.chris.tiantian.module.main.presenter.PolicyPresenter;
import com.chris.tiantian.module.main.presenter.PolicyPresenterImpl;
import com.chris.tiantian.module.main.presenter.PolicySignalPresenter;
import com.chris.tiantian.module.main.presenter.PolicySignalPresenterImpl;
import com.fanjun.keeplive.config.KeepLiveService;

import java.util.Calendar;

/**
 * Created by jianjianhong on 20-1-14
 */
public class PolicyMonitorService2 implements KeepLiveService {
    private static final String TAG = "PolicyMonitorService";
    private static PolicyMonitorService2 sInstance;

    private PolicyPresenter policyPresenter;
    private PolicySignalPresenter signalPresenter;
    private MonitorThread monitorThread;
    private boolean monitorFlag = true;

    public static PolicyMonitorService2 getInstance(Context context) {
        synchronized (PolicyMonitorService2.class) {
            if (sInstance == null) {
                synchronized (PolicyMonitorService2.class) {
                    Calendar calendar = Calendar.getInstance();
                    sInstance = new PolicyMonitorService2(context);
                }

            }
        }

        return sInstance;
    }

    private PolicyMonitorService2(Context context){
        signalPresenter = new PolicySignalPresenterImpl(context);
        policyPresenter = new PolicyPresenterImpl(context);
    }

    @Override
    public void onWorking() {
        if(monitorThread != null) {
            monitorThread.interrupt();
            monitorThread = null;
            monitorFlag = false;
        }
        monitorThread = new MonitorThread();
        monitorFlag = true;
        monitorThread.start();
    }

    @Override
    public void onStop() {
        monitorThread.interrupt();
        monitorFlag = false;
    }

    private class MonitorThread extends Thread {
        @Override
        public void run() {
            while (monitorFlag) {
                Log.i(TAG, "Monitor...");
                try {
                    Thread.sleep(15000);
                    policyPresenter.monitorPolicy();
                    signalPresenter.monitorPolicySignal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
