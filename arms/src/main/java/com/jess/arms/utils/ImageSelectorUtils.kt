package com.jess.arms.utils

import android.app.Activity
import android.content.Context
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.jess.arms.R
import com.luck.picture.lib.animators.AnimationType
import com.luck.picture.lib.basic.PictureSelectionModel
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.config.PictureSelectionConfig.selectorStyle
import com.luck.picture.lib.engine.ImageEngine
import com.luck.picture.lib.engine.UriToFileTransformEngine
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnKeyValueResultCallbackListener
import com.luck.picture.lib.style.PictureSelectorStyle
import com.luck.picture.lib.style.SelectMainStyle
import com.luck.picture.lib.style.TitleBarStyle
import com.luck.picture.lib.utils.SandboxTransformUtils
import com.luck.picture.lib.utils.StyleUtils
import com.yalantis.ucrop.UCrop

/**
 * 选择图片库
 * 使用单例静态内部类模式
 * @author IurKwan
 * @date 1/19/21
 */
object ImageSelectorUtils {

    private var mEngine: ImageEngine? = null
    private var mTheme: PictureSelectorStyle = PictureSelectorStyle()

    fun init(theme: PictureSelectorStyle, engine: ImageEngine) {
        mTheme = theme
        mEngine = engine
    }

    fun defaultModel(model: PictureSelectionModel): PictureSelectionModel {
        return model.setImageEngine(mEngine)
            .setSelectorUIStyle(mTheme)
            .setCompressEngine(ImageFileCompressEngine())
            .setSandboxFileEngine(SandboxFileEngine())
            .setRecyclerAnimationMode(AnimationType.DEFAULT_ANIMATION)
            .isDisplayCamera(true)
            .isPageStrategy(true)
            .isGif(true)
            .isOpenClickSound(false)
            .isPreviewAudio(false)
            .isPreviewImage(true)
            .isPreviewVideo(true)
            .isPreviewFullScreenMode(true)
            .isEmptyResultReturn(true)
            .isWithSelectVideoImage(false)
            .isSelectZoomAnim(true)
            .isCameraAroundState(true)
            .isCameraRotateImage(true)
            .isWebp(true)
            .isBmp(true)
            .isMaxSelectEnabledMask(true)
            .isAutomaticTitleRecyclerTop(true)
            .isFastSlidingSelect(true)
            .isDirectReturnSingle(true)
            .setSkipCropMimeType(PictureMimeType.ofGIF(), PictureMimeType.ofWEBP())
    }

    fun getFinalPath(media: LocalMedia): String {
        if (media.isCompressed) {
            return media.compressPath
        }
        if (media.isCut) {
            return media.cutPath
        }
        return media.availablePath
    }

    fun openExternalPreview(fragment: Fragment, position: Int, media: ArrayList<LocalMedia>) {
        PictureSelector.create(fragment)
            .openPreview()
            .setImageEngine(mEngine)
            .setSelectorUIStyle(mTheme)
            .isPreviewFullScreenMode(false)
            .startActivityPreview(position, false, media)
    }

    fun openExternalPreview(activity: Activity, position: Int, media: ArrayList<LocalMedia>) {
        PictureSelector.create(activity)
            .openPreview()
            .setImageEngine(mEngine)
            .setSelectorUIStyle(mTheme)
            .isPreviewFullScreenMode(false)
            .startActivityPreview(position, false, media)
    }

    fun buildOptions(context: Context): UCrop.Options {
        val options = UCrop.Options()
        options.setHideBottomControls(false)
        options.setFreeStyleCropEnabled(false)
        options.setShowCropFrame(true)
        options.setShowCropGrid(true)
        options.setCircleDimmedLayer(true)
        options.withAspectRatio(-1f, -1f)
        options.isCropDragSmoothToCenter(false)
        options.setSkipCropMimeType(PictureMimeType.ofGIF(), PictureMimeType.ofWEBP())
        options.isForbidCropGifWebp(true)
        options.isForbidSkipMultipleCrop(false)
        options.setMaxScaleMultiplier(100f)
        if (mTheme.selectMainStyle.statusBarColor != 0) {
            val mainStyle: SelectMainStyle = selectorStyle.selectMainStyle
            val isDarkStatusBarBlack = mainStyle.isDarkStatusBarBlack
            val statusBarColor = mainStyle.statusBarColor
            options.isDarkStatusBarBlack(isDarkStatusBarBlack)
            if (StyleUtils.checkStyleValidity(statusBarColor)) {
                options.setStatusBarColor(statusBarColor)
                options.setToolbarColor(statusBarColor)
            } else {
                options.setStatusBarColor(
                    ContextCompat.getColor(
                        context,
                        R.color.ps_color_grey
                    )
                )
                options.setToolbarColor(ContextCompat.getColor(context, R.color.ps_color_grey))
            }
            val titleBarStyle: TitleBarStyle = selectorStyle.titleBarStyle
            if (StyleUtils.checkStyleValidity(titleBarStyle.titleTextColor)) {
                options.setToolbarWidgetColor(titleBarStyle.titleTextColor)
            } else {
                options.setToolbarWidgetColor(
                    ContextCompat.getColor(
                        context,
                        R.color.ps_color_white
                    )
                )
            }
        } else {
            options.setStatusBarColor(ContextCompat.getColor(context, R.color.ps_color_grey))
            options.setToolbarColor(ContextCompat.getColor(context, R.color.ps_color_grey))
            options.setToolbarWidgetColor(
                ContextCompat.getColor(
                    context,
                    R.color.ps_color_white
                )
            )
        }
        return options
    }

    class SandboxFileEngine : UriToFileTransformEngine {
        override fun onUriToFileAsyncTransform(
            context: Context,
            srcPath: String,
            mineType: String,
            call: OnKeyValueResultCallbackListener
        ) {
            call.onCallback(
                srcPath,
                SandboxTransformUtils.copyPathToSandbox(context, srcPath, mineType)
            )
        }
    }
}
