package com.jess.arms.update;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

/**
 * @author IurKwan
 * @date 12/14/20
 */
public class DownloadManager {

    private static SoftReference<Context> context;

    private String apkUrl = "";

    private String apkName = "";

    private String downloadPath;

    private int smallIcon = -1;

    private List<UpdateDownloadListener> onDownloadListeners = null;

    private boolean downloading = false;

    private static DownloadManager manager;

    public static DownloadManager getInstance(Context context) {
        DownloadManager.context = new SoftReference<>(context);
        if (manager == null) {
            synchronized (DownloadManager.class) {
                if (manager == null) {
                    manager = new DownloadManager();
                }
            }
        }
        return manager;
    }

    public static DownloadManager getInstance() {
        return manager;
    }

    public void start(){
        if (!checkParams()){
            //参数设置出错....
            return;
        }
        // 启动服务下载
        context.get().startService(new Intent(context.get(),UpdateService.class));
    }

    private boolean checkParams(){
        if (TextUtils.isEmpty(apkUrl)){
            return false;
        }
        if (TextUtils.isEmpty(apkName)) {
            return false;
        }
        if (!apkName.endsWith(".apk")) {
            return false;
        }
        downloadPath = context.get().getExternalCacheDir().getPath();
        if (smallIcon == -1) {
            return false;
        }
        return true;
    }

    public DownloadManager setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl;
        return this;
    }

    public String getApkUrl() {
        return apkUrl;
    }

    public DownloadManager setApkName(String apkName) {
        this.apkName = apkName;
        return this;
    }

    public String getApkName() {
        return apkName;
    }

    public DownloadManager setSmallIcon(int smallIcon) {
        this.smallIcon = smallIcon;
        return this;
    }

    public int getSmallIcon() {
        return smallIcon;
    }

    public String getDownloadPath() {
        return downloadPath;
    }

    public boolean isDownloading() {
        return downloading;
    }

    public void setDownloading(boolean downloading) {
        this.downloading = downloading;
    }

    public DownloadManager addOnDownloadListeners(UpdateDownloadListener listener) {
        if (onDownloadListeners == null){
            onDownloadListeners = new ArrayList<>();
        }
        onDownloadListeners.add(listener);
        return this;
    }

    public List<UpdateDownloadListener> getOnDownloadListeners() {
        return onDownloadListeners;
    }

    public void cancel() {

    }

    public void release() {
        context.clear();
        context = null;
        manager = null;
        if (onDownloadListeners != null) {
            onDownloadListeners.clear();
        }
    }
}
