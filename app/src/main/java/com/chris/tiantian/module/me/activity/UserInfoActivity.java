package com.chris.tiantian.module.me.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anima.componentlib.toolbar.Toolbar;
import com.chris.tiantian.R;
import com.chris.tiantian.base.db.dao.PolicySignalDao;
import com.chris.tiantian.entity.ActionMenuItem;
import com.chris.tiantian.entity.Constant;
import com.chris.tiantian.entity.PolicySignal;
import com.chris.tiantian.entity.User;
import com.chris.tiantian.module.commom.ActionAdapter;
import com.chris.tiantian.module.commom.UserInfoAdapter;
import com.chris.tiantian.module.login.LoginActivity;
import com.chris.tiantian.util.CommonUtil;
import com.chris.tiantian.util.DateUtil;
import com.chris.tiantian.util.PreferencesUtil;
import com.chris.tiantian.util.UserUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by jianjianhong on 20-4-29
 */
public class UserInfoActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        toolbar = findViewById(R.id.activity_toolBar);
        toolbar.setTitle("个人信息");
        recyclerView = findViewById(R.id.user_info_list);
        initData();

        findViewById(R.id.btn_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserUtil.logout();
                UserUtil.resetMessage();
                /*PolicySignalDao policySignalDao = CommonUtil.getDatabase().policySignalDao();
                policySignalDao.clear();*/
                UserInfoActivity.this.finish();
                startActivity(new Intent(UserInfoActivity.this, LoginActivity.class));
            }
        });
    }

    private void initData() {
        List<ActionMenuItem> itemList = getActionMenuItems();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        UserInfoAdapter adapter = new UserInfoAdapter(this, itemList);
        adapter.setOnItemClickListener(new UserInfoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {

            }
        });
        recyclerView.setAdapter(adapter);
    }

    private List<ActionMenuItem> getActionMenuItems() {
        User user = UserUtil.getUser();
        List<ActionMenuItem> itemList = new ArrayList<>();
        itemList.add(new ActionMenuItem("用户名",  user.getName()));
        itemList.add(new ActionMenuItem("中文名", user.getChName() == null ? "" : user.getChName().toString()));
        itemList.add(new ActionMenuItem("手机号", user.getPhoneNumber()));
        itemList.add(new ActionMenuItem("会员等级", user.getLevel()));
        return itemList;
    }
}
