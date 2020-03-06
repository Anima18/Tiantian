package com.chris.tiantian.module.main.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.chris.tiantian.R;
import com.chris.tiantian.util.AutoStartupSetting;

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
        AutoStartupSetting.openSettingPage(this);
    }
}
