package com.chris.tiantian.util;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import static java.lang.Thread.sleep;

/**
 * Created by jianjianhong on 19-9-29
 */
public class LocationLog {
    public static final String FILE_DIR = "Download/tiantian_info";

    private static LocationLog instance = new LocationLog();
    private LocationLog() {}

    public static LocationLog getInstance() {
        return instance;
    }

    private String fileName;
    private FileOutputStream fos = null;
    private boolean running = true;
    private volatile StringBuffer buffer = new StringBuffer();

    public void start() {
        File dir = getAlbumStorageDir();
        fileName = String.format("%s.txt", DateUtil.formatDate(new Date(), "yyyy-MM-dd"));
        String filePath = dir.getAbsolutePath()+ File.separator+fileName;
        File logFile = new File(filePath);
        createNewFile(logFile);
        try {
            fos =  new FileOutputStream(filePath, true);// 获得文件输出流
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (running) {
                        if(!TextUtils.isEmpty(buffer.toString())) {
                            try {
                                fos.write(buffer.toString().getBytes());
                                fos.flush();// 清除缓存
                                buffer.setLength(0);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        try {
                            sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }).start();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        running = false;
        if (fos != null) {
            try {
                fos.close();// 关闭文件输出流
                fos = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void i(String log) {
        String dateStr = DateUtil.formatDate(new Date(), "yyyy-MM-dd");
        if(TextUtils.isEmpty(fileName) || !fileName.contains(dateStr) || fos == null) {
            close();
            start();
            running = true;
        }
        Log.i("LocationLog", log);
        setLog(log);
    }

    private synchronized void setLog(String log) {
        buffer.append(DateUtil.getTodayTime());
        buffer.append(" ");
        buffer.append(log);
        buffer.append("\n");
    }

    public static File getAlbumStorageDir() {
        // Get the directory for the user's public pictures directory.
        /*File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), FILE_DIR);*/
        File file = new File(Environment.getExternalStorageDirectory(), FILE_DIR);
        if (!file.mkdirs()) {

        }
        return file;
    }

    public static void createNewFile(File logFile) {

        if(!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
