package com.chris.tiantian.util;

import android.text.TextUtils;

public class StringUtil {
    public static boolean isPhone(String phoneNumber) {
        if (TextUtils.isEmpty(phoneNumber) && phoneNumber.length() != 11)
            return false;
        String regex = "^1[3-9][0-9]\\d{8}$";
        return phoneNumber.matches(regex);
    }
}
