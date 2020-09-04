package com.chris.tiantian.util;

import android.util.Log;

import com.anima.networkrequest.util.sharedprefs.ConfigSharedPreferences;
import com.anima.networkrequest.util.sharedprefs.UserInfoSharedPreferences;
import com.chris.tiantian.entity.Constant;
import com.chris.tiantian.util.jsonParse.UTJsonFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jianjianhong on 20-4-29
 */
public class PreferencesUtil {
    public static UserInfoSharedPreferences getUserInfoPreference() {
        return UserInfoSharedPreferences.Companion.getInstance(CommonUtil.getApplicationContext());
    }

    public static ConfigSharedPreferences getConfigPreference() {
        return ConfigSharedPreferences.Companion.getInstance(CommonUtil.getApplicationContext());
    }

    public static String getMessageTimestamp() {
        int userId = UserUtil.getUserId();
        if(userId == -1) {
            return null;
        }
        String userIdStr = userId+"";
        Map<String, String> timeStampMap = getMessageTimestampMap();
        if(timeStampMap.containsKey(userIdStr)) {
            return timeStampMap.get(userIdStr);
        }else {
            String dataTime = DateUtil.getTime(new Date(), Constant.DATA_TIME_FORMAT);
            timeStampMap.put(userIdStr, dataTime);
            String timeStampData = UTJsonFactory.createJson().toJson(timeStampMap);
            PreferencesUtil.getUserInfoPreference().putStringValue(Constant.SP_MESSAGE_TIME_STAMP, timeStampData);
            return dataTime;
        }
    }

    public static void updateMessageTimestamp() {
        int userId = UserUtil.getUserId();
        if(userId == -1) {
            return ;
        }
        String userIdStr = userId+"";
        Map<String, String> timeStampMap = getMessageTimestampMap();
        timeStampMap.put(userIdStr, DateUtil.getTime(new Date(), Constant.DATA_TIME_FORMAT));
        String timeStampData = UTJsonFactory.createJson().toJson(timeStampMap);
        PreferencesUtil.getUserInfoPreference().putStringValue(Constant.SP_MESSAGE_TIME_STAMP, timeStampData);
    }

    private static Map<String, String> getMessageTimestampMap() {

        String timeStampData = PreferencesUtil.getUserInfoPreference().getStringValue(Constant.SP_MESSAGE_TIME_STAMP, "");
        Log.i("PreferencesUtil", timeStampData);
        Map<String, String> timeStampMap = UTJsonFactory.createJson().fromJson(timeStampData, Map.class);
        if(timeStampMap == null) {
            timeStampMap = new HashMap<>();
        }
        return timeStampMap;
    }
}
