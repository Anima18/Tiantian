package com.chris.tiantian.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.Nullable;

import com.anima.networkrequest.NetworkRequest;
import com.anima.networkrequest.callback.DataObjectCallback;
import com.anima.networkrequest.entity.RequestParam;
import com.chris.tiantian.entity.Constant;
import com.chris.tiantian.entity.dataparser.ObjectDataParser;
import com.chris.tiantian.entity.dataparser.StringDataParser;
import com.chris.tiantian.util.CommonUtil;
import com.chris.tiantian.util.jsonParse.UTJsonFactory;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

/**
 * Created by jianjianhong on 20-5-14
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private static final String TAG = "WXEntryActivity";

    private IWXAPI api;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, Constant.WX_APP_ID, false);
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
                            "appid=%s&secret=%s&code=%s&grant_type=authorization_code", Constant.WX_APP_ID, Constant.WX_APP_SECRET, code);
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
                        }
                    });

        }
        finish();
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
                        Log.i("WXEntryActivity", data.toString());
                        Toast.makeText(WXEntryActivity.this, data.toString(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(@NotNull String s) {
                        Toast.makeText(WXEntryActivity.this, s, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
