package com.chris.tiantian.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.chris.tiantian.R;
import com.chris.tiantian.entity.Constant;
import com.chris.tiantian.util.CommonUtil;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
	
	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
	
    private IWXAPI api;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
        
    	api = WXAPIFactory.createWXAPI(this, Constant.APP_ID);
        api.handleIntent(getIntent(), this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
		//Log.d(TAG, "onPayFinish, errCode = " + resp.errCode);

		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			if(0 == resp.errCode) {
				Toast.makeText(CommonUtil.getApplicationContext(), "支付成功", Toast.LENGTH_SHORT).show();
			}else if(-1 == resp.errCode) {
				Toast.makeText(CommonUtil.getApplicationContext(), "支付失败，"+resp.errStr, Toast.LENGTH_SHORT).show();
			}else if(-2 == resp.errCode) {
				Toast.makeText(CommonUtil.getApplicationContext(), "你取消了支付", Toast.LENGTH_SHORT).show();
			}
			WXPayEntryActivity.this.finish();
		}
	}
}