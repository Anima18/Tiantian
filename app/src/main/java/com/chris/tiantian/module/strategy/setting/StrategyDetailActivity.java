package com.chris.tiantian.module.strategy.setting;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.anima.componentlib.toolbar.Toolbar;
import com.chris.tiantian.R;

/**
 * Created by jianjianhong on 20-7-31
 */
public class StrategyDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strategy_detail);

        Toolbar toolbar = findViewById(R.id.activity_toolBar);
        toolbar.setTitle("详情");

    }
}
