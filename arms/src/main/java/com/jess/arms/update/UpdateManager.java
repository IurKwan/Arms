package com.jess.arms.update;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import java.lang.ref.SoftReference;

/**
 * @author IurKwan
 * @date 12/11/20
 */
public class UpdateManager {

    private static SoftReference<Context> context;

    private String apkUrl = "";

    private String apkName = "";

    private String downloadPath;

    private int smallIcon = -1;

    private static UpdateManager manager;

    public static UpdateManager getInstance(Context context) {
        UpdateManager.context = new SoftReference<>(context);
        if (manager == null) {
            synchronized (UpdateManager.class) {
                if (manager == null) {
                    manager = new UpdateManager();
                }
            }
        }
        return manager;
    }

    public static UpdateManager getInstance() {
        return manager;
    }

    public UpdateManager setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl;
        return this;
    }

    public void start() {
        if (!checkParams()) {
            //参数设置出错....
            return;
        }
        // 启动服务下载
        context.get().startService(new Intent(context.get(), UpdateService.class));
    }

    private boolean checkParams() {
        if (TextUtils.isEmpty(apkUrl)) {
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

    public String getApkUrl() {
        return apkUrl;
    }

    public String getApkName() {
        return apkName;
    }

    public int getSmallIcon() {
        return smallIcon;
    }

    public String getDownloadPath() {
        return downloadPath;
    }

}
