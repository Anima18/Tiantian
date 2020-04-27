package com.chris.tiantian.util;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;

import com.chris.tiantian.application.BaseApplication;
import com.chris.tiantian.base.db.AppDatabase;

import java.io.File;


/**
 * Created by Chris on 2015/9/9.
 */
public class CommonUtil {
    public static Context getApplicationContext() {
        return BaseApplication.mContext;
    }

    public static AppDatabase getDatabase() {
        return BaseApplication.appDatabase;
    }

    public static String getBaseUrl() {
        //真实环境IP
        return "http://114.67.65.199:8080";

        //测试环境IP
        //return "http://114.67.85.91:8080";
    }

    public static String getDownloadDirPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/";
    }

    public static File getDownloadDir() {
        return new File(getDownloadDirPath());
    }

    public static int dipToPixels(float dipValue) {
        DisplayMetrics metrics = getApplicationContext().getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
    }


    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * The width of the side navigation is equal to the width of the screen minus the height of the actionbar,
     * 56 dp or away from the right side of the screen edge.Side navigation maximum width
     * is 5 times the standard increment (56 dp on the mobile phone, tablet is 64 dp).
     * @return
     */
    public static int getNavigationViewWidth() {
        return 5*64;
    }

    /**
     * 获取屏幕宽度dp
     * 根据宽度dp，可以判断当前设备属于哪个Value范畴
     * @param context
     * @return
     */
    public static int getSceenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metric = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;

        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (width / scale + 0.5f);
    }
    public static int percent( int  p1,  int  p2)  {
        double p3  =  (p1*1.0) / (p2*1.0);
        int valie = (int) (p3*100);
        return valie;
    }

    public static String getPackageName(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);
            return packageInfo.packageName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getVersionName(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
