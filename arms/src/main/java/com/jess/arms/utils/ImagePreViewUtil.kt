package com.jess.arms.utils

import android.content.Context
import cc.shinichi.library.ImagePreview
import com.jess.arms.R

/**
 * 图片浏览器
 * 使用单例静态内部类模式
 * @author IurKwan
 * @date 2/22/21
 */
class ImagePreViewUtil private constructor() {

    companion object {
        val instance = ImagePreViewUtil.holder
    }

    private object ImagePreViewUtil {
        val holder = ImagePreViewUtil()
    }

    fun show(
        context: Context,
        index: Int,
        list: List<String>
    ): ImagePreview {
        return ImagePreview.getInstance()
            // 上下文，必须是activity，不需要担心内存泄漏，本框架已经处理好
            .setContext(context)
            // 从第几张图片开始，索引从0开始哦~
            .setIndex(index)
            // 直接传url List
            .setImageList(list)
            // 加载策略，默认为手动模式
            .setLoadStrategy(ImagePreview.LoadStrategy.Default)
            // 保存的文件夹名称，会在Picture目录进行文件夹的新建。比如："BigImageView"，会在Picture目录新建BigImageView文件夹)
            //.setFolderName(context.getResources().getString(R.string.app_name)) // 缩放动画时长，单位ms
            .setZoomTransitionDuration(300)
            // 是否显示加载失败的Toast
            .setShowErrorToast(true)
            // 是否启用点击图片关闭。默认启用
            .setEnableClickClose(true)
            // 是否启用下拉关闭。默认不启用
            .setEnableDragClose(true)
            // 是否启用上拉关闭。默认不启用
            .setEnableUpDragClose(false)
            // 是否显示关闭页面按钮，在页面左下角。默认不显示
            .setShowCloseButton(false)
            // 设置关闭按钮图片资源，可不填，默认为库中自带：R.drawable.ic_action_close
            // setCloseIconResId(R.drawable.ic_action_close)
            // 是否显示下载按钮，在页面右下角。默认显示
            .setShowDownButton(false)
            // 设置下载按钮图片资源，可不填，默认为库中自带：R.drawable.icon_download_new
            //.setDownIconResId(R.drawable.icon_download_new)
            // 设置是否显示顶部的指示器（1/9）默认显示
            .setShowIndicator(true)
            // 设置顶部指示器背景shape，默认自带灰色圆角shape
            .setIndicatorShapeResId(R.drawable.shape_indicator_bg)
            // 设置失败时的占位图，默认为库中自带R.drawable.load_failed，设置为 0 时不显示
            .setErrorPlaceHolder(R.drawable.load_failed)
        // 点击回调
        // 设置查看原图时的百分比样式：库中带有一个样式：ImagePreview.PROGRESS_THEME_CIRCLE_TEXT，使用如下：
        // .setProgressLayoutId(ImagePreview.PROGRESS_THEME_CIRCLE_TEXT, object : OnOriginProgressListener {
        //       override fun progress(parentView: View, progress: Int) {
        //               // 需要找到进度控件并设置百分比，回调中的parentView即传入的布局的根View，可通过parentView找到控件：
        //               val progressBar = parentView.findViewById<ProgressBar>(R.id.sh_progress_view)
        //               val textView = parentView.findViewById<TextView>(R.id.sh_progress_text)
        //               progressBar.progress = progress
        //               val progressText = "$progress%"
        //                textView.text = progressText
        //        }
        //
        //         override fun finish(parentView: View) {}
        //         })
    }

    fun show(
        context: Context,
        index: Int,
        url: String
    ): ImagePreview {
        return ImagePreview.getInstance()
            // 上下文，必须是activity，不需要担心内存泄漏，本框架已经处理好
            .setContext(context)
            // 从第几张图片开始，索引从0开始哦~
            .setIndex(index)
            // 直接传url
            .setImage(url)
            // 加载策略，默认为手动模式
            .setLoadStrategy(ImagePreview.LoadStrategy.Default)
            // 保存的文件夹名称，会在Picture目录进行文件夹的新建。比如："BigImageView"，会在Picture目录新建BigImageView文件夹)
            //.setFolderName(context.getResources().getString(R.string.app_name)) // 缩放动画时长，单位ms
            .setZoomTransitionDuration(300)
            // 是否显示加载失败的Toast
            .setShowErrorToast(true)
            // 是否启用点击图片关闭。默认启用
            .setEnableClickClose(true)
            // 是否启用下拉关闭。默认不启用
            .setEnableDragClose(true)
            // 是否启用上拉关闭。默认不启用
            .setEnableUpDragClose(false)
            // 是否显示关闭页面按钮，在页面左下角。默认不显示
            .setShowCloseButton(false)
            // 设置关闭按钮图片资源，可不填，默认为库中自带：R.drawable.ic_action_close
            // setCloseIconResId(R.drawable.ic_action_close)
            // 是否显示下载按钮，在页面右下角。默认显示
            .setShowDownButton(false)
            // 设置下载按钮图片资源，可不填，默认为库中自带：R.drawable.icon_download_new
            //.setDownIconResId(R.drawable.icon_download_new)
            // 设置是否显示顶部的指示器（1/9）默认显示
            .setShowIndicator(true)
            // 设置顶部指示器背景shape，默认自带灰色圆角shape
            .setIndicatorShapeResId(R.drawable.shape_indicator_bg)
            // 设置失败时的占位图，默认为库中自带R.drawable.load_failed，设置为 0 时不显示
            .setErrorPlaceHolder(R.drawable.load_failed)
        // 点击回调
        // 设置查看原图时的百分比样式：库中带有一个样式：ImagePreview.PROGRESS_THEME_CIRCLE_TEXT，使用如下：
        // .setProgressLayoutId(ImagePreview.PROGRESS_THEME_CIRCLE_TEXT, object : OnOriginProgressListener {
        //       override fun progress(parentView: View, progress: Int) {
        //               // 需要找到进度控件并设置百分比，回调中的parentView即传入的布局的根View，可通过parentView找到控件：
        //               val progressBar = parentView.findViewById<ProgressBar>(R.id.sh_progress_view)
        //               val textView = parentView.findViewById<TextView>(R.id.sh_progress_text)
        //               progressBar.progress = progress
        //               val progressText = "$progress%"
        //                textView.text = progressText
        //        }
        //
        //         override fun finish(parentView: View) {}
        //         })
    }
}