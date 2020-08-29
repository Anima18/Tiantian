package com.chris.tiantian.module.main;

import android.content.Context;
import android.util.Log;

import com.chris.tiantian.module.message.presenter.SyntheticMessagePresenter;
import com.chris.tiantian.module.message.presenter.SyntheticMessagePresenterImpl;
import com.chris.tiantian.util.LocationLog;
import com.fanjun.keeplive.config.KeepLiveService;

import java.util.Calendar;

/**
 * Created by jianjianhong on 20-1-14
 */
public class PolicyMonitorService implements KeepLiveService {
    private static final String TAG = "PolicyMonitorService";
    private static PolicyMonitorService sInstance;

    private SyntheticMessagePresenter signalPresenter;
    private MonitorThread monitorThread;
    private MarketTicksThread marketTicksThread;
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
        LocationLog.getInstance().i("PolicyMonitorService create");
        signalPresenter = new SyntheticMessagePresenterImpl(context);
    }

    @Override
    public void onWorking() {
        if(monitorThread != null) {
            monitorThread.interrupt();
            monitorThread = null;
            monitorFlag = false;

            marketTicksThread.interrupt();
            marketTicksThread = null;
        }
        LocationLog.getInstance().i("PolicyMonitorService onWorking");
        monitorThread = new MonitorThread();
        monitorFlag = true;
        monitorThread.start();

        marketTicksThread = new MarketTicksThread();
        marketTicksThread.start();
    }

    @Override
    public void onStop() {
        monitorThread.interrupt();
        monitorFlag = false;
        LocationLog.getInstance().i("PolicyMonitorService onStop");
    }

    private class MonitorThread extends Thread {
        public MonitorThread() {
            Log.i("PolicyMonitorService", "new MonitorThread");
        }


        @Override
        public void run() {
            while (monitorFlag) {
                try {
                    Thread.sleep(5000);
                    LocationLog.getInstance().i("PolicyMonitorService monitorPolicySignal");
                    signalPresenter.monitorPolicySignal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private class MarketTicksThread extends Thread {
        public MarketTicksThread() {
            Log.i("PolicyMonitorService", "new MarketTicksThread");
        }


        @Override
        public void run() {
            while (monitorFlag) {
                try {
                    Thread.sleep(10000);
                    //LocationLog.getInstance().i("PolicyMonitorService marketTicksThread");
                    signalPresenter.monitorMarketTicks();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
