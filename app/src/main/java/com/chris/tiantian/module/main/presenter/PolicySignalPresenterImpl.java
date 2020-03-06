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
import com.chris.tiantian.base.database.PolicySignalManager;
import com.chris.tiantian.entity.Constant;
import com.chris.tiantian.entity.NetworkDataParser;
import com.chris.tiantian.entity.PolicySignal;
import com.chris.tiantian.entity.PolicySignalMessage;
import com.chris.tiantian.module.main.activity.PolicySignalActionView;
import com.chris.tiantian.util.CommonUtil;
import com.ut.utuicomponents.common.utils.DateUtil;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.List;

/**
 * Created by jianjianhong on 20-1-14
 */
public class PolicySignalPresenterImpl implements PolicySignalPresenter {

    private Context context;
    private PolicySignalActionView actionView;
    private UserInfoSharedPreferences preferences;
    private PolicySignalManager manager;

    public PolicySignalPresenterImpl(Context context) {
        this(context, null);
    }

    public PolicySignalPresenterImpl(Context context, PolicySignalActionView actionView) {
        this.context = context;
        this.actionView = actionView;
        preferences = UserInfoSharedPreferences.Companion.getInstance(context);
        manager = DBManager.getInstance(context).getPolicySignalManager();
    }

    @Override
    public void requestDataByLocal() {
        Event event1 = new Event() {
            @Override
            protected Object run() {
                return manager.query();
            }
        };
        EventFlow.create(context, event1).subscribe(new EventResult() {
            @Override
            public void onResult(Object data) {
                actionView.showData((List<PolicySignal>)data);
            }
        });
        /*List<PolicySignal> policySignals = manager.query();
        actionView.showData(policySignals);*/
    }

    @Override
    public void requestDataByNetwork() {
        int currentPolicy = preferences.getIntValue(Constant.SP_CURRENT_POLICY, -1);
        if(currentPolicy == -1) {
            actionView.showError("请在广场列表选择一个策略");
        }else {
            actionView.showLoading();
            String url = String.format("%s/comment/apiv2/policysignallist/%s", CommonUtil.getBaseUrl(), currentPolicy+"");
            new NetworkRequest<PolicySignal>(context)
                    .url(url)
                    .method(RequestParam.Method.GET)
                    .dataClass(PolicySignal.class)
                    .dataParser(new NetworkDataParser<PolicySignal>())
                    .getList(new DataListCallback<PolicySignal>() {
                        @Override
                        public void onFailure(@NotNull String s) {
                            actionView.showError(s);
                        }

                        @Override
                        public void onSuccess(@NotNull List<? extends PolicySignal> list) {
                            actionView.showData((List<PolicySignal>)list);

                            preferences.putStringValue(Constant.SP_LASTTIME_POLICY_SIGNAL_NETWORK, DateUtil.getTime(new Date(), Constant.DATA_TIME_FORMAT));
                            preferences.putBooleanValue(Constant.SP_LOADING_POLICY_SIGNAL_DATABASE, true);
                            manager.clear();
                            manager.insertList((List<PolicySignal>) list);
                        }
                    });
        }

    }

    @Override
    public void monitorPolicySignal() {
        Log.i("PolicyMonitorService", "monitorPolicySignal");
        String lastTime = preferences.getStringValue(Constant.SP_LASTTIME_POLICY_SIGNAL_NETWORK, "");
        int currentPolicy = preferences.getIntValue(Constant.SP_CURRENT_POLICY, -1);
        if(currentPolicy == -1 || TextUtils.isEmpty(lastTime)) {
            return;
        }

        lastTime = Base64.encodeToString(lastTime.getBytes(), Base64.DEFAULT);
        String url = String.format("%s/comment/apiv2/policysignallist/%s/%s", CommonUtil.getBaseUrl(), currentPolicy+"", lastTime);
        new NetworkRequest<PolicySignal>(context)
                .url(url)
                .method(RequestParam.Method.GET)
                .dataClass(PolicySignal.class)
                .dataParser(new NetworkDataParser<PolicySignal>())
                .getList(new DataListCallback<PolicySignal>() {
                    @Override
                    public void onFailure(@NotNull String s) {

                    }

                    @Override
                    public void onSuccess(@NotNull List<? extends PolicySignal> list) {
                        if(list != null && list.size() > 0) {
                            manager.updateList((List<PolicySignal>) list);
                            preferences.putStringValue(Constant.SP_LASTTIME_POLICY_SIGNAL_NETWORK, DateUtil.getTime(new Date(), Constant.DATA_TIME_FORMAT));
                            preferences.putBooleanValue(Constant.SP_LOADING_POLICY_SIGNAL_DATABASE, true);
                            EventBus.getDefault().post(new PolicySignalMessage());
                        }
                    }
                });
    }
}
