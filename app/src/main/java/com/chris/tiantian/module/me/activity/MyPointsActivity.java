package com.chris.tiantian.module.me.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anima.networkrequest.NetworkRequest;
import com.anima.networkrequest.callback.DataObjectCallback;
import com.anima.networkrequest.entity.RequestParam;
import com.chris.tiantian.R;
import com.chris.tiantian.entity.Constant;
import com.chris.tiantian.entity.Order;
import com.chris.tiantian.entity.PointData;
import com.chris.tiantian.entity.UserPoint;
import com.chris.tiantian.entity.dataparser.ObjectDataParser;
import com.chris.tiantian.entity.dataparser.ObjectStatusDataParser;
import com.chris.tiantian.util.CommonUtil;
import com.chris.tiantian.util.DensityUtil;
import com.chris.tiantian.util.DeviceUtil;
import com.chris.tiantian.util.PreferencesUtil;
import com.chris.tiantian.util.StringUtil;
import com.chris.tiantian.util.UIAdapter;
import com.chris.tiantian.util.UserUtil;
import com.chris.tiantian.util.WXPayUtil;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jianjianhong on 20-3-25
 */
public class MyPointsActivity extends Activity {
    private final int DEFAUlT_LOCKED_POINT = 3000;

    private TextView freedomPoint;
    private TextView lockedPoint;
    private RecyclerView freedomPointListView;
    private LinearLayout lockedPointListView;
    private ImageView lockedPointImage;
    private TextView lockedPointValue;

    private LinearLayout payView;
    private TextView totalView;

