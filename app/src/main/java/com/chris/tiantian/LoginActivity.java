package com.chris.tiantian;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


/**
 * Created by jianjianhong on 2019/4/3.
 */
public class LoginActivity extends AppCompatActivity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    public void toSMSVerification(View view) {
        startActivity(new Intent(this, SMSverificationActivity.class));
    }
}
