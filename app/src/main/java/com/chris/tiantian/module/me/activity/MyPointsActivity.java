package com.chris.tiantian.module.me.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.anima.networkrequest.DataObjectCallback;
import com.anima.networkrequest.NetworkRequest;
import com.anima.networkrequest.entity.RequestParam;
import com.chris.tiantian.R;
import com.chris.tiantian.entity.User;
import com.chris.tiantian.entity.UserPoint;
import com.chris.tiantian.entity.dataparser.ObjectDataParser;
import com.chris.tiantian.util.CommonUtil;
import com.chris.tiantian.util.UserUtil;

import org.jetbrains.annotations.NotNull;

/**
 * Created by jianjianhong on 20-3-25
 */
public class MyPointsActivity extends Activity {

    private TextView myPoint;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_points);
        myPoint = findViewById(R.id.myPoints);
        initData();

        findViewById(R.id.pay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pay(v);
            }
        });
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
                        if(userPoint == null) {
                            myPoint.setText("0.00");
                        }else {
                            myPoint.setText(userPoint.getPoints()+"");
                        }

                    }

                    @Override
                    public void onFailure(@NotNull String s) {
                        Toast.makeText(MyPointsActivity.this, "获取会员积分失败！", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void pay(View view) {
        Toast.makeText(this, "暂未实现", Toast.LENGTH_SHORT).show();
    }
}
