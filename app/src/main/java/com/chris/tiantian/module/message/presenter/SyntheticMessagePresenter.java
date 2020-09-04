package com.chris.tiantian.module.message.presenter;

/**
 * Created by jianjianhong on 20-1-14
 */
public interface SyntheticMessagePresenter {
    void requestStrategyDataByLocal();
    void monitorPolicySignal();
    void monitorMarketTicks();
    void refreshPolicySignal();
}
