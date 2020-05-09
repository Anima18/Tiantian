package com.chris.tiantian.base.service;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.anima.networkrequest.IRequest;
import com.anima.networkrequest.NetworkRequest;
import com.anima.networkrequest.callback.DataListCallback;
import com.anima.networkrequest.callback.DataObjectCallback;
import com.anima.networkrequest.entity.RequestParam;
import com.chris.tiantian.entity.dataparser.ListDataParser;
import com.chris.tiantian.entity.VersionInfo;
import com.chris.tiantian.util.CommonUtil;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class VersionUploadService {
    private static final String TAG = "VersionUploadService";

    public interface OnNewVersionListener{
        void onNewVersion(boolean hasNewVersion);
    }

    public static void hasNewVersion(Context context, OnNewVersionListener listener) {
        getVersionInfoRequest(context).getList(new DataListCallback<VersionInfo>() {
            @Override
            public void onFailure(@NotNull String s) {
                Log.i(TAG, "checkUpdate error: "+s);
            }

            @Override
            public void onSuccess(@NotNull List<? extends VersionInfo> list) {
                if(list != null && list.size() > 0) {
                    Collections.sort(list, new Comparator<VersionInfo>() {
                        @Override
                        public int compare(VersionInfo o1, VersionInfo o2) {
                            String versionName1 = o1.getVersion();
                            String versionName2 = o2.getVersion();
                            return compareVersionName(versionName1, versionName2);
                        }
                    });
                    String currentVersion = CommonUtil.getVersionName(context);
                    VersionInfo lastVersionInfo = list.get(0);
                    listener.onNewVersion(compareVersionName(currentVersion, lastVersionInfo.getVersion()) < 0);
                }else {
                    Log.i(TAG, "no version update");
                    listener.onNewVersion(false);
                }
            }
        });
    }

    /*public static void checkUpdateInBackground(Context context) {
        getVersionInfoRequest(context).getList(new DataListCallback<VersionInfo>() {
            @Override
            public void onFailure(@NotNull String s) {
                Log.i(TAG, "checkUpdate error: "+s);
            }

            @Override
            public void onSuccess(@NotNull List<? extends VersionInfo> list) {
                if(list != null && list.size() > 0) {
                    Collections.sort(list);
                    String currentVersion = CommonUtil.getVersionName(context);
                    VersionInfo lastVersionInfo = list.get(0);
                    if(currentVersion.compareTo(lastVersionInfo.getVersion()) < 0) {
                        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/"+lastVersionInfo.getInstallFileName());
                        if(!file.exists()) {
                            getDownloadApkRequest(context, lastVersionInfo).download(new DataObjectCallback<String>() {
                                @Override
                                public void onSuccess(String s) {
                                    showVersionInfoDialog(context, lastVersionInfo, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            installAPK(context, new File(s));
                                        }
                                    });
                                }

                                @Override
                                public void onFailure(@NotNull String s) {
                                    Log.i(TAG, "download apk error: "+s);
                                }
                            });
                        }else {
                            showVersionInfoDialog(context, lastVersionInfo, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    installAPK(context, file);
                                }
                            });
                        }
                    }
                }else {
                    Log.i(TAG, "no version update");
                }
            }
        });
    }*/

    public static void checkUpdate(Context context) {
        getVersionInfoRequest(context).loadingMessage("正在检查更新...").getList(new DataListCallback<VersionInfo>() {
                    @Override
                    public void onFailure(@NotNull String s) {
                        Log.i(TAG, "checkUpdate error: "+s);
                    }

                    @Override
                    public void onSuccess(@NotNull List<? extends VersionInfo> list) {
                        if(list != null && list.size() > 0) {
                            Collections.sort(list, new Comparator<VersionInfo>() {
                                @Override
                                public int compare(VersionInfo o1, VersionInfo o2) {
                                    String versionName1 = o1.getVersion();
                                    String versionName2 = o2.getVersion();
                                    return compareVersionName(versionName1, versionName2);
                                }
                            });
                            String currentVersion = CommonUtil.getVersionName(context);
                            VersionInfo lastVersionInfo = list.get(0);
                            if(compareVersionName(currentVersion, lastVersionInfo.getVersion()) < 0) {
                                showVersionInfoDialog(context, lastVersionInfo, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        downloadApk(context, lastVersionInfo);
                                    }
                                });
                            }else {
                                Toast.makeText(context, "已经是最新版本！", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(context, "已经是最新版本！", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private static void downloadApk(Context context, VersionInfo versionInfo) {
       File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/"+versionInfo.getInstallFileName());
       if(!file.exists()) {
           getDownloadApkRequest(context, versionInfo).loadingMessage("下载安装包...").download(new DataObjectCallback<String>() {
               @Override
               public void onSuccess(String s) {
                   Log.i(TAG, "download apk filePath: "+s);
                   File apkFile = new File(s);
                   File newFile = new File(apkFile.getParent() + File.separator + versionInfo.getInstallFileName());
                   apkFile.renameTo(newFile);
                   installAPK(context, newFile);
               }

               @Override
               public void onFailure(@NotNull String s) {
                   Log.i(TAG, "download apk fail: "+s);
               }
           });
       }else {
           installAPK(context, file);
       }
    }


    private static IRequest<VersionInfo> getVersionInfoRequest(Context context) {
        //String packageName = "Y29tLnR0Lm1hcHA=";
        String packageName = Base64.encodeToString(CommonUtil.getPackageName(context).getBytes(), Base64.DEFAULT);
        String url = String.format("%s/comment/apiv2/softwarenewest/Android/%s", CommonUtil.getBaseUrl(), packageName);
        IRequest request = new NetworkRequest<VersionInfo>(context)
                .url(url)
                .method(RequestParam.Method.GET)
                .dataClass(VersionInfo.class)
                .dataParser(new ListDataParser<VersionInfo>());
        return  request;
    }

    private static IRequest<String> getDownloadApkRequest(Context context, VersionInfo versionInfo) {
        IRequest request = new NetworkRequest<String>(context)
                .url(versionInfo.getLink())
                .method(RequestParam.Method.GET)
                .downloadFilePath(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/")
                .downloadFileName(versionInfo.getInstallFileName()+".bak");
        return request;
    }

    private static void installAPK(Context context, File apkFile){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            //第二个参数需要与<provider>标签中的android:authorities属性相同
            uri = FileProvider.getUriForFile(context,"com.chris.tiantian.fileprovider222",apkFile);
        }else{
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            uri = Uri.fromFile(apkFile);
        }
        intent.setDataAndType(uri ,"application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    private static void showVersionInfoDialog(Context context, VersionInfo lastVersionInfo, DialogInterface.OnClickListener listener) {
        new AlertDialog.Builder(context)
                .setTitle("版本"+lastVersionInfo.getVersion())
                .setMessage(lastVersionInfo.getChanges().replaceAll("\\\\n", "\n"))
                .setCancelable(false)
                .setPositiveButton("确认", listener)
                .setNegativeButton("取消", null)
                .show();
    }

    /**
     * 1.将版本号按点分割，并转成数字类型，放入list
     * 2.取两个版本位数的最大数，如：1.0.1为3位 1.0.0.1为4位
     * 3.将位数不够的版本进行补全，不够部分补成0
     * 4.从第一位开始比较，出现大于情况返回1，出现小于情况返回-1，后面的就不用再比较了，如果没有出现大于和小于的情况，那只剩下等于了，for循环走完，返回0
     * @param versionName1
     * @param versionName2
     * @return
     */
    private static int compareVersionName(String versionName1, String versionName2) {
        if(TextUtils.isEmpty(versionName1) || TextUtils.isEmpty(versionName2)) {
            throw new RuntimeException("版本号不能都为空");
        }

        if(versionName1.equals(versionName2)) {
            return 0;
        }
        String[] version1Array = versionName1.split("\\.");
        String[] version2Array = versionName2.split("\\.");
        List<Integer> version1List = new ArrayList<Integer>();
        List<Integer> version2List = new ArrayList<Integer>();
        for (int i = 0; i < version1Array.length; i++) {
            version1List.add(Integer.parseInt(version1Array[i]));
        }
        for (int i = 0; i < version2Array.length; i++) {
            version2List.add(Integer.parseInt(version2Array[i]));
        }
        int size = version1List.size() > version2List.size() ? version1List.size() : version2List.size();
        while (version1List.size() < size) {
            version1List.add(0);
        }
        while (version2List.size() < size) {
            version2List.add(0);
        }
        for (int i = 0; i < size; i++) {
            if (version1List.get(i) > version2List.get(i)) {
                return 1;
            }
            if (version1List.get(i) < version2List.get(i)) {
                return -1;
            }
        }
        return 0;
    }
}
