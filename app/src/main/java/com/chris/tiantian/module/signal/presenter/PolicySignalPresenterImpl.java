package com.chris.tiantian.module.signal.presenter;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.sqlite.db.SimpleSQLiteQuery;

import com.anima.componentlib.paginglistview.PagingRecycleView;
import com.anima.eventflow.Event;
import com.anima.eventflow.EventFlow;
import com.anima.eventflow.EventResult;
import com.anima.networkrequest.NetworkRequest;
import com.anima.networkrequest.callback.DataListCallback;
import com.anima.networkrequest.entity.RequestParam;
import com.anima.networkrequest.util.sharedprefs.UserInfoSharedPreferences;
import com.chris.tiantian.MainActivity;
import com.chris.tiantian.R;
import com.chris.tiantian.base.db.dao.PolicySignalDao;
import com.chris.tiantian.entity.MarketTick;
import com.chris.tiantian.entity.PolicySignal;
import com.chris.tiantian.entity.Constant;
import com.chris.tiantian.entity.dataparser.ListDataParser;
import com.chris.tiantian.entity.dataparser.ListStatusDataParser;
import com.chris.tiantian.entity.eventmessage.MarketTIcksMessage;
import com.chris.tiantian.entity.eventmessage.PolicySignalMessage;
import com.chris.tiantian.entity.eventmessage.PolicySignalNoDataMessage;
import com.chris.tiantian.module.signal.activity.PolicySignalActionView;
import com.chris.tiantian.util.CommonUtil;
import com.chris.tiantian.util.DateUtil;
import com.chris.tiantian.util.LocationLog;
import com.chris.tiantian.util.PreferencesUtil;
import com.chris.tiantian.util.UserUtil;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static android.app.NotificationManager.IMPORTANCE_HIGH;
import static androidx.core.app.NotificationCompat.PRIORITY_HIGH;

/**
 * Created by jianjianhong on 20-1-14
 */
public class PolicySignalPresenterImpl implements PolicySignalPresenter {

    private Context context;
    private PolicySignalActionView actionView;
    private UserInfoSharedPreferences preferences;
    //private PolicySignalManager manager;
    private PolicySignalDao policySignalDao;

    public PolicySignalPresenterImpl(Context context) {
        this(context, null);
    }

    public PolicySignalPresenterImpl(Context context, PolicySignalActionView actionView) {
        this.context = context;
        this.actionView = actionView;
        preferences = PreferencesUtil.getUserInfoPreference();
        //manager = DBManager.getInstance(context).getPolicySignalManager();
        policySignalDao = CommonUtil.getDatabase().policySignalDao();
    }

    @Override
    public void requestDataByLocal(PagingRecycleView.LoadCallback loadCallback, int startNo, int endNo) {
        Event event1 = new Event() {
            @Override
            protected Object run() {
                String sql = String.format("select * from PolicySignal_Bean  order by time desc LIMIT %s, %s", startNo+"", endNo+"");

                SimpleSQLiteQuery query = new SimpleSQLiteQuery(sql);
                return uniqData(policySignalDao.query(query));
            }
        };
        EventFlow.create(context, event1).subscribe(new EventResult() {
            @Override
            public void onResult(Object data) {
                actionView.showData(loadCallback, (List<PolicySignal>)data);
            }
        });

    }

