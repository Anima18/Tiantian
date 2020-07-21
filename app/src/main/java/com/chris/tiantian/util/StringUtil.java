package com.chris.tiantian.util;

import android.text.TextUtils;

import java.util.UUID;

public class StringUtil {
    public static boolean isPhone(String phoneNumber) {
        if (TextUtils.isEmpty(phoneNumber) && phoneNumber.length() != 11)
            return false;
        String regex = "^1[3-9][0-9]\\d{8}$";
        return phoneNumber.matches(regex);
    }

    public static String createNonceStr() {
        String s = UUID.randomUUID().toString();
        // 去掉“-”符号
        return s.replaceAll( "\\-","").toUpperCase();
    }

    public static String createTimestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

}
