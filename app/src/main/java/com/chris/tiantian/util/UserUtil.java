package com.chris.tiantian.util;

import android.text.TextUtils;

import com.anima.networkrequest.util.sharedprefs.ConfigSharedPreferences;
import com.anima.networkrequest.util.sharedprefs.UserInfoSharedPreferences;
import com.chris.tiantian.base.db.dao.PolicySignalDao;
import com.chris.tiantian.entity.Constant;
import com.chris.tiantian.entity.User;
import com.chris.tiantian.util.jsonParse.UTJsonFactory;

/**
 * Created by jianjianhong on 20-4-29
 */
public class UserUtil {

    public static User getUser() {
        String userData = PreferencesUtil.getUserInfoPreference().getStringValue(Constant.SP_USER_DATA, "");
        if(TextUtils.isEmpty(userData)) {
            return null;
        }else {
            return UTJsonFactory.createJson().fromJson(userData, User.class);
        }
    }

    public static int getUserId() {
        return PreferencesUtil.getUserInfoPreference().getIntValue(Constant.SP_USER_ID, -1);
    }

    public static String getToken() {
        return PreferencesUtil.getUserInfoPreference().getStringValue(Constant.SP_TOKEN, "");
    }

    public static boolean isLogin() {
        return getUserId() != -1;
    }

    public static void logout() {
        UserInfoSharedPreferences preferences = PreferencesUtil.getUserInfoPreference();
        preferences.remove(Constant.SP_USER_ID);
        preferences.remove(Constant.SP_USER_DATA);
        preferences.remove(Constant.SP_TOKEN);
        preferences.remove(Constant.SP_OPENID);
    }

    public static void resetMessage() {
        /*PolicySignalDao policySignalDao = CommonUtil.getDatabase().policySignalDao();
        policySignalDao.clear();*/
        //clear message lastTime
        //PreferencesUtil.getUserInfoPreference().remove(Constant.SP_LASTTIME_POLICY_SIGNAL_NETWORK);
        PreferencesUtil.getConfigPreference().putBooleanValue(Constant.SP_STRATEGY_LOADED, true);
    }
}
