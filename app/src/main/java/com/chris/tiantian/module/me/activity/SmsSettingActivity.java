package com.chris.tiantian.module.me.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anima.networkrequest.NetworkRequest;
import com.anima.networkrequest.RequestStream;
import com.anima.networkrequest.callback.DataObjectCallback;
import com.anima.networkrequest.entity.RequestParam;
import com.chris.tiantian.R;
import com.chris.tiantian.entity.Advertise;
import com.chris.tiantian.entity.BuySmsResult;
import com.chris.tiantian.entity.Constant;
import com.chris.tiantian.entity.New;
import com.chris.tiantian.entity.Order;
import com.chris.tiantian.entity.PointData;
import com.chris.tiantian.entity.dataparser.ObjectStatusDataParser;
import com.chris.tiantian.module.strategy.setting.StrategySettingActivity;
import com.chris.tiantian.util.CommonUtil;
import com.chris.tiantian.util.DensityUtil;
import com.chris.tiantian.util.UserUtil;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jianjianhong on 20-8-9
 */
public class SmsSettingActivity extends AppCompatActivity {

    private TextView smsCountTextView;
    private SwitchCompat smsSettingSwitch;
    private RecyclerView recyclerView;
    private LinearLayout payView;
    private TextView totalView;

    private List<PointData> pointDataList;
    private FreedomPointAdapter freedomAdapter;
    private int bySmsCount = 0;

    private boolean loaded = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_setting);

        smsCountTextView = findViewById(R.id.sms_count_textView);
        smsSettingSwitch = findViewById(R.id.sms_setting_switch);
        recyclerView = findViewById(R.id.buy_sms_listView);
        payView = findViewById(R.id.pay_point);
        totalView = findViewById(R.id.pay_total);

        initView();
        initData();

        smsSettingSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(loaded) {
                    updateSmsSetting(isChecked);
                }
            }
        });

        payView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buySmsCount();
            }
        });
    }

    private void initView() {
        pointDataList = new ArrayList<>();
        pointDataList.add(new PointData(10));
        pointDataList.add(new PointData(50));
        pointDataList.add(new PointData(100));
        pointDataList.add(new PointData(200));
        pointDataList.add(new PointData(500));
        pointDataList.add(new PointData(1000));

        recyclerView.setNestedScrollingEnabled(false);
        freedomAdapter = new FreedomPointAdapter(this,pointDataList);
        recyclerView.setAdapter(freedomAdapter);
        freedomAdapter.setOnItemClickListener(new FreedomPointAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                for(PointData pointData : pointDataList) {
                    pointData.selected = false;
                }
                PointData selectedPoint = pointDataList.get(position);
                if(selectedPoint.point != bySmsCount) {
                    selectedPoint.selected = true;
                    bySmsCount = selectedPoint.point;
                }else {
                    bySmsCount = 0;
                }
                freedomAdapter.notifyDataSetChanged();
                showPayButton();
            }
        });
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.addItemDecoration(new SpaceItemDecoration(DensityUtil.dpToPx(16)));
    }

    private void initData() {
        String settingUrl = String.format("%s/comment/apiv2/smsNotifyEnable/%s", CommonUtil.getBaseUrl(), UserUtil.getUserId());
        NetworkRequest settingRequest = new NetworkRequest<Boolean>(this)
                .url(settingUrl)
                .method(RequestParam.Method.GET)
                .dataClass(Boolean.class)
                .dataParser(new ObjectStatusDataParser<Boolean>())
                .create();
        RequestStream.Companion.create(this).parallel(settingRequest).collect(new RequestStream.OnCollectListener() {
            @Override
            public void onFailure(@NotNull String s) {
                Toast.makeText(SmsSettingActivity.this, s, Toast.LENGTH_SHORT).show();
                loaded = true;
            }

            @Override
            public void onSuccess(@NotNull List<?> list) {
                Boolean setting = (Boolean) list.get(0);
                smsSettingSwitch.setChecked(setting);
                loaded = true;
            }
        });
    }

    private void showPayButton(){
        int total = (int) (bySmsCount * Constant.SMS_PRICE);
        payView.setVisibility(total>0 ? View.VISIBLE : View.GONE);
        totalView.setText(String.format("总计：%s天机积分", total+""));
    }

    private void updateSmsSetting(boolean setting) {
        //http://114.67.204.96:8080/comment/apiv2/changeSMSNotifyEnable/4/true
        String settingUrl = String.format("%s/comment/apiv2/changeSMSNotifyEnable/%s/%s", CommonUtil.getBaseUrl(), UserUtil.getUserId(), String.valueOf(setting));
        new NetworkRequest<String>(this)
                .url(settingUrl)
                .method(RequestParam.Method.GET)
                .loadingMessage("正在设置中...")
                .dataClass(String.class)
                .dataParser(new ObjectStatusDataParser<String>())
                .getObject(new DataObjectCallback<String>() {
                    @Override
                    public void onSuccess(@org.jetbrains.annotations.Nullable String s) {
                        Toast.makeText(SmsSettingActivity.this, "设置成功！", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(@NotNull String s) {
                        Toast.makeText(SmsSettingActivity.this, s, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void buySmsCount() {
        if(bySmsCount == 0) {
            return;
        }
        int total = (int) (bySmsCount * Constant.SMS_PRICE);
        String attach = String.format("短信通知#0.1#%d#%d", bySmsCount, total);
        String url = String.format("%s/comment/apiv2/consumePoint", CommonUtil.getBaseUrl());
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("body", "");
        paramMap.put("attach", attach);
        paramMap.put("totalFee", total+"");
        paramMap.put("userId", UserUtil.getUserId()+"");
        paramMap.put("id", "1");
        paramMap.put("name", "短信通知");

        new NetworkRequest<BuySmsResult>(this)
                .url(url)
                .method(RequestParam.Method.POST)
                .params(paramMap)
                .loadingMessage("正在购买中...")
                .asJson(true)
                .dataClass(BuySmsResult.class)
                .dataParser(new ObjectStatusDataParser<BuySmsResult>())
                .getObject(new DataObjectCallback<BuySmsResult>() {
                    @Override
                    public void onSuccess(@org.jetbrains.annotations.Nullable BuySmsResult result) {
                        if(0 == result.getErr_code()) {
                            showMessage("购买成功");
                            initData();
                        }else if(1000 == result.getErr_code()) {
                            showMessage("积分不足,购买失败,请充值！");
                        }else if(1001 == result.getErr_code()) {
                            showMessage(result.getErr_msg());
                        }else {
                            showMessage(result.getErr_msg());
                        }
                    }

                    @Override
                    public void onFailure(@NotNull String s) {
                        showMessage(s);
                    }
                });
    }

    private void showMessage(String message) {
        Toast.makeText(SmsSettingActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
