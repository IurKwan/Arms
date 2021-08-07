package com.jess.arms.update;

import java.io.File;

/**
 * 下载不同状态接口回调
 *
 * @author IurKwan
 * @date 12/11/20
 */
public interface UpdateDownloadListener {

    /**
     * 开始下载
     */
    void start();

    /**
     * 下载中
     *
     * @param max      总进度
     * @param progress 当前进度
     */
    void downloading(int max, int progress);

    /**
     * 下载完成
     *
     * @param apk 下载好的apk
     */
    void done(File apk);

    /**
     * 取消下载
     */
    void cancel();

    /**
     * 下载出错
     *
     * @param e 错误信息
     */
    void error(Exception e);

}
