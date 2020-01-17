package com.chris.tiantian;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

import com.anima.networkrequest.util.sharedprefs.UserInfoSharedPreferences;
import com.chris.tiantian.entity.Constant;

/**
 * Created by Chris on 2016/1/17.
 */
public class IndexActivity extends AppCompatActivity {
    boolean isLogined = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UserInfoSharedPreferences.Companion.getInstance(this).putBooleanValue(Constant.SP_LOADING_POLICY_SIGNAL_DATABASE, true);
        UserInfoSharedPreferences.Companion.getInstance(this).putBooleanValue(Constant.SP_LOADING_POLICY_DATABASE, true);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                getNextPageIntent();
            }
        }, 1500);
    }

    private void getNextPageIntent() {
        Intent intent = null;
        if(isLogined) {
            intent = new Intent(IndexActivity.this, MainActivity.class);
        }else {
            intent = new Intent(IndexActivity.this, LoginActivity.class);
        }
        if (null != getIntent().getData()) {
            intent.setData(getIntent().getData());
        }
        startActivity(intent);
        IndexActivity.this.finish();
    }
}
