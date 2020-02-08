package com.chris.tiantian.module.main.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.chris.tiantian.util.CommonUtil;
import com.chris.tiantian.util.ProfileAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jianjianhong on 19-12-18
 */
public class MeFragment extends Fragment implements View.OnClickListener {
    private View rootView;
    private TextView userNameTv;
    private TextView userTypeTv;
    private RecyclerView dataRv;
    private View beVipView;
    private View publishView;
    private View buyView;
    private View alertView;
    private View vipCenterView;
    private List<ActionMenuItem> itemList;
    private ProfileAdapter adapter;

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

    public void initView(View view) {
        dataRv = view.findViewById(R.id.profileAct_menu_rv);
        beVipView = view.findViewById(R.id.toBeVip);
        publishView = view.findViewById(R.id.setting_my_publish);
        buyView = view.findViewById(R.id.setting_my_buy);
        alertView = view.findViewById(R.id.settings_alert_set);
        vipCenterView = view.findViewById(R.id.settings_vip_center);
        beVipView.setOnClickListener(this);
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
        adapter = new ProfileAdapter(getContext(), itemList);
        adapter.setOnItemClickListener(new ProfileAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                if(position == 0) {
                    VersionUploadService.checkUpdate(getContext());
                }else {
                    Toast.makeText(getContext(), "暂未实现", Toast.LENGTH_SHORT).show();
                }

            }
        });
        dataRv.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toBeVip:
                Toast.makeText(getContext(), "暂未实现", Toast.LENGTH_SHORT).show();
                break;
            case R.id.setting_my_publish:
                Toast.makeText(getContext(), "暂未实现", Toast.LENGTH_SHORT).show();
                break;
            case R.id.setting_my_buy:
                Toast.makeText(getContext(), "暂未实现", Toast.LENGTH_SHORT).show();
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
        return itemList;
    }

    public void toVip(View view) {
        Toast.makeText(getContext(), "暂未实现", Toast.LENGTH_SHORT).show();
    }

    public void toMyPublish(View view) {
        Toast.makeText(getContext(), "暂未实现", Toast.LENGTH_SHORT).show();
    }

    public void toMyBuy(View view) {
        Toast.makeText(getContext(), "暂未实现", Toast.LENGTH_SHORT).show();
    }

    public void toAlert(View view) {
        Toast.makeText(getContext(), "暂未实现", Toast.LENGTH_SHORT).show();
    }

    public void toVipCenter(View view) {
        Toast.makeText(getContext(), "暂未实现", Toast.LENGTH_SHORT).show();
    }
}
