package com.chris.tiantian.module.me.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anima.networkrequest.NetworkRequest;
import com.anima.networkrequest.callback.DataObjectCallback;
import com.anima.networkrequest.entity.RequestParam;
import com.chris.tiantian.R;
import com.chris.tiantian.base.db.dao.PolicySignalDao;
import com.chris.tiantian.base.service.VersionUploadService;
import com.chris.tiantian.entity.ActionMenuItem;
import com.chris.tiantian.entity.Constant;
import com.chris.tiantian.entity.User;
import com.chris.tiantian.entity.dataparser.ObjectStatusDataParser;
import com.chris.tiantian.module.commom.ActionAdapter;
import com.chris.tiantian.module.login.LoginActivity;
import com.chris.tiantian.util.CommonUtil;
import com.chris.tiantian.util.PreferencesUtil;
import com.chris.tiantian.util.UserUtil;

import org.jetbrains.annotations.NotNull;

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
                    case 4:
                        new AlertDialog.Builder(getContext())
                                .setTitle("提示")
                                .setMessage("初始设置会重置策略定制，和清空综合提醒数据，是否继续？")
                                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        resetData();
                                    }
                                })
                                .setNegativeButton("否", null)
                                .show();

                        break;
                    default:
                        Toast.makeText(getContext(), "暂未实现", Toast.LENGTH_SHORT).show();

                }
            }
        });
        dataRv.setAdapter(adapter);
    }

    private void resetData() {
        String currentStrategyId = PreferencesUtil.getUserInfoPreference().getStringValue(Constant.SP_CURRENT_STRATEGY_ID, "0");
        String saveUrl = String.format("%s/comment/apiv2/policySubscribe/%s/%s/%s", CommonUtil.getBaseUrl(), UserUtil.getUserId(), currentStrategyId, "0");
        new NetworkRequest<String>(getContext())
                .url(saveUrl)
                .method(RequestParam.Method.GET)
                .loadingMessage("正在重置中...")
                .dataClass(String.class)
                .dataParser(new ObjectStatusDataParser<String>())
                .getObject(new DataObjectCallback<String>() {
                    @Override
                    public void onSuccess(@org.jetbrains.annotations.Nullable String aBoolean) {
                        UserUtil.resetMessage();
                        PolicySignalDao policySignalDao = CommonUtil.getDatabase().policySignalDao();
                        policySignalDao.clear();
                        PreferencesUtil.updateMessageTimestamp();
                        Toast.makeText(getContext(), "重置成功", Toast.LENGTH_SHORT).show();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent LaunchIntent = getContext().getPackageManager().getLaunchIntentForPackage(getActivity().getApplication().getPackageName());
                                LaunchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(LaunchIntent);
                            }
                        }, 100);// 1秒钟后重启应用
                    }

                    @Override
                    public void onFailure(@NotNull String s) {
                        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onClick(View v) {
        if(UserUtil.isLogin()) {

            switch (v.getId()) {
                case R.id.setting_my_publish:
                    Toast.makeText(getContext(), "暂未实现", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.setting_my_buy:
                    //startActivity(new Intent(getContext(), PurchasedPolicyActivity.class));
                    Toast.makeText(getContext(), "暂未实现", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.settings_alert_set:
                    startActivity(new Intent(getContext(), SmsSettingActivity.class));
                    break;
                case R.id.settings_vip_center:
                    Toast.makeText(getContext(), "暂未实现", Toast.LENGTH_SHORT).show();
                    break;
            }
        }else {
            showMustLogin();
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
        itemList.add(new ActionMenuItem("版本更新",  CommonUtil.getVersionName(getContext()), R.drawable.settings_ic_update, R.color.secondary_text_dark_color));
        itemList.add(new ActionMenuItem("我的积分", R.drawable.settings_ic_score, R.color.secondary_text_dark_color));
        itemList.add(new ActionMenuItem("使用教程", R.drawable.settings_ic_using_tutorials, R.color.secondary_text_dark_color));
        itemList.add(new ActionMenuItem("关于我们", R.drawable.settings_ic_about_us, R.color.secondary_text_dark_color));
        itemList.add(new ActionMenuItem("初始设置", R.drawable.ic_reset, R.color.secondary_text_dark_color));
        itemList.get(2).setMake(true);
        return itemList;
    }

    private void showMustLogin() {
        Toast.makeText(getContext(), "请先登录！", Toast.LENGTH_SHORT).show();
    }
}
