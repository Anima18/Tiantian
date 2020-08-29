package com.chris.tiantian.module.strategy.setting;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.anima.componentlib.toolbar.Toolbar;
import com.anima.networkrequest.NetworkRequest;
import com.anima.networkrequest.RequestStream;
import com.anima.networkrequest.callback.DataObjectCallback;
import com.anima.networkrequest.entity.RequestParam;
import com.anima.networkrequest.util.sharedprefs.ConfigSharedPreferences;
import com.chris.tiantian.R;
import com.chris.tiantian.entity.Constant;
import com.chris.tiantian.entity.Strategy;
import com.chris.tiantian.entity.StrategyDetailItem;
import com.chris.tiantian.entity.StrategyTimeLevelGroup;
import com.chris.tiantian.entity.dataparser.ListStatusDataParser;
import com.chris.tiantian.entity.dataparser.ObjectStatusDataParser;
import com.chris.tiantian.module.me.activity.SmsSettingActivity;
import com.chris.tiantian.util.CommonUtil;
import com.chris.tiantian.util.PreferencesUtil;
import com.chris.tiantian.util.UserUtil;
import com.chris.tiantian.util.ViewPagerAdapter;
import com.chris.tiantian.view.MultipleStatusView;
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
    private MultipleStatusView statusView;
    private SwipeRefreshLayout refreshLayout;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private Strategy strategy;
    private List<Fragment> fragmentList;
    private List<String> fragmentNameList;
    private StrategySettingFragment strategySettingFragment;

    private TextView smsSettingResult;
    private StrategyDetailItem oldSelectedItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strategy_setting);

        strategy = getIntent().getParcelableExtra(strategy_data);
        statusView = findViewById(R.id.strategy_setting_status_view);
        tabLayout = findViewById(R.id.strategy_setting_tabLayout);
        viewPager = findViewById(R.id.strategy_setting_viewPager);
        toolbar = findViewById(R.id.activity_toolBar);
        toolbar.setTitle(strategy.getMarket()+"定制");
        smsSettingResult = findViewById(R.id.sms_setting_result);
        refreshLayout = findViewById(R.id.view_swipe_refresh_layout);
        refreshLayout.setColorSchemeColors(getResources().getColor(android.R.color.holo_orange_light),
                getResources().getColor(android.R.color.holo_red_light),
                getResources().getColor(android.R.color.holo_blue_light),
                getResources().getColor(android.R.color.holo_green_light));

        findViewById(R.id.sms_setting_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StrategySettingActivity.this, SmsSettingActivity.class));
            }
        });

        if(UserUtil.getUser() == null) {
            statusView.showError("请先登录！");
        }else {
            initTabView();
            requestData();
            statusView.setOnRetryClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    statusView.showLoading();
                    requestData();
                }
            });

            refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    statusView.showLoading();
                    requestData();
                }
            });
        }

    }

    public void initTabView() {
        fragmentList = new ArrayList<>();
        fragmentNameList = new ArrayList<>();
        strategySettingFragment = new StrategySettingFragment();
        fragmentList.add(strategySettingFragment);
        fragmentList.add(new SignalSettingFragment());
        fragmentNameList.add("策略选择");
        fragmentNameList.add("信号选择");

        viewPager.setOffscreenPageLimit(4);
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), fragmentList, fragmentNameList));
        tabLayout.setupWithViewPager(viewPager);
    }

    private void requestData() {
        refreshLayout.setRefreshing(false);
        String smsUrl = String.format("%s/comment/apiv2/smsNotifyEnable/%s", CommonUtil.getBaseUrl(), UserUtil.getUserId());
        NetworkRequest smsRequest = new NetworkRequest<Boolean>(this)
                .url(smsUrl)
                .method(RequestParam.Method.GET)
                .dataClass(Boolean.class)
                .dataParser(new ObjectStatusDataParser<Boolean>())
                .create();

        String policyDetailUrl = String.format("%s/comment/apiv2/policyMarketCustom/%s/%s", CommonUtil.getBaseUrl(), UserUtil.getUserId(), strategy.getMarket());
        NetworkRequest policyDetailRequest = new NetworkRequest<StrategyDetailItem>(this)
                .url(policyDetailUrl)
                .method(RequestParam.Method.GET)
                .dataClass(StrategyDetailItem.class)
                .dataParser(new ListStatusDataParser<StrategyDetailItem>())
                .create();

        RequestStream.Companion.create(this).parallel(smsRequest, policyDetailRequest).collect(new RequestStream.OnCollectListener() {
            @Override
            public void onFailure(@NotNull String s) {
                statusView.showError(s);
            }

            @Override
            public void onSuccess(@NotNull List<?> list) {
                statusView.showContent();
                Boolean isSmsSetting = (Boolean) list.get(0);
                List<StrategyDetailItem> strategyDetailItems = (List<StrategyDetailItem>)list.get(1);
                showSmsSetting(isSmsSetting);
                strategySettingFragment.showSetting(strategyDetailItems);

                for(StrategyDetailItem item : strategyDetailItems) {
                    if(item.isChoosed()) {
                        oldSelectedItem = item;
                        break;
                    }
                }
            }
        });
    }

    private void showSmsSetting(Boolean aBoolean) {
        if(aBoolean) {
            smsSettingResult.setText("已设置");
        }else {
            smsSettingResult.setText("设置");
        }
        smsSettingResult.setSelected(aBoolean);
    }

    @Override
    public void onBackPressed() {
        if(!UserUtil.isLogin()) {
            super.onBackPressed();
            return;
        }
        StrategyTimeLevelGroup.TimeLevel newSelectedItem = strategySettingFragment.getSetting();
        if((oldSelectedItem == null && newSelectedItem == null) || (oldSelectedItem != null && newSelectedItem != null && oldSelectedItem.getId() == newSelectedItem.getId())) {
            super.onBackPressed();
        }else {
            AlertDialog.Builder normalDialog = new AlertDialog.Builder(this);
            normalDialog.setTitle("提示");
            normalDialog.setMessage("你修改了策略配置，是否保存？");
            normalDialog.setCancelable(false);
            normalDialog.setPositiveButton("是",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            saveSetting(newSelectedItem);
                        }
                    });
            normalDialog.setNegativeButton("否", null);
            normalDialog.show();
        }
    }

    private void saveSetting(StrategyTimeLevelGroup.TimeLevel newSelectedItem) {
        String oldId = oldSelectedItem == null  ? "0" :oldSelectedItem.getId()+"";
        String newId = newSelectedItem == null  ? "0" :newSelectedItem.getId()+"";
        String saveUrl = String.format("%s/comment/apiv2/policySubscribe/%s/%s/%s", CommonUtil.getBaseUrl(), UserUtil.getUserId(), oldId, newId);
         new NetworkRequest<String>(this)
                .url(saveUrl)
                .method(RequestParam.Method.GET)
                 .loadingMessage("正在保存中...")
                .dataClass(String.class)
                .dataParser(new ObjectStatusDataParser<String>())
                .getObject(new DataObjectCallback<String>() {
                    @Override
                    public void onSuccess(@org.jetbrains.annotations.Nullable String aBoolean) {
                        Toast.makeText(StrategySettingActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                        UserUtil.resetMessage();
                        StrategySettingActivity.this.finish();
                    }

                    @Override
                    public void onFailure(@NotNull String s) {
                        Toast.makeText(StrategySettingActivity.this, s, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
