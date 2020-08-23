package com.chris.tiantian.module.signal.presenter;

import com.anima.componentlib.paginglistview.PagingRecycleView;

/**
 * Created by jianjianhong on 20-1-14
 */
public interface PolicySignalPresenter {
    void requestDataByLocal(PagingRecycleView.LoadCallback loadCallback, int startNo, int endNo);
    void monitorPolicySignal();
    void monitorMarketTicks();
    void refreshPolicySignal(boolean isShowResultMessage);
}
