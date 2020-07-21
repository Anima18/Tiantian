package com.chris.tiantian.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.anima.networkrequest.NetworkRequest;
import com.anima.networkrequest.callback.DataObjectCallback;
import com.anima.networkrequest.entity.RequestParam;
import com.chris.tiantian.entity.Constant;
import com.chris.tiantian.entity.UserData;
import com.chris.tiantian.entity.dataparser.ObjectDataParser;
import com.chris.tiantian.entity.dataparser.ObjectStatusDataParser;
import com.chris.tiantian.module.login.BindPhoneNumberActivity;
import com.chris.tiantian.module.login.LoginPresenterImpl;
import com.chris.tiantian.util.CommonUtil;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.jetbrains.annotations.NotNull;

/**
 * Created by jianjianhong on 20-5-14
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private static final String TAG = "WXEntryActivity";

    private IWXAPI api;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, Constant.APP_ID, false);
        try {
            Intent intent = getIntent();
            api.handleIntent(intent, this);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp resp) {
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                onAction(resp);
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                Toast.makeText(WXEntryActivity.this, "用户取消", Toast.LENGTH_SHORT).show();
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                Toast.makeText(WXEntryActivity.this, "用户拒绝授权", Toast.LENGTH_SHORT).show();
                break;
        }

    }

    private void onAction(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_SENDAUTH) {
            SendAuth.Resp authResp = (SendAuth.Resp)resp;
            final String code = authResp.code;
            String url = String.format("https://api.weixin.qq.com/sns/oauth2/access_token?" +
                            "appid=%s&secret=%s&code=%s&grant_type=authorization_code", Constant.APP_ID, Constant.APP_SECRET, code);
            new NetworkRequest<WXAccessData>(this)
                    .url(url)
                    .method(RequestParam.Method.GET)
                    .dataClass(WXAccessData.class)
                    .dataParser(new ObjectDataParser<WXAccessData>())
                    .getObject(new DataObjectCallback<WXAccessData>() {
                        @Override
                        public void onSuccess(@org.jetbrains.annotations.Nullable WXAccessData data) {
                            Log.i("WXEntryActivity", data.toString());
                            requestWXUser(data);
                        }

                        @Override
                        public void onFailure(@NotNull String s) {
                            Toast.makeText(WXEntryActivity.this, s, Toast.LENGTH_SHORT).show();
                            hide();
                        }
                    });

        }
    }

    private void requestWXUser(WXAccessData data) {
        String url = String.format("https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s", data.getAccess_token(), data.getOpenid());
        new NetworkRequest<WXUserInfo>(this)
                .url(url)
                .method(RequestParam.Method.GET)
                .dataClass(WXUserInfo.class)
                .dataParser(new ObjectDataParser<WXUserInfo>())
                .getObject(new DataObjectCallback<WXUserInfo>() {
                    @Override
                    public void onSuccess(@org.jetbrains.annotations.Nullable WXUserInfo data) {
                        hasUserBind(data);
                    }

                    @Override
                    public void onFailure(@NotNull String s) {
                        Toast.makeText(WXEntryActivity.this, s, Toast.LENGTH_SHORT).show();
                        hide();
                    }
                });
    }

    private void hasUserBind(WXUserInfo userInfo) {
        String url = String.format("%s/comment/apiv2/checkHasWxuserBinded/%s", CommonUtil.getBaseUrl(), userInfo.getOpenid());
        new NetworkRequest<UserData>(this)
                .url(url)
                .method(RequestParam.Method.GET)
                .dataClass(UserData.class)
                .dataParser(new ObjectStatusDataParser<UserData>())
                .getObject(new DataObjectCallback<UserData>() {
                    @Override
                    public void onSuccess(@org.jetbrains.annotations.Nullable UserData data) {
                        Log.i("dddddddd","ccccccccccccc");
                        if(data.getErr_code() == 0 && data.getUser() != null &&!TextUtils.isEmpty(data.getUser().getPhoneNumber())) {
                            new LoginPresenterImpl(null).saveUser(data.getUser());
                            Toast.makeText(WXEntryActivity.this, "微信登录成功", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(WXEntryActivity.this, "微信登录成功，请绑定手机号", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(WXEntryActivity.this, BindPhoneNumberActivity.class);
                            intent.putExtra("openid", userInfo.getOpenid());
                            intent.putExtra("nickname", userInfo.getNickname());
                            intent.putExtra("unionid", userInfo.getUnionid());
                            startActivity(intent);

                        }
                        hide();
                    }

                    @Override
                    public void onFailure(@NotNull String s) {
                        Toast.makeText(WXEntryActivity.this, s, Toast.LENGTH_SHORT).show();
                        hide();
                    }
                });
    }

    private void hide() {
        WXEntryActivity.this.finish();
    }

}
