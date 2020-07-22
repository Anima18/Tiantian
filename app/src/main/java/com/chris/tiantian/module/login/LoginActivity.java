package com.chris.tiantian.module.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.anima.componentlib.toolbar.Toolbar;
import com.chris.tiantian.R;
import com.chris.tiantian.util.StringUtil;
import com.chris.tiantian.util.TimerTextUtil;
import com.chris.tiantian.util.VisibilityAnimation;
import com.chris.tiantian.util.WXPayUtil;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.lang.ref.WeakReference;

import static com.chris.tiantian.entity.Constant.APP_ID;


/**
 * Created by jianjianhong on 2019/4/3.
 */
public class LoginActivity extends AppCompatActivity implements LoginActionView {

    private View loginView;
    private View smsVerifyView;

    private Toolbar smsToolbar;
    private TimerTextUtil timerTextUtil;
    private TextView timerTextView;

    private TextView phoneNumberValueEt;
    private EditText codeEt;
    private TextView areaCodeTv;
    private TextView phoneEt;

    private LoginPresenter presenter;
    private IWXAPI iwxapi;

    private static WeakReference<LoginActivity> loginActivityRef;

    public static void finishActivity() {
        if (loginActivityRef != null && loginActivityRef.get() != null) {
            loginActivityRef.get().finish();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginActivityRef = new WeakReference<>(this);

        loginView = findViewById(R.id.login_layout);
        smsVerifyView = findViewById(R.id.sms_layout);

        presenter = new LoginPresenterImpl(this);
        iwxapi = WXAPIFactory.createWXAPI(this, APP_ID, true);
        initLoginView();
        initSMSView();
    }

    private void initLoginView() {
        loginView.findViewById(R.id.weixin_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(WXPayUtil.isWeixinAvilible(LoginActivity.this)) {
                    final SendAuth.Req req = new SendAuth.Req();
                    req.scope = "snsapi_userinfo";
                    req.state = "wechat_sdk_demo_test";
                    iwxapi.sendReq(req);
                }else {
                    Toast.makeText(LoginActivity.this, "你没有安装微信！", Toast.LENGTH_SHORT).show();
                }
            }
        });

        areaCodeTv = loginView.findViewById(R.id.loginView_area_code);
        phoneEt = loginView.findViewById(R.id.loginView_phone_input);
        /*areaCodeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(LoginActivity.this, AreaCodeActivity.class), 0);
            }
        });*/
        findViewById(R.id.btn_signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifySMS(v);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 0) {
            String areaCode = data.getStringExtra("area_code");
            areaCodeTv.setText(areaCode);
        }
    }

    private void initSMSView() {
        smsVerifyView.setX(getResources().getDisplayMetrics().widthPixels);

        smsToolbar = smsVerifyView.findViewById(R.id.activity_toolBar);
        smsToolbar.getNavigationView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VisibilityAnimation.translTo(smsVerifyView, smsVerifyView.getWidth());
                loginView.setVisibility(View.VISIBLE);
            }
        });
        timerTextView = findViewById(R.id.timeTv);
        timerTextUtil = new TimerTextUtil(60000L, 1000L, timerTextView);
        timerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(timerTextUtil.isFinished()) {
                    verifySMS(v);
                }

            }
        });
        phoneNumberValueEt = smsVerifyView.findViewById(R.id.phoneNumberValue);
        codeEt = smsVerifyView.findViewById(R.id.validateCode);
        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(v);
            }
        });
    }

    public void verifySMS(View view) {
        if(!timerTextUtil.isFinished()) {
            showSmsVerifyView();
            return;
        }

        String phone = phoneEt.getText().toString();
        if(!StringUtil.isPhone(phone)) {
            actionError("请输入正确的手机号码！");
            return;
        }

        if(timerTextUtil.isFinished()) {
            codeEt.setText("");
        }

        presenter.requestSMSCode(phone);
    }

    public void login(View view) {
        String smsCode = codeEt.getText().toString();
        if(TextUtils.isEmpty(smsCode)) {
            actionError("请输入验证码");
        }else {
            presenter.loginByPhoneNumber(phoneEt.getText().toString(), smsCode);
        }

    }

    private void showSmsVerifyView() {
        VisibilityAnimation.translTo(smsVerifyView, 0);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loginView.setVisibility(View.GONE);
            }
        }, 1000);

        phoneNumberValueEt.setText(String.format("短信已发送至 %s%s", areaCodeTv.getText(), phoneEt.getText()));
        if(timerTextUtil.isFinished()) {
            timerTextUtil.start();
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void requestSMSCodeSuccess() {
        actionMessage("验证码已发送");
        showSmsVerifyView();
    }

    @Override
    public void loginSuccess() {
        actionMessage("登录成功！");
        finish();
    }

    @Override
    public void actionError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void actionMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timerTextUtil.cancel();
    }

    @Override
    public void onBackPressed() {
        if(isSMSVerify()) {
            VisibilityAnimation.translTo(smsVerifyView, smsVerifyView.getWidth());
            loginView.setVisibility(View.VISIBLE);
        }else {
            super.onBackPressed();
        }
    }

    private boolean isSMSVerify() {
        return smsVerifyView.getX() == 0;
    }
}
