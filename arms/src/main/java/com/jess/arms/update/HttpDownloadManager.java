package com.jess.arms.update;

import android.content.Context;

import com.jess.arms.utils.ArmsUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

/**
 * @author IurKwan
 * @date 12/14/20
 */
public class HttpDownloadManager {

    public static final int HTTP_TIME_OUT = 30_000;
    private boolean shutdown = false;
    private String apkUrl;
    private String apkName;
    private String downloadPath;
    private UpdateDownloadListener listener;

    public HttpDownloadManager(String downloadPath) {
        this.downloadPath = downloadPath;
    }

    public void download(Context context,String apkUrl, String apkName, UpdateDownloadListener listener){
        this.apkUrl = apkUrl;
        this.apkName = apkName;
        this.listener = listener;

        ArmsUtils.obtainAppComponentFromContext(context)
                .executorService()
                .execute(mRunnable);
    }

    public void cancel(){
        shutdown = true;
    }

    public void release(){
        listener = null;
    }

    private void startDownload(){
        listener.start();
        try {
            URL url = new URL(apkUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setReadTimeout(HTTP_TIME_OUT);
            con.setConnectTimeout(HTTP_TIME_OUT);
            con.setRequestProperty("Accept-Encoding", "identity");
            if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream is = con.getInputStream();
                int length = con.getContentLength();
                int len;
                //当前已下载完成的进度
                int progress = 0;
                byte[] buffer = new byte[1024 * 2];
                File file = new File(downloadPath, apkName);
                FileOutputStream stream = new FileOutputStream(file);
                while ((len = is.read(buffer)) != -1 && !shutdown) {
                    //将获取到的流写入文件中
                    stream.write(buffer, 0, len);
                    progress += len;
                    listener.downloading(length, progress);
                }
                if (shutdown) {
                    //取消了下载 同时再恢复状态
                    shutdown = false;
                    listener.cancel();
                } else {
                    listener.done(file);
                }
                //完成io操作,释放资源
                stream.flush();
                stream.close();
                is.close();
                //重定向
            } else if (con.getResponseCode() == HttpURLConnection.HTTP_MOVED_PERM ||
                    con.getResponseCode() == HttpURLConnection.HTTP_MOVED_TEMP) {
                apkUrl = con.getHeaderField("Location");
                con.disconnect();
                startDownload();
            } else {
                listener.error(new SocketTimeoutException("下载失败：Http ResponseCode = " + con.getResponseCode()));
            }
            con.disconnect();
        } catch (Exception e) {
            listener.error(e);
            e.printStackTrace();
        }
    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            startDownload();
        }
    };

}
