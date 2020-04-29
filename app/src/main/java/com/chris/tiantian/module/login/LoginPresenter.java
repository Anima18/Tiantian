package com.chris.tiantian.module.login;

/**
 * Created by jianjianhong on 20-4-29
 */
public interface LoginPresenter {
    void requestSMSCode(String phoneNumber);
    void checkSMSCode(String phoneNumber, String smsCode);
}
