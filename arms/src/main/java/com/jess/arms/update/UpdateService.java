package com.jess.arms.update;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;

import com.jess.arms.utils.ArmsUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author IurKwan
 * @date 12/11/20
 */
public class UpdateService extends Service implements UpdateDownloadListener {

    private int smallIcon;

    private String apkUrl;

    private String apkName;

    private String downloadPath;

    private int lastProgress;

    private DownloadManager mDownloadManager;

    private List<UpdateDownloadListener> mDownloadListeners;

    private HttpDownloadManager mUpdateManager;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (null == intent) {
            return START_STICKY;
        }
        init();
        return super.onStartCommand(intent, flags, startId);
    }

    private void init() {
        mDownloadManager = DownloadManager.getInstance();
        if (mDownloadManager == null) {
            return;
        }

        apkUrl = mDownloadManager.getApkUrl();
        apkName = mDownloadManager.getApkName();
        downloadPath = mDownloadManager.getDownloadPath();
        smallIcon = mDownloadManager.getSmallIcon();
        mDownloadListeners = mDownloadManager.getOnDownloadListeners();

        // 检查文件夹是否存在
        boolean exists = true;
        File dir = new File(downloadPath);
        if (!dir.exists()) {
            exists = dir.mkdirs();
        }
        if (!exists) {
            return;
        }

        // 判断文件是否存在
        // 存在则删除，重新下载
        File apk = new File(downloadPath, apkName);
        boolean fileExists = apk.exists();
        boolean canDownload = true;
        if (fileExists) {
            canDownload = apk.delete();
        }
        if (canDownload) {
            startDownload();
        }
    }

    private synchronized void startDownload() {
        if (mDownloadManager.isDownloading()) {
            return;
        }
        mUpdateManager = new HttpDownloadManager(downloadPath);
        mUpdateManager.download(UpdateService.this, apkUrl, apkName, this);
        mDownloadManager.setDownloading(true);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 0:
                    Toast.makeText(UpdateService.this,"正在后台下载新版本...",Toast.LENGTH_LONG).show();
                    break;
                case 1:
                    if (mDownloadListeners != null) {
                        for (UpdateDownloadListener listener : mDownloadListeners) {
                            listener.start();
                        }
                    }
                    break;
                case 2:
                    if (mDownloadListeners != null) {
                        for (UpdateDownloadListener listener : mDownloadListeners) {
                            listener.downloading(msg.arg1, msg.arg2);
                        }
                    }
                    break;
                case 3:
                    if (mDownloadListeners != null) {
                        for (UpdateDownloadListener listener : mDownloadListeners) {
                            listener.done((File) msg.obj);
                        }
                    }
                    //执行了完成开始释放资源
                    releaseResources();
                    break;
                case 4:
                    if (mDownloadListeners != null) {
                        for (UpdateDownloadListener listener : mDownloadListeners) {
                            listener.cancel();
                        }
                    }
                    break;
                case 5:
                    if (mDownloadListeners != null) {
                        for (UpdateDownloadListener listener : mDownloadListeners) {
                            listener.error((Exception) msg.obj);
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    };

    private void releaseResources() {
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
        if (mUpdateManager != null) {
            mUpdateManager.release();
        }
        stopSelf();
        mDownloadManager.release();
    }

    @Override
    public void start() {
        // 显示通知栏通知
        UpdateNotificationUtil.showStartNotification(this, smallIcon, "开始下载", "可稍后查看下载进度");

        mHandler.sendEmptyMessage(1);
    }

    @Override
    public void downloading(int max, int progress) {
        //优化通知栏更新，减少通知栏更新次数
        int curr = (int) (progress / (double) max * 100.0);
        if (curr != lastProgress) {
            lastProgress = curr;
            String content = curr < 0 ? "" : curr + "%";
            UpdateNotificationUtil.showProgressNotification(this, smallIcon, "正在下载新版本",
                    content, max == -1 ? -1 : 100, curr);
        }
        mHandler.obtainMessage(2, max, progress).sendToTarget();
    }

    @Override
    public void done(File apk) {
        mDownloadManager.setDownloading(false);
        //如果是android Q（api=29）及其以上版本showNotification=false也会发送一个下载完成通知
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            String downloadCompleted = "下载完成";
            String clickHint = "点击进行安装";
            UpdateNotificationUtil.showDoneNotification(this, smallIcon, downloadCompleted,
                    clickHint, getApplicationContext().getPackageName() + ".fileProvider", apk);
        }
        installApk(this, getApplicationContext().getPackageName() + ".fileProvider", apk);
        //如果用户设置了回调 则先处理用户的事件 在执行自己的
        mHandler.obtainMessage(3, apk).sendToTarget();
    }

    @Override
    public void cancel() {
        mDownloadManager.setDownloading(false);
        UpdateNotificationUtil.cancelNotification(this);
        mHandler.sendEmptyMessage(4);
    }

    @Override
    public void error(Exception e) {
        mDownloadManager.setDownloading(false);
        String downloadError = "下载出错";
        String conDownloading = "点击继续下载";
        UpdateNotificationUtil.showErrorNotification(this, smallIcon, downloadError, conDownloading);
        mHandler.obtainMessage(5, e).sendToTarget();
    }

    public static void installApk(Context context, String authorities, File apk) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setAction(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(context, authorities, apk);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(apk);
        }
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        context.startActivity(intent);
    }
}
