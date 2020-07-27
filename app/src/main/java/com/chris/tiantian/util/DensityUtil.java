package com.chris.tiantian.util;

import android.content.Context;

/**
 * @author chenjieliang
 */
public class DensityUtil {
    public static int dpToPx(int dp) {
        //获取屏蔽的像素密度系数
        float density = CommonUtil.getApplicationContext().getResources().getDisplayMetrics().density;
        return (int)(dp * density);
    }

    public static float dpToPx(Context context, float dp) {
        //获取屏蔽的像素密度系数
        float density = context.getResources().getDisplayMetrics().density;
        return dp * density;
    }

    public static float pxTodp(Context context, float px) {
        //获取屏蔽的像素密度系数
        float density = context.getResources().getDisplayMetrics().density;
        return px / density;
    }
}
