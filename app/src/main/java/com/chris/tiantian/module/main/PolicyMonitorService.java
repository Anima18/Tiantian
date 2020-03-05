package com.chris.tiantian.module.main;

import android.content.Context;

import com.chris.tiantian.module.main.presenter.PolicyPresenter;
import com.chris.tiantian.module.main.presenter.PolicyPresenterImpl;
import com.chris.tiantian.module.main.presenter.PolicySignalPresenter;
import com.chris.tiantian.module.main.presenter.PolicySignalPresenterImpl;
import com.fanjun.keeplive.config.KeepLiveService;

import java.util.Calendar;

/**
 * Created by jianjianhong on 20-1-14
 */
public class PolicyMonitorService implements KeepLiveService {
    private static final String TAG = "PolicyMonitorService";
    private static PolicyMonitorService sInstance;

    private PolicyPresenter policyPresenter;
    private PolicySignalPresenter signalPresenter;
    private MonitorThread monitorThread;
    private boolean monitorFlag = true;

    public static PolicyMonitorService getInstance(Context context) {
        synchronized (PolicyMonitorService.class) {
            if (sInstance == null) {
                synchronized (PolicyMonitorService.class) {
                    Calendar calendar = Calendar.getInstance();
                    sInstance = new PolicyMonitorService(context);
                }

            }
        }

        return sInstance;
    }

    private PolicyMonitorService(Context context){
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
               // Log.i(TAG, "Monitor...");
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
