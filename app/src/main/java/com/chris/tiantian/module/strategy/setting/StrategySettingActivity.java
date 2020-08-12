package com.chris.tiantian.module.strategy.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.anima.componentlib.toolbar.Toolbar;
import com.anima.networkrequest.NetworkRequest;
import com.anima.networkrequest.callback.DataListCallback;
import com.anima.networkrequest.callback.DataObjectCallback;
import com.anima.networkrequest.entity.RequestParam;
import com.chris.tiantian.R;
import com.chris.tiantian.entity.Strategy;
import com.chris.tiantian.entity.UserPointLog;
import com.chris.tiantian.entity.dataparser.ListStatusDataParser;
import com.chris.tiantian.entity.dataparser.ObjectStatusDataParser;
import com.chris.tiantian.module.me.activity.SmsSettingActivity;
import com.chris.tiantian.module.plaza.adapter.ViewPagerAdapter;
import com.chris.tiantian.util.CommonUtil;
import com.chris.tiantian.util.UserUtil;
import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jianjianhong on 20-7-29
 */
public class StrategySettingActivity extends AppCompatActivity {
    public static final String strategy_data = "strategyData";

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private Strategy strategy;
    private List<Fragment> fragmentList;
    private List<String> fragmentNameList;

    private TextView smsSettingResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strategy_setting);

        strategy = getIntent().getParcelableExtra(strategy_data);

        tabLayout = findViewById(R.id.strategy_setting_tabLayout);
        viewPager = findViewById(R.id.strategy_setting_viewPager);
        toolbar = findViewById(R.id.activity_toolBar);
        toolbar.setTitle(strategy.getName()+"定制");



        findViewById(R.id.sms_setting_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(UserUtil.getUser() == null) {
                    Toast.makeText(StrategySettingActivity.this, "请先登录！", Toast.LENGTH_SHORT).show();
                }else {
                    startActivity(new Intent(StrategySettingActivity.this, SmsSettingActivity.class));
                }

            }
        });
        smsSettingResult = findViewById(R.id.sms_setting_result);
        initTabView();
        requestData();
    }

    public void initTabView() {
        fragmentList = new ArrayList<>();
        fragmentNameList = new ArrayList<>();
        fragmentList.add(new StrategySettingFragment());
        fragmentList.add(new SignalSettingFragment());
        fragmentNameList.add("策略选择");
        fragmentNameList.add("信号选择");

        viewPager.setOffscreenPageLimit(4);
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), fragmentList, fragmentNameList));
        tabLayout.setupWithViewPager(viewPager);


    }

    private void requestData() {
        //http://114.67.204.96:8080/comment/apiv2/smsNotifyEnable/4
        String url = String.format("%s/comment/apiv2/smsNotifyEnable/%s", CommonUtil.getBaseUrl(), UserUtil.getUserId());
        new NetworkRequest<Boolean>(this)
                .url(url)
                .method(RequestParam.Method.GET)
                .dataClass(Boolean.class)
                .dataParser(new ObjectStatusDataParser<Boolean>())
                .getObject(new DataObjectCallback<Boolean>() {
                    @Override
                    public void onSuccess(@org.jetbrains.annotations.Nullable Boolean aBoolean) {
                        if(aBoolean) {
                            smsSettingResult.setText("已设置");
                        }else {
                            smsSettingResult.setText("设置");
                        }
                        smsSettingResult.setSelected(aBoolean);
                    }

                    @Override
                    public void onFailure(@NotNull String s) {
                        Toast.makeText(StrategySettingActivity.this, s, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
