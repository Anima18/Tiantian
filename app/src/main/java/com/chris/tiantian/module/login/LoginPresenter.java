package com.chris.tiantian.module.login;

import java.util.Map;

/**
 * Created by jianjianhong on 20-4-29
 */
public interface LoginPresenter {
    void requestSMSCode(String phoneNumber);
    void loginByPhoneNumber(String phoneNumber, String smsCode);
    void loginByWx(Map<String, String> wxData, String openId);
}