    private List<PolicySignal> uniqData(List<PolicySignal> dataList) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return dataList.stream()
                    .collect(
                            Collectors.collectingAndThen(
                                    Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(PolicySignal::getTime))), ArrayList::new))
                    .stream()
                    .sorted(Comparator.comparing(PolicySignal::getTime).reversed()).collect(Collectors.toList());
        }else {
            Set set = new HashSet();
            List newList = new ArrayList();
            for (Iterator iter = dataList.iterator(); iter.hasNext();) {
                Object element = iter.next();
                if (set.add(element))
                    newList.add(element);
            }
            return newList;
        }
    }

    @Override
    public void monitorPolicySignal() {
        refreshPolicySignal(false);
    }

    @Override
    public void monitorMarketTicks() {
        String url = String.format("%s/comment/apiv2/qryMarketTicks", CommonUtil.getBaseUrl());
        new NetworkRequest<MarketTick>(context)
                .url(url)
                .method(RequestParam.Method.GET)
                .dataClass(MarketTick.class)
                .dataParser(new ListDataParser<>())
                .getList(new DataListCallback<MarketTick>() {
                    @Override
                    public void onFailure(@NotNull String s) {
                        actionView.showError(s);
                    }

                    @Override
                    public void onSuccess(@Nullable List<? extends MarketTick> list) {
                        EventBus.getDefault().post(new MarketTIcksMessage(list));
                    }
                });
    }

    @Override
    public void refreshPolicySignal(boolean isShowResultMessage) {
        if(!UserUtil.isLogin()) {
            return;
        }
        String lastTime = preferences.getStringValue(Constant.SP_LASTTIME_POLICY_SIGNAL_NETWORK, "2020.01.01 01:00:00");
        LocationLog.getInstance().i("PolicyMonitorService request policySignal at "+lastTime);
        lastTime = Base64.encodeToString(lastTime.getBytes(), Base64.DEFAULT);
        String url = String.format("%s/comment/apiv2/policySubscribedSignalList/%s/%s", CommonUtil.getBaseUrl(), UserUtil.getUserId(), lastTime);
        new NetworkRequest<PolicySignal>(context)
                .url(url)
                .method(RequestParam.Method.GET)
                .dataClass(PolicySignal.class)
                .dataParser(new ListStatusDataParser<PolicySignal>())
                .getList(new DataListCallback<PolicySignal>() {
                    @Override
                    public void onFailure(@NotNull String s) {
                        LocationLog.getInstance().i("PolicyMonitorService request error: "+s);
                        EventBus.getDefault().post(new PolicySignalMessage(PreferencesUtil.getConfigPreference().getBooleanValue(Constant.SP_Message_LOADED, false)));
                        PreferencesUtil.getConfigPreference().putBooleanValue(Constant.SP_Message_LOADED, false);
                    }

                    @Override
                    public void onSuccess(@NotNull List<? extends PolicySignal> list) {
                        LocationLog.getInstance().i("PolicyMonitorService request success");
                        if(list != null && list.size() > 0) {
                            LocationLog.getInstance().i("PolicyMonitorService data size: "+ list.size());
                            Event event = new Event() {
                                @Override
                                protected Object run() {
                                    policySignalDao.insertList((List<PolicySignal>) list);
                                    return null;
                                }
                            };
                            EventFlow.create(context, event).subscribe(new EventResult() {
                                @Override
                                public void onResult(Object data) {
                                    preferences.putStringValue(Constant.SP_LASTTIME_POLICY_SIGNAL_NETWORK, DateUtil.getTime(new Date(), Constant.DATA_TIME_FORMAT));
                                    EventBus.getDefault().post(new PolicySignalMessage(true));
                                    LocationLog.getInstance().i("PolicyMonitorService showNotification");
                                    showNotification(context);
                                }
                            });
                        }else {
                            EventBus.getDefault().post(new PolicySignalMessage(PreferencesUtil.getConfigPreference().getBooleanValue(Constant.SP_Message_LOADED, false)));
                            PreferencesUtil.getConfigPreference().putBooleanValue(Constant.SP_Message_LOADED, false);
                        }
                        preferences.putBooleanValue(Constant.SP_LOADING_POLICY_SIGNAL_DATABASE, false);
                    }
                });
    }

    private void updateList(List<PolicySignal> policySignals){
        LocationLog.getInstance().i("PolicyMonitorService insert data");
        if(policySignals == null || policySignals.size() == 0) {
            return;
        }
        for(PolicySignal signal : policySignals) {
            if(!isExists(signal)) {
                policySignalDao.insert(signal);
            }
        }
    }

    private boolean isExists(PolicySignal policySignal) {
        return policySignalDao.findPolicySignal(policySignal.id).size() > 0;
    }

    public static void showNotification(Context context) {
        //获取NotificationManager实例
        NotificationManager notifyManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "my_channel_id_01";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", IMPORTANCE_HIGH);
            //Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            // Configure the notification channel.
            notificationChannel.setDescription("收到一个新的操作信号");
            notificationChannel.setBypassDnd(true);//设置绕过免打扰模式
            notificationChannel.canBypassDnd();//检测是否绕过免打扰模式
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_SECRET);//设置在锁屏界面上显示这条通知
            //设置通知出现时的呼吸灯
            notificationChannel.enableLights(true);
            notificationChannel.enableVibration(true);
            notificationChannel.setLightColor(Color.WHITE);
            //卸载重装，让设置的震动生效 注意：设置震动时不用动态申请震动权限，只在清单文件中声明即可
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            Uri mUri = Settings.System.DEFAULT_NOTIFICATION_URI;
            notificationChannel.setSound(mUri, Notification.AUDIO_ATTRIBUTES_DEFAULT);
            notifyManager.createNotificationChannel(notificationChannel);
        }

        Intent mainIntent = new Intent(context, MainActivity.class);
        mainIntent.putExtra("KEY_PAGE_INDEX", 2);
        PendingIntent mainPendingIntent = PendingIntent.getActivity(context, 0, mainIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        //实例化NotificationCompat.Builde并设置相关属性
        NotificationCompat.Builder builder = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID);
        }else {
            builder = new NotificationCompat.Builder(context);
            builder.setPriority(PRIORITY_HIGH);
        }

        //设置小图标
        builder.setSmallIcon(R.mipmap.ic_app)
                //设置通知标题
                .setContentTitle("天机")
                //设置通知内容
                .setContentText("收到一个新的操作信号")
                .setContentIntent(mainPendingIntent)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL);
        //设置通知时间，默认为系统发出通知的时间，通常不用设置
        notifyManager.notify(1, builder.build());
    }
}
