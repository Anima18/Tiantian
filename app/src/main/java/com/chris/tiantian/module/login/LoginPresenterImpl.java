package com.chris.tiantian.module.login;

import android.content.Context;

import androidx.annotation.NonNull;

import com.anima.networkrequest.DataObjectCallback;
import com.anima.networkrequest.NetworkRequest;
import com.anima.networkrequest.entity.RequestParam;
import com.anima.networkrequest.util.sharedprefs.UserInfoSharedPreferences;
import com.chris.tiantian.entity.Constant;
import com.chris.tiantian.entity.Policy;
import com.chris.tiantian.entity.User;
import com.chris.tiantian.entity.dataparser.ObjectDataParser;
import com.chris.tiantian.entity.dataparser.StringDataParser;
import com.chris.tiantian.util.CommonUtil;
import com.chris.tiantian.util.PreferencesUtil;
import com.chris.tiantian.util.jsonParse.UTJson;
import com.chris.tiantian.util.jsonParse.UTJsonFactory;

import org.jetbrains.annotations.NotNull;

/**
 * Created by jianjianhong on 20-4-29
 */
public class LoginPresenterImpl implements LoginPresenter {
    private LoginActionView actionView;
    private Context context;

    public LoginPresenterImpl(@NonNull LoginActionView actionView) {
        this.actionView = actionView;
        this.context = actionView.getContext();
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
    public void checkSMSCode(String phoneNumber, String smsCode) {
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
                        saveUser(user);
                        actionView.checkSMSCodeSuccess();
                    }

                    @Override
                    public void onFailure(@NotNull String s) {
                        actionView.actionError(s);
                    }
                });
    }

    private void saveUser(User user) {
        UTJson utJson = UTJsonFactory.createJson();
        String userData = utJson.toJson(user);
        int userId = user.getId();

        UserInfoSharedPreferences preferences = PreferencesUtil.getUserInfoPreference();
        preferences.putStringValue(Constant.SP_USER_DATA, userData);
        preferences.putIntValue(Constant.SP_USER_ID, userId);
        preferences.putStringValue(Constant.SP_TOKEN, user.getToken());
    }
}
