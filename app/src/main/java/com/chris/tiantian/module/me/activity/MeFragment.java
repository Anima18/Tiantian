package com.chris.tiantian.module.me.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chris.tiantian.R;
import com.chris.tiantian.base.service.VersionUploadService;
import com.chris.tiantian.entity.ActionMenuItem;
import com.chris.tiantian.entity.User;
import com.chris.tiantian.module.login.LoginActivity;
import com.chris.tiantian.util.CommonUtil;
import com.chris.tiantian.module.commom.ActionAdapter;
import com.chris.tiantian.util.UserUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jianjianhong on 19-12-18
 */
public class MeFragment extends Fragment implements View.OnClickListener {
    private View rootView;
    private RecyclerView dataRv;
    private TextView beVipView;
    private ImageView avatarImageView;
    private View publishView;
    private View buyView;
    private View alertView;
    private View vipCenterView;
    private List<ActionMenuItem> itemList;
    private ActionAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_me, container, false);
            initView(rootView);
            initData();
            hasNewVersion();
        }
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(UserUtil.isLogin()) {
            User user = UserUtil.getUser();
            beVipView.setText(user.getName());
            avatarImageView.setImageResource(R.drawable.moment_avatar_user);
            beVipView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getContext(), UserInfoActivity.class));
                }
            });
        }else {
            beVipView.setText("登陆/注册");
            avatarImageView.setImageResource(R.drawable.moment_avatar_default);
            beVipView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                }
            });
        }
    }

    public void initView(View view) {
        dataRv = view.findViewById(R.id.profileAct_menu_rv);
        beVipView = view.findViewById(R.id.toBeVip);
        avatarImageView = view.findViewById(R.id.moment_avatar_imageView);
        publishView = view.findViewById(R.id.setting_my_publish);
        buyView = view.findViewById(R.id.setting_my_buy);
        alertView = view.findViewById(R.id.settings_alert_set);
        vipCenterView = view.findViewById(R.id.settings_vip_center);
        publishView.setOnClickListener(this);
        buyView.setOnClickListener(this);
        alertView.setOnClickListener(this);
        vipCenterView.setOnClickListener(this);
    }

    public void initData() {
        this.itemList = getActionMenuItems();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        //dataRv.addItemDecoration(new DividerItemDecoration(getContext(), layoutManager.getOrientation()));
        dataRv.setLayoutManager(layoutManager);
        adapter = new ActionAdapter(getContext(), itemList);
        adapter.setOnItemClickListener(new ActionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                switch (position) {
                    case 0:
                        VersionUploadService.checkUpdate(getContext());
                        break;
                    case 1:
                        if(UserUtil.isLogin()) {
                            startActivity(new Intent(getContext(), MyPointsActivity.class));
                        }else {
                            showMustLogin();
                        }

                        break;
                    case 2:
                        startActivity(new Intent(getContext(), UserGuideActivity.class));
                        break;
                    default:
                        Toast.makeText(getContext(), "暂未实现", Toast.LENGTH_SHORT).show();

                }

            }
        });
        dataRv.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setting_my_publish:
                Toast.makeText(getContext(), "暂未实现", Toast.LENGTH_SHORT).show();
                break;
            case R.id.setting_my_buy:
                if(UserUtil.isLogin()) {
                    startActivity(new Intent(getContext(), PurchasedPolicyActivity.class));
                }else {
                    showMustLogin();
                }
                break;
            case R.id.settings_alert_set:
                Toast.makeText(getContext(), "暂未实现", Toast.LENGTH_SHORT).show();
                break;
            case R.id.settings_vip_center:
                Toast.makeText(getContext(), "暂未实现", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void hasNewVersion() {
        VersionUploadService.hasNewVersion(getContext(), new VersionUploadService.OnNewVersionListener() {
            @Override
            public void onNewVersion(boolean hasNewVersion) {
                itemList.get(0).setMake(hasNewVersion);
                adapter.notifyItemChanged(0);
            }
        });
    }

    private List<ActionMenuItem> getActionMenuItems() {
        List<ActionMenuItem> itemList = new ArrayList<>();
        itemList.add(new ActionMenuItem("版本更新",  CommonUtil.getVersionName(getContext()), R.drawable.settings_ic_update));
        itemList.add(new ActionMenuItem("我的积分", R.drawable.settings_ic_score));
        itemList.add(new ActionMenuItem("使用教程", R.drawable.settings_ic_using_tutorials));
        itemList.add(new ActionMenuItem("关于我们", R.drawable.settings_ic_about_us));
        itemList.get(2).setMake(true);
        return itemList;
    }

    private void showMustLogin() {
        Toast.makeText(getContext(), "请先登录！", Toast.LENGTH_SHORT).show();
    }
}
