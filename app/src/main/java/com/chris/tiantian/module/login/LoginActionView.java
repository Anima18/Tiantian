package com.chris.tiantian.module.login;

import android.content.Context;

/**
 * Created by jianjianhong on 20-4-29
 */
public interface LoginActionView {
    Context getContext();
    void requestSMSCodeSuccess();
    void checkSMSCodeSuccess();
    void actionError(String message);
}
