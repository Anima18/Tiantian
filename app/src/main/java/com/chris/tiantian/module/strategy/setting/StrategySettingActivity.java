package com.chris.tiantian.module.strategy.setting;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.anima.componentlib.toolbar.Toolbar;
import com.chris.tiantian.R;
import com.chris.tiantian.entity.Strategy;
import com.chris.tiantian.module.plaza.adapter.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

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
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strategy_setting);

        strategy = getIntent().getParcelableExtra(strategy_data);

        tabLayout = findViewById(R.id.strategy_setting_tabLayout);
        viewPager = findViewById(R.id.strategy_setting_viewPager);
        toolbar = findViewById(R.id.activity_toolBar);
        toolbar.setTitle(strategy.getName()+"定制");

        initTabView();
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
}
