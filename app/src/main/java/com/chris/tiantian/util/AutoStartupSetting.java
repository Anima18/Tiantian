package com.chris.tiantian.util;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import com.anima.networkrequest.util.sharedprefs.ConfigSharedPreferences;

import java.util.List;

import static com.chris.tiantian.entity.Constant.SHOW_AUTO_STARTUP_MAKER;

/**
 * Created by jianjianhong on 20-3-6
 */
public class AutoStartupSetting {

    public static void open(Context context) {
        boolean isShow = ConfigSharedPreferences.Companion.getInstance(context).getBooleanValue(SHOW_AUTO_STARTUP_MAKER, false);
        if(!isShow) {
            new AlertDialog.Builder(context)
                    .setTitle("提示")
                    .setMessage("为了在后台正常运行,请允许应用自启动或者后台运行.")
                    .setCancelable(false)
                    .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            openSettingPage(context);
                        }
                    })
                    .show();
        }
    }

    public static void openSettingPage(Context context) {
        try {
            Intent intent = new Intent();
            String manufacturer = android.os.Build.MANUFACTURER;
            if ("xiaomi".equalsIgnoreCase(manufacturer)) {
                intent.setComponent(new ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity"));
            } else if ("oppo".equalsIgnoreCase(manufacturer)) {
                intent.setComponent(new ComponentName("com.coloros.safecenter", "com.coloros.safecenter.permission.startup.StartupAppListActivity"));
            } else if ("vivo".equalsIgnoreCase(manufacturer)) {
                intent.setComponent(new ComponentName("com.vivo.permissionmanager", "com.vivo.permissionmanager.activity.BgStartUpManagerActivity"));
            } else if ("Letv".equalsIgnoreCase(manufacturer)) {
                intent.setComponent(new ComponentName("com.letv.android.letvsafe", "com.letv.android.letvsafe.AutobootManageActivity"));
            } else if ("Honor".equalsIgnoreCase(manufacturer)) {
                intent.setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.optimize.process.ProtectActivity"));
            } else if ("samsung".equalsIgnoreCase(manufacturer)) {
                intent.setComponent(ComponentName.unflattenFromString("com.samsung.android.sm/.app.dashboard.SmartManagerDashBoardActivity"));
            } else if ("HUAWEI".equalsIgnoreCase(manufacturer)) {
                intent.setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.optimize.process.ProtectActivity"));//跳自启动管理
            } else if ("Meizu".equalsIgnoreCase(manufacturer)) {
                intent.setComponent(ComponentName.unflattenFromString("com.meizu.safe/.permission.SmartBGActivity"));//跳转到后台管理页面
                //componentName = ComponentName.unflattenFromString("com.meizu.safe/.permission.PermissionMainActivity");
            } else {
                // 将用户引导到系统设置页面
                if (Build.VERSION.SDK_INT >= 9) {
                    intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                    intent.setData(Uri.fromParts("package", context.getPackageName(), null));
                } else if (Build.VERSION.SDK_INT <= 8) {
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
                    intent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
                }
            }

            List<ResolveInfo> list = context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            if (list.size() > 0) {
                context.startActivity(intent);
                ConfigSharedPreferences.Companion.getInstance(context).putBooleanValue(SHOW_AUTO_STARTUP_MAKER, true);
            }
        } catch (Exception e) {
            Log.e("exc", String.valueOf(e));
        }
    }
}