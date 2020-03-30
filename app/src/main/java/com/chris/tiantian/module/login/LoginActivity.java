package com.chris.tiantian.module.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.chris.tiantian.R;
import com.chris.tiantian.util.TimerTextUtil;
import com.chris.tiantian.util.VisibilityAnimation;
import com.ut.utuicomponents.uttoolbar.UTUiAndroidToolbar;


/**
 * Created by jianjianhong on 2019/4/3.
 */
public class LoginActivity extends AppCompatActivity {

    private View loginView;
    private View smsVerifyView;

    private UTUiAndroidToolbar smsToolbar;
    private TimerTextUtil timerTextUtil;
    private TextView timerTextView;

    private EditText code1Et;
    private EditText code2Et;
    private EditText code3Et;
    private EditText code4Et;
    private TextView areaCodeTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginView = findViewById(R.id.login_layout);
        smsVerifyView = findViewById(R.id.sms_layout);

        initLoginView();
        initSMSView();
    }

    private void initLoginView() {
        loginView.findViewById(R.id.weixin_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "敬请期待", Toast.LENGTH_SHORT).show();
            }
        });

        areaCodeTv = loginView.findViewById(R.id.loginView_area_code);
        areaCodeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(LoginActivity.this, AreaCodeActivity.class), 0);
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
            }
        });
        timerTextView = findViewById(R.id.timeTv);
        timerTextUtil = new TimerTextUtil(60000L, 1000L, timerTextView);
        timerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifySMS(v);
            }
        });
        onValidateCodeChanged();
    }

    public void verifySMS(View view) {
        if(timerTextUtil.isFinished()) {
            //todo 请求网络后台发送短信
            code1Et.setText("");
            code2Et.setText("");
            code3Et.setText("");
            code4Et.setText("");
            showSmsVerifyView();
        }else {
            showSmsVerifyView();
        }
    }

    private void showSmsVerifyView() {
        VisibilityAnimation.translTo(smsVerifyView, 0);
        if(timerTextUtil.isFinished()) {
            timerTextUtil.start();
        }
    }

    private void onValidateCodeChanged() {
        code1Et = smsVerifyView.findViewById(R.id.validateCode1);
        code2Et = smsVerifyView.findViewById(R.id.validateCode2);
        code3Et = smsVerifyView.findViewById(R.id.validateCode3);
        code4Et = smsVerifyView.findViewById(R.id.validateCode4);
        code1Et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (code1Et.getText().toString().length() == 1) {
                    code2Et.setFocusable(true);//设置获取焦点可以编辑
                    code2Et.setFocusableInTouchMode(true);
                    code2Et.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        code2Et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (code2Et.getText().toString().length() == 1) {
                    code3Et.setFocusable(true);//设置获取焦点可以编辑
                    code3Et.setFocusableInTouchMode(true);
                    code3Et.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        code3Et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (code3Et.getText().toString().length() == 1) {
                    code4Et.setFocusable(true);//设置获取焦点可以编辑
                    code4Et.setFocusableInTouchMode(true);
                    code4Et.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        code4Et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (code4Et.getText().toString().length() == 1) {
                    Toast.makeText(LoginActivity.this, "提交验证", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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
        }else {
            super.onBackPressed();
        }
    }

    private boolean isSMSVerify() {
        return smsVerifyView.getX() == 0;
    }
}