    private FreedomPointAdapter freedomAdapter;
    private List<PointData> pointDataList;
    private int buyFreedomPoint;
    private int buyLockedPoint;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_points);
        freedomPoint = findViewById(R.id.freedom_points);
        lockedPoint = findViewById(R.id.locked_points);
        freedomPointListView = findViewById(R.id.buy_freedom_point_listView);
        lockedPointListView = findViewById(R.id.buy_locked_point_listView);
        lockedPointImage = findViewById(R.id.locked_pointImage);
        lockedPointValue = findViewById(R.id.locked_pointValue);
        lockedPointValue.setText(DEFAUlT_LOCKED_POINT+"");
        payView = findViewById(R.id.pay_point);
        totalView = findViewById(R.id.pay_total);

        initView();

        findViewById(R.id.myPointsDetail_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyPointsActivity.this, MyPointDetailActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initView() {
        pointDataList = new ArrayList<>();
        pointDataList.add(new PointData(10, R.drawable.points));
        pointDataList.add(new PointData(50, R.drawable.points));
        pointDataList.add(new PointData(100, R.drawable.points));
        pointDataList.add(new PointData(200, R.drawable.points));
        pointDataList.add(new PointData(500, R.drawable.points));
        pointDataList.add(new PointData(1000, R.drawable.points));

        freedomPointListView.setNestedScrollingEnabled(false);
        freedomAdapter = new FreedomPointAdapter(this,pointDataList);
        freedomPointListView.setAdapter(freedomAdapter);
        freedomAdapter.setOnItemClickListener(new FreedomPointAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                for(PointData pointData : pointDataList) {
                    pointData.selected = false;
                }
                PointData selectedPoint = pointDataList.get(position);
                selectedPoint.selected = true;
                buyFreedomPoint = selectedPoint.point;
                buyLockedPoint = 0;
                freedomAdapter.notifyDataSetChanged();
                showPayButton();
            }
        });
        freedomPointListView.setLayoutManager(new GridLayoutManager(this, 3));
        freedomPointListView.addItemDecoration(new SpaceItemDecoration(DensityUtil.dpToPx(16)));

        lockedPointListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lockedPointListView.setSelected(true);
                lockedPointImage.setColorFilter(getResources().getColor(R.color.white));
                lockedPointValue.setTextColor(getResources().getColor(R.color.white));

                buyLockedPoint = DEFAUlT_LOCKED_POINT;
                buyFreedomPoint = 0;
                showPayButton();
            }
        });

        payView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pay(v);
            }
        });
    }

    private void showPayButton(){
        if(buyFreedomPoint == 0) {
            for(PointData pointData : pointDataList) {
                pointData.selected = false;
            }
            freedomAdapter.notifyDataSetChanged();
        }
        if(buyLockedPoint == 0) {
            lockedPointListView.setSelected(false);
            lockedPointImage.setColorFilter(getResources().getColor(R.color.secondary_text_dark_color));
            lockedPointValue.setTextColor(getResources().getColor(R.color.secondary_text_dark_color));
        }

        int total = buyFreedomPoint *2 + buyLockedPoint;
        payView.setVisibility(total>0 ? View.VISIBLE : View.GONE);
        totalView.setText(String.format("总计：%s元", total+""));
    }

    private void initData() {
        String url = String.format("%s/comment/apiv2/qryMembershipPoints/%s", CommonUtil.getBaseUrl(), UserUtil.getUserId());
        new NetworkRequest<UserPoint>(this)
                .url(url)
                .method(RequestParam.Method.GET)
                .dataClass(UserPoint.class)
                .dataParser(new ObjectDataParser<UserPoint>())
                .getObject(new DataObjectCallback<UserPoint>() {
                    @Override
                    public void onSuccess(@org.jetbrains.annotations.Nullable UserPoint userPoint) {
                        if(userPoint != null) {
                            freedomPoint.setText(userPoint.getFreedomPoints()+"");
                            lockedPoint.setText(userPoint.getLockedPoints()+"");
                        }
                    }

                    @Override
                    public void onFailure(@NotNull String s) {
                        Toast.makeText(MyPointsActivity.this, "获取会员积分失败！", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void pay(View view) {
        String openId = PreferencesUtil.getUserInfoPreference().getStringValue(Constant.SP_OPENID, "");
        if(TextUtils.isEmpty(openId)) {
            Toast.makeText(MyPointsActivity.this, "你还没有绑定微信，请登出用微信登录！", Toast.LENGTH_SHORT).show();
            return;
        }

        String ip = DeviceUtil.getIPAddress();
        String attach = "";
        if(buyFreedomPoint != 0) {
            attach = String.format("消费积分#%d#%d#%d", Constant.FREEDOM_POINT_PRICE, buyFreedomPoint, Constant.FREEDOM_POINT_PRICE*buyFreedomPoint);
        }
        if(buyLockedPoint != 0) {
            attach = String.format("定投积分#%d#%d#%d", Constant.LOCKED_POINT_PRICE, buyLockedPoint, Constant.LOCKED_POINT_PRICE*buyLockedPoint);
        }
        if(TextUtils.isEmpty(attach)) {
            return;
        }
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("body", "天机APP-购买测试");
        paramMap.put("attach", attach);
        paramMap.put("total_fee", "1");
        paramMap.put("spbill_create_ip", ip);
        paramMap.put("trade_type", "APP");
        paramMap.put("userId", UserUtil.getUserId()+"");
        paramMap.put("openId", openId);

        String url = String.format("%s/comment/apiv2/wxUnifiedOrder", CommonUtil.getBaseUrl());
        new NetworkRequest<Order>(this)
                .url(url)
                .method(RequestParam.Method.POST)
                .params(paramMap)
                .loadingMessage("请求订单中...")
                .asJson(true)
                .dataClass(Order.class)
                .dataParser(new ObjectStatusDataParser<Order>())
                .getObject(new DataObjectCallback<Order>() {
                    @Override
                    public void onSuccess(@org.jetbrains.annotations.Nullable Order order) {
                        if("SUCCESS".equals(order.getReturn_code())) {
                            if("SUCCESS".equals(order.getResult_code())) {
                                requestWxPlayPage(order);
                            }else {
                                showMessage(order.getErr_code_des());
                            }
                        }else {
                            showMessage(order.getReturn_msg());
                        }
                    }

                    @Override
                    public void onFailure(@NotNull String s) {
                        showMessage(s);
                    }
                });
    }

    private void requestWxPlayPage(Order order) {
        try {
            String nonceStr = StringUtil.createNonceStr();
            String timestamp = StringUtil.createTimestamp();
            Map<String, String> params = new HashMap<>();
            params.put("appid", Constant.APP_ID);
            params.put("partnerid", Constant.PARTNER_ID);
            params.put("prepayid", order.getPrepay_id());
            params.put("package", "Sign=WXPay");
            params.put("noncestr", nonceStr);
            params.put("timestamp", timestamp);
            String sign = WXPayUtil.generateSignature(params, Constant.wx_pay_key);

            IWXAPI api = WXAPIFactory.createWXAPI(this, Constant.APP_ID, true);
            PayReq request = new PayReq();
            request.appId = Constant.APP_ID;
            request.partnerId = Constant.PARTNER_ID;
            request.prepayId= order.getPrepay_id();
            request.packageValue = "Sign=WXPay";
            request.nonceStr= nonceStr;
            request.timeStamp= timestamp;
            request.sign = sign;
            api.sendReq(request);
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
