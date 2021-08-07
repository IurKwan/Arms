package com.jess.arms.update;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.content.FileProvider;

import java.io.File;

/**
 * 单独的更新通知工具类
 *
 * @author IurKwan
 * @date 12/11/20
 */
public final class UpdateNotificationUtil {

    /**
     * 通知栏id
     */
    private static final int notifyId = 1011;

    /**
     * 渠道通知id
     */
    public static final String DEFAULT_CHANNEL_ID = "appUpdate";
    /**
     * 渠道通知名称
     */
    public static final String DEFAULT_CHANNEL_NAME = "AppUpdate";

    public static void showStartNotification(Context context, int icon, String title, String content) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel(manager);
        }
        NotificationCompat.Builder builder = builderNotification(context, icon, title, content).setDefaults(Notification.DEFAULT_SOUND);
        manager.notify(notifyId, builder.build());
    }

    public static void showProgressNotification(Context context, int icon, String title, String content,
                                                int max, int progress) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager == null) {
            return;
        }
        NotificationCompat.Builder builder = builderNotification(context, icon, title, content)
                //indeterminate:true表示不确定进度，false表示确定进度
                //当下载进度没有获取到content-length时，使用不确定进度条
                .setProgress(max, progress, max == -1);
        manager.notify(notifyId, builder.build());
    }

    public static void showDoneNotification(Context context, int icon, String title, String content,
                                            String authorities, File apk) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager == null){
            return;
        }
        //不知道为什么需要先取消之前的进度通知，才能显示完成的通知。
        manager.cancel(notifyId);
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
        PendingIntent pi = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Builder builder = builderNotification(context, icon, title, content)
                .setContentIntent(pi);
        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        manager.notify(notifyId, notification);
    }

    public static void showErrorNotification(Context context, int icon, String title, String content) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel(manager);
        }
        Intent intent = new Intent(context, UpdateService.class);
        PendingIntent pi = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = builderNotification(context, icon, title, content)
                .setAutoCancel(true)
                .setOngoing(false)
                .setContentIntent(pi)
                .setDefaults(Notification.DEFAULT_SOUND);
        manager.notify(notifyId, builder.build());
    }

    private static NotificationCompat.Builder builderNotification(Context context, int icon, String title, String content) {
        String channelId = "";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            channelId = DEFAULT_CHANNEL_ID;
        }
        return new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(icon)
                .setContentTitle(title)
                .setWhen(System.currentTimeMillis())
                .setContentText(content)
                //不能删除
                .setAutoCancel(false)
                //正在交互（如播放音乐）
                .setOngoing(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static void createChannel(NotificationManager manager) {
        NotificationChannel channel = new NotificationChannel(
                DEFAULT_CHANNEL_ID,
                DEFAULT_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_LOW
        );
        // 是否在桌面icon右上角展示小圆点
        channel.enableLights(true);
        //是否在久按桌面图标时显示此渠道的通知
        channel.setShowBadge(true);
        manager.createNotificationChannel(channel);
    }

    public static void cancelNotification(Context context) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager == null){
            return;
        }
        manager.cancel(notifyId);
    }


}
