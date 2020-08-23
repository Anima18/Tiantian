package com.chris.tiantian.module.signal.activity;

import com.anima.componentlib.paginglistview.PagingRecycleView;
import com.chris.tiantian.entity.PolicySignal;

import java.util.List;

/**
 * Created by jianjianhong on 20-1-14
 */
public interface PolicySignalActionView {
    void showLoading();
    void showError(String message);
    void showData(PagingRecycleView.LoadCallback loadCallback, List<PolicySignal> policySignalList);
}
