package com.chris.tiantian.base.service;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.anima.networkrequest.DataListCallback;
import com.anima.networkrequest.DataObjectCallback;
import com.anima.networkrequest.IRequest;
import com.anima.networkrequest.NetworkRequest;
import com.anima.networkrequest.entity.RequestParam;
import com.chris.tiantian.entity.NetworkDataParser;
import com.chris.tiantian.entity.VersionInfo;
import com.chris.tiantian.util.CommonUtil;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Collections;
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
                    Collections.sort(list);
                    String currentVersion = CommonUtil.getVersionName(context);
                    VersionInfo lastVersionInfo = list.get(0);
                    listener.onNewVersion(currentVersion.compareTo(lastVersionInfo.getVersion()) < 0);
                }else {
                    Log.i(TAG, "no version update");
                    listener.onNewVersion(false);
                }
            }
        });
    }

    public static void checkUpdateInBackground(Context context) {
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
    }

    public static void checkUpdate(Context context) {
        getVersionInfoRequest(context).loadingMessage("正在检查更新...").getList(new DataListCallback<VersionInfo>() {
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
                   installAPK(context, new File(s));
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
                .dataParser(new NetworkDataParser<VersionInfo>());
        return  request;
    }

    private static IRequest<String> getDownloadApkRequest(Context context, VersionInfo versionInfo) {
        IRequest request = new NetworkRequest<String>(context)
                .url(versionInfo.getLink())
                .method(RequestParam.Method.GET)
                .downloadFilePath(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/")
                .downloadFileName(versionInfo.getInstallFileName());
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
}
