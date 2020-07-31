package com.chris.tiantian.module.message;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.chris.tiantian.R;
import com.chris.tiantian.entity.Strategy;
import com.chris.tiantian.module.message.fragment.AllMessageFragment;
import com.chris.tiantian.module.message.fragment.SignalMessageFragment;
import com.chris.tiantian.module.message.fragment.StrategyMessageFragment;
import com.chris.tiantian.module.plaza.adapter.ViewPagerAdapter;
import com.chris.tiantian.module.strategy.setting.SignalSettingFragment;
import com.chris.tiantian.module.strategy.setting.StrategySettingActivity;
import com.chris.tiantian.module.strategy.setting.StrategySettingFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import static com.chris.tiantian.module.strategy.setting.StrategySettingActivity.strategy_data;

/**
 * Created by jianjianhong on 20-7-28
 */
public class MessageFragment extends Fragment {
    private View rootView;

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private List<Fragment> fragmentList;
    private List<String> fragmentNameList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_message, container, false);

            tabLayout = rootView.findViewById(R.id.message_tabLayout);
            viewPager = rootView.findViewById(R.id.message_viewPager);

            initTabView();
        }
        return rootView;
    }

    public void initTabView() {
        fragmentList = new ArrayList<>();
        fragmentNameList = new ArrayList<>();
        fragmentList.add(new AllMessageFragment());
        fragmentList.add(new SignalMessageFragment());
        fragmentList.add(new StrategyMessageFragment());
        fragmentNameList.add("综合提醒");
        fragmentNameList.add("信号提醒");
        fragmentNameList.add("策略提醒");

        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(new ViewPagerAdapter(getChildFragmentManager(), fragmentList, fragmentNameList));
        tabLayout.setupWithViewPager(viewPager);


    }

}
