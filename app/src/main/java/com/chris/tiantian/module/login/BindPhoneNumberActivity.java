package com.chris.tiantian.module.login;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.chris.tiantian.R;
import com.chris.tiantian.util.KeyboardUtil;
import com.chris.tiantian.util.StringUtil;
import com.chris.tiantian.util.TimerTextUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jianjianhong on 20-7-18
 */
public class BindPhoneNumberActivity extends AppCompatActivity implements LoginActionView {
    private EditText phoneNumberEt;
    private EditText codeEt;
    private TextView timerTextView;
    private Button sendBt;

    private TimerTextUtil timerTextUtil;
    private String unionid;
    private String openid;
    private String nickname;

    private LoginPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_phone_number);

        unionid = getIntent().getStringExtra("unionid");
        openid = getIntent().getStringExtra("openid");
        nickname = getIntent().getStringExtra("nickname");
        presenter = new LoginPresenterImpl(this);

        phoneNumberEt = findViewById(R.id.bindPhone_number);
        codeEt = findViewById(R.id.bindPhone_code);
        timerTextView = findViewById(R.id.bindPhone_requestCode);
        sendBt = findViewById(R.id.bindPhone_send);

        timerTextUtil = new TimerTextUtil(60000L, 1000L, timerTextView);
        timerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(timerTextUtil.isFinished()) {
                    String phone = phoneNumberEt.getText().toString();
                    if(!StringUtil.isPhone(phone)) {
                        actionError("请输入正确的手机号码！");
                        return;
                    }
                    timerTextUtil.start();
                    codeEt.requestFocus();
                    presenter.requestSMSCode(phone);
                }
            }
        });

        sendBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardUtil.hideKeyboard(BindPhoneNumberActivity.this);
                String phone = phoneNumberEt.getText().toString();
                String code = codeEt.getText().toString();
                if(!StringUtil.isPhone(phone)) {
                    actionError("请输入正确的手机号码！");
                    return;
                }
                if(TextUtils.isEmpty(code)) {
                    actionError("请输入验证码！");
                    return;
                }
                Map<String, String> dataMap = new HashMap<>();
                dataMap.put("unionid", unionid);
                dataMap.put("openid", openid);
                dataMap.put("nickname", nickname);
                dataMap.put("id", "0");
                dataMap.put("userid", code);
                dataMap.put("userphonenumber", phone);
                presenter.loginByWx(dataMap);
            }
        });
    }

    @Override
    public void actionError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void requestSMSCodeSuccess() {
        actionError("验证码已发送");
    }

    @Override
    public void loginSuccess() {
        actionError("绑定成功！");
        finish();
    }
}
