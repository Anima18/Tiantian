package com.chris.tiantian.module.message.activityview;

import com.chris.tiantian.entity.PolicySignal;

import java.util.List;

/**
 * Created by jianjianhong on 20-1-14
 */
public interface SyntheticMessageActionView {
    void showLoading();
    void showError(String message);
    void showData(List<PolicySignal> policySignalList);
}
