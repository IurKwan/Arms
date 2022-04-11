package com.jess.arms.utils

import android.app.Activity
import android.content.Context
import android.net.Uri
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.jess.arms.R
import com.luck.picture.lib.animators.AnimationType
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.config.PictureSelectionConfig.selectorStyle
import com.luck.picture.lib.config.SelectMimeType
import com.luck.picture.lib.config.SelectModeConfig
import com.luck.picture.lib.engine.CompressFileEngine
import com.luck.picture.lib.engine.CropFileEngine
import com.luck.picture.lib.engine.ImageEngine
import com.luck.picture.lib.engine.UriToFileTransformEngine
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnKeyValueResultCallbackListener
import com.luck.picture.lib.interfaces.OnResultCallbackListener
import com.luck.picture.lib.style.PictureSelectorStyle
import com.luck.picture.lib.style.SelectMainStyle
import com.luck.picture.lib.style.TitleBarStyle
import com.luck.picture.lib.utils.DateUtils
import com.luck.picture.lib.utils.SandboxTransformUtils
import com.luck.picture.lib.utils.StyleUtils
import com.yalantis.ucrop.UCrop
import top.zibin.luban.Luban
import top.zibin.luban.OnNewCompressListener
import java.io.File

/**
 * 选择图片库
 * 使用单例静态内部类模式
 * @author IurKwan
 * @date 1/19/21
 */
class ImageSelectorUtils private constructor() {

    companion object {
        val instance = ImageSelectorUtils.holder
    }

    private object ImageSelectorUtils {
        val holder = ImageSelectorUtils()
    }

    private var mEngine: ImageEngine? = null
    private var mCutEngine: CropFileEngine? = null

    private var mTheme: PictureSelectorStyle = PictureSelectorStyle()

    fun init(theme: PictureSelectorStyle, engine: ImageEngine, cutEngine: CropFileEngine) {
        mTheme = theme
        mEngine = engine
        mCutEngine = cutEngine
    }

    fun getEngine(): ImageEngine {
        return mEngine!!
    }

    fun getTheme(): PictureSelectorStyle {
        return mTheme
    }

    fun takeOnePhoto(
        fragment: Fragment,
        isCut: Boolean,
        listener: OnResultCallbackListener<LocalMedia>
    ) {
        checkTheme()

        PictureSelector.create(fragment)
            .openGallery(SelectMimeType.ofImage())
            .setSelectionMode(SelectModeConfig.SINGLE)
            .setSelectedData(null)
            .setMaxSelectNum(9)
            .setMinSelectNum(1)
            .setMaxVideoSelectNum(9)
            .setMinVideoSelectNum(1)
            .setRecordVideoMaxSecond(15)
            .setRecordVideoMinSecond(3)

            .setImageEngine(mEngine)
            .setSelectorUIStyle(mTheme)
            .setImageSpanCount(3)
            .setCompressEngine(ImageFileCompressEngine())
            .setCropEngine(mCutEngine)
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
            .forResult(listener)
    }

    fun starCamera(fragment: Fragment, listener: OnResultCallbackListener<LocalMedia>) {
        checkTheme()

        PictureSelector.create(fragment)
            .openCamera(SelectMimeType.ofImage())
            .setCropEngine(mCutEngine)
            .setCompressEngine(ImageFileCompressEngine())
            .forResult(listener)
    }

    fun startPictureSelected(
        fragment: Fragment,
        mLocalMedia: List<LocalMedia>,
        max: Int,
        isPhoto: Boolean,
        listener: OnResultCallbackListener<LocalMedia>
    ) {
        checkTheme()

        PictureSelector.create(fragment)
            .openGallery(if (isPhoto) SelectMimeType.ofImage() else SelectMimeType.ofVideo())
            .setSelectionMode(SelectModeConfig.SINGLE)
            .setSelectedData(mLocalMedia)
            .setMaxSelectNum(if (max == 0) 1 else max)
            .setMinSelectNum(1)
            .setMaxVideoSelectNum(999)
            .setMinVideoSelectNum(1)
            .setRecordVideoMaxSecond(15)
            .setRecordVideoMinSecond(1)

            .setImageEngine(mEngine)
            .setSelectorUIStyle(mTheme)
            .setImageSpanCount(3)
            .setCompressEngine(ImageFileCompressEngine())
            .setCropEngine(mCutEngine)
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
            .forResult(listener)
    }

