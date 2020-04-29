package com.chris.tiantian.util;

import com.anima.networkrequest.util.sharedprefs.ConfigSharedPreferences;
import com.anima.networkrequest.util.sharedprefs.UserInfoSharedPreferences;

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
}
