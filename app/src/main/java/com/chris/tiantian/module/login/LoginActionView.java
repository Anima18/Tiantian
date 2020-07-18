package com.chris.tiantian.module.login;

import android.content.Context;

/**
 * Created by jianjianhong on 20-4-29
 */
public interface LoginActionView {
    Context getContext();
    void requestSMSCodeSuccess();
    void loginSuccess();
    void actionError(String message);
}
