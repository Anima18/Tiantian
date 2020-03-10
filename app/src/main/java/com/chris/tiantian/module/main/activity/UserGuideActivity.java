package com.chris.tiantian.module.main.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.chris.tiantian.R;
import com.chris.tiantian.util.BackgrounderSetting;

/**
 * Created by jianjianhong on 20-3-6
 */
public class UserGuideActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_guide);
    }

    public void autoStartupSetting(View view) {
        BackgrounderSetting.requestAutoRunning(this);
    }

    public void requestWhiteList(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(!BackgrounderSetting.isIgnoringBatteryOptimizations(this)) {
                BackgrounderSetting.requestIgnoreBatteryOptimizations(this);
            }else {
                Toast.makeText(this, "天机已经加入了白名单!", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "天机已经加入了白名单!", Toast.LENGTH_SHORT).show();
        }
    }
}
