package com.chris.tiantian.module.main.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.anima.eventflow.Event;
import com.anima.eventflow.EventFlow;
import com.anima.eventflow.EventResult;
import com.anima.networkrequest.DataListCallback;
import com.anima.networkrequest.NetworkRequest;
import com.anima.networkrequest.entity.RequestParam;
import com.anima.networkrequest.util.sharedprefs.UserInfoSharedPreferences;
import com.chris.tiantian.base.database.DBManager;
import com.chris.tiantian.base.database.PolicyManager;
import com.chris.tiantian.entity.Constant;
import com.chris.tiantian.entity.NetworkDataParser;
import com.chris.tiantian.entity.Policy;
import com.chris.tiantian.entity.PolicyMessage;
import com.chris.tiantian.module.main.activity.PolicyActionView;
import com.chris.tiantian.util.CommonUtil;
import com.ut.utuicomponents.common.utils.DateUtil;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.List;

/**
 * Created by jianjianhong on 20-1-14
 */
public class PolicyPresenterImpl implements PolicyPresenter {

    private Context context;
    private PolicyActionView actionView;
    private UserInfoSharedPreferences preferences;
    private PolicyManager manager;

    public PolicyPresenterImpl(Context context) {
        this(context, null);
    }

    public PolicyPresenterImpl(Context context, PolicyActionView actionView) {
        this.context = context;
        this.actionView = actionView;
        preferences = UserInfoSharedPreferences.Companion.getInstance(context);
        manager = DBManager.getInstance(context).getPolicyManager();
    }

    @Override
    public void requestDataByLocal() {
        List<Policy> Policys = manager.query();
        actionView.showData(Policys);
    }

    @Override
    public void requestDataByNetwork() {
        actionView.showLoading();
        String url = String.format("%s/comment/apiv2/policylist", CommonUtil.getBaseUrl());
        new NetworkRequest<Policy>(context)
                .url(url)
                .method(RequestParam.Method.GET)
                .dataClass(Policy.class)
                .dataParser(new NetworkDataParser<Policy>())
                .getList(new DataListCallback<Policy>() {
                    @Override
                    public void onFailure(@NotNull String s) {
                        actionView.showError(s);
                    }

                    @Override
                    public void onSuccess(@NotNull List<? extends Policy> list) {

                        Event event = new Event() {
                            @Override
                            protected Object run() {
                                manager.clear();
                                manager.insertList((List<Policy>) list);
                                return null;
                            }
                        };
                        EventFlow.create(context, event).subscribe(new EventResult() {
                            @Override
                            public void onResult(Object data) {
                                actionView.showData((List<Policy>)list);
                                preferences.putStringValue(Constant.SP_LASTTIME_POLICY_NETWORK, DateUtil.getTime(new Date(), Constant.DATA_TIME_FORMAT));
                                preferences.putBooleanValue(Constant.SP_LOADING_POLICY_DATABASE, true);
                            }
                        });
                    }
                });
    }

    @Override
    public void monitorPolicy() {
        Log.i("PolicyMonitorService", "monitorPolicy");
        String lastTime = preferences.getStringValue(Constant.SP_LASTTIME_POLICY_NETWORK, "");
        if(TextUtils.isEmpty(lastTime)) {
            return;
        }

        lastTime = Base64.encodeToString(lastTime.getBytes(), Base64.DEFAULT);
        String url = String.format("%s/comment/apiv2/policylist/%s", CommonUtil.getBaseUrl(), lastTime);
        new NetworkRequest<Policy>(context)
                .url(url)
                .method(RequestParam.Method.GET)
                .dataClass(Policy.class)
                .dataParser(new NetworkDataParser<Policy>())
                .getList(new DataListCallback<Policy>() {
                    @Override
                    public void onFailure(@NotNull String s) {
                    }

                    @Override
                    public void onSuccess(@NotNull List<? extends Policy> list) {
                        if(list != null && list.size() > 0) {
                            Event event = new Event() {
                                @Override
                                protected Object run() {
                                    manager.updateList((List<Policy>) list);
                                    preferences.putStringValue(Constant.SP_LASTTIME_POLICY_NETWORK, DateUtil.getTime(new Date(), Constant.DATA_TIME_FORMAT));
                                    preferences.putBooleanValue(Constant.SP_LOADING_POLICY_DATABASE, true);
                                    return null;
                                }
                            };
                            EventFlow.create(context, event).subscribe(new EventResult() {
                                @Override
                                public void onResult(Object data) {
                                    EventBus.getDefault().post(new PolicyMessage());
                                }
                            });
                        }
                    }
                });
    }
}
