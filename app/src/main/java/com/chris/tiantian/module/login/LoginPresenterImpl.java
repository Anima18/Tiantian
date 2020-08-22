package com.chris.tiantian.module.login;

import android.content.Context;
import android.text.TextUtils;

import com.anima.networkrequest.NetworkRequest;
import com.anima.networkrequest.callback.DataObjectCallback;
import com.anima.networkrequest.entity.RequestParam;
import com.anima.networkrequest.util.sharedprefs.ConfigSharedPreferences;
import com.anima.networkrequest.util.sharedprefs.UserInfoSharedPreferences;
import com.chris.tiantian.entity.Constant;
import com.chris.tiantian.entity.User;
import com.chris.tiantian.entity.UserData;
import com.chris.tiantian.entity.dataparser.ObjectDataParser;
import com.chris.tiantian.entity.dataparser.ObjectStatusDataParser;
import com.chris.tiantian.entity.dataparser.StringDataParser;
import com.chris.tiantian.util.CommonUtil;
import com.chris.tiantian.util.PreferencesUtil;
import com.chris.tiantian.util.jsonParse.UTJson;
import com.chris.tiantian.util.jsonParse.UTJsonFactory;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Created by jianjianhong on 20-4-29
 */
public class LoginPresenterImpl implements LoginPresenter {
    private LoginActionView actionView;
    private Context context;

    public LoginPresenterImpl(LoginActionView actionView) {
        this.actionView = actionView;
        if(actionView != null) {
            this.context = actionView.getContext();
        }
    }

    @Override
    public void requestSMSCode(String phoneNumber) {
        String url = String.format("%s/comment/apiv2/userGenSMSValidateCode/%s", CommonUtil.getBaseUrl(), phoneNumber);
        new NetworkRequest<String>(context)
                .url(url)
                .method(RequestParam.Method.GET)
                .loadingMessage("正在发送验证码...")
                .dataClass(String.class)
                .dataParser(new StringDataParser())
                .getObject(new DataObjectCallback<String>() {
                    @Override
                    public void onSuccess(@org.jetbrains.annotations.Nullable String s) {
                        actionView.requestSMSCodeSuccess();
                    }

                    @Override
                    public void onFailure(@NotNull String s) {
                        actionView.actionError(s);
                    }
                });
    }

    @Override
    public void loginByPhoneNumber(String phoneNumber, String smsCode) {
        String url = String.format("%s/comment/apiv2/checkSMSValidateCode/%s/%s", CommonUtil.getBaseUrl(), phoneNumber, smsCode);
        new NetworkRequest<User>(context)
                .url(url)
                .method(RequestParam.Method.GET)
                .loadingMessage("正在登录...")
                .dataClass(User.class)
                .dataParser(new ObjectDataParser<User>())
                .getObject(new DataObjectCallback<User>() {
                    @Override
                    public void onSuccess(@org.jetbrains.annotations.Nullable User user) {
                        if(user == null || "FAIL".equals(user.getMsg()) || "TIMEOUT".equals(user.getMsg())) {
                            actionView.actionError("验证码不正确");
                        }else {
                            saveUser(user);
                            actionView.loginSuccess();
                        }

                    }

                    @Override
                    public void onFailure(@NotNull String s) {
                        actionView.actionError(s);
                    }
                });
    }

    @Override
    public void loginByWx(Map<String, String> wxData,String openId) {
        String url = String.format("%s/comment/apiv2/wxuserBindPhonenumber", CommonUtil.getBaseUrl());
        new NetworkRequest<UserData>(context)
                .url(url)
                .method(RequestParam.Method.POST)
                .params(wxData)
                .loadingMessage("正在绑定...")
                .asJson(true)
                .dataClass(UserData.class)
                .dataParser(new ObjectStatusDataParser<UserData>())
                .getObject(new DataObjectCallback<UserData>() {
                    @Override
                    public void onSuccess(@org.jetbrains.annotations.Nullable UserData result) {
                        if(result.getErr_code() == 0) {
                            User user = result.getUser();
                            user.setOpenId(openId);
                            saveUser(result.getUser());
                            actionView.loginSuccess();
                        }else {
                            actionView.actionError(result.getErr_msg());
                        }

                    }

                    @Override
                    public void onFailure(@NotNull String s) {
                        actionView.actionError(s);
                    }
                });
    }

    public void saveUser(User user) {
        UTJson utJson = UTJsonFactory.createJson();
        String userData = utJson.toJson(user);
        int userId = user.getId();

        UserInfoSharedPreferences preferences = PreferencesUtil.getUserInfoPreference();
        preferences.putStringValue(Constant.SP_USER_DATA, userData);
        preferences.putIntValue(Constant.SP_USER_ID, userId);
        preferences.putStringValue(Constant.SP_TOKEN, user.getToken());
        if(!TextUtils.isEmpty(user.getOpenId())) {
            preferences.putStringValue(Constant.SP_OPENID, user.getOpenId());
        }

        PreferencesUtil.getConfigPreference().putBooleanValue(Constant.SP_STRATEGY_LOADED, true);
    }
}
