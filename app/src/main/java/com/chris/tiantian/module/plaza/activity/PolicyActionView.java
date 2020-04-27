package com.chris.tiantian.module.plaza.activity;

import com.chris.tiantian.entity.Policy;

import java.util.List;

/**
 * Created by jianjianhong on 20-1-14
 */
public interface PolicyActionView {
    void showLoading();
    void showError(String message);
    void showData(List<Policy> list);
}
