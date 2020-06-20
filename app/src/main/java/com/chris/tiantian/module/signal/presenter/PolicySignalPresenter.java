package com.chris.tiantian.module.signal.presenter;

/**
 * Created by jianjianhong on 20-1-14
 */
public interface PolicySignalPresenter {
    void requestDataByLocal();
    void requestDataByNetwork();
    void monitorPolicySignal();
    void refreshPolicySignal(boolean isShowResultMessage);
}