    fun startPictureSelected(
        fragment: Fragment,
        mLocalMedia: List<LocalMedia>,
        limitSize: Boolean,
        isPhoto: Boolean,
        listener: OnResultCallbackListener<LocalMedia>
    ) {
        checkTheme()

        PictureSelector.create(fragment)
            .openGallery(if (isPhoto) SelectMimeType.ofImage() else SelectMimeType.ofVideo())
            .setSelectionMode(SelectModeConfig.SINGLE)
            .setSelectedData(mLocalMedia)
            .setMaxSelectNum(if (limitSize) 9 else 999)
            .setMinSelectNum(1)
            .setMaxVideoSelectNum(999)
            .setMinVideoSelectNum(1)
            .setRecordVideoMaxSecond(15)
            .setRecordVideoMinSecond(1)

            .setImageEngine(mEngine)
            .setSelectorUIStyle(mTheme)
            .setImageSpanCount(3)
            .setCompressEngine(ImageFileCompressEngine())
            .setCropEngine(mCutEngine)
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
            .forResult(listener)
    }

    fun startPictureSelected(
        activity: Activity,
        mLocalMedia: List<LocalMedia>,
        limitSize: Boolean,
        isPhoto: Boolean,
        listener: OnResultCallbackListener<LocalMedia>
    ) {
        checkTheme()

        PictureSelector.create(activity)
            .openGallery(if (isPhoto) SelectMimeType.ofImage() else SelectMimeType.ofVideo())
            .setSelectionMode(SelectModeConfig.SINGLE)
            .setSelectedData(mLocalMedia)
            .setMaxSelectNum(if (limitSize) 9 else 999)
            .setMinSelectNum(1)
            .setMaxVideoSelectNum(999)
            .setMinVideoSelectNum(1)
            .setRecordVideoMaxSecond(15)
            .setRecordVideoMinSecond(1)

            .setImageEngine(mEngine)
            .setSelectorUIStyle(mTheme)
            .setImageSpanCount(3)
            .setCompressEngine(ImageFileCompressEngine())
            .setCropEngine(mCutEngine)
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
            .forResult(listener)
    }

    fun startMediaSelected(
        activity: Activity,
        mLocalMedia: List<LocalMedia>,
        listener: OnResultCallbackListener<LocalMedia>
    ) {
        checkTheme()

        PictureSelector.create(activity)
            .openGallery(SelectMimeType.ofAll())
            .setSelectionMode(SelectModeConfig.SINGLE)
            .setSelectedData(mLocalMedia)
            .setMaxSelectNum(9)
            .setMinSelectNum(1)
            .setMaxVideoSelectNum(1)
            .setMinVideoSelectNum(1)
            .setRecordVideoMaxSecond(15)
            .setRecordVideoMinSecond(1)

            .setImageEngine(mEngine)
            .setSelectorUIStyle(mTheme)
            .setImageSpanCount(3)
            .setCompressEngine(ImageFileCompressEngine())
            .setCropEngine(mCutEngine)
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
            .forResult(listener)
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
        checkTheme()

        PictureSelector.create(fragment)
            .openPreview()
            .setImageEngine(mEngine)
            .setSelectorUIStyle(mTheme)
            .isPreviewFullScreenMode(false)
            .startActivityPreview(position, false, media)
    }

    fun openExternalPreview(activity: Activity, position: Int, media: ArrayList<LocalMedia>) {
        checkTheme()

        PictureSelector.create(activity)
            .openPreview()
            .setImageEngine(mEngine)
            .setSelectorUIStyle(mTheme)
            .isPreviewFullScreenMode(false)
            .startActivityPreview(position, false, media)
    }

    private fun checkTheme() {
        if (mEngine == null) {
            throw RuntimeException("请先初始化主题")
        }
    }

    private class ImageFileCompressEngine : CompressFileEngine {
        override fun onStartCompress(
            context: Context,
            source: ArrayList<Uri>,
            call: OnKeyValueResultCallbackListener?
        ) {
            Luban.with(context)
                .load(source)
                .ignoreBy(100)
                .setRenameListener { filePath ->
                    val indexOf = filePath.lastIndexOf(".")
                    val postfix = if (indexOf != -1) filePath.substring(indexOf) else ".jpg"
                    DateUtils.getCreateFileName("CMP_") + postfix
                }
                .setCompressListener(object : OnNewCompressListener {
                    override fun onStart() {}
                    override fun onSuccess(source: String, compressFile: File) {
                        call?.onCallback(source, compressFile.absolutePath)
                    }

                    override fun onError(source: String, e: Throwable) {
                        call?.onCallback(source, null)
                    }
                })
                .launch()
        }
    }

    private fun buildOptions(context: Context): UCrop.Options {
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

    private class SandboxFileEngine : UriToFileTransformEngine {
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
