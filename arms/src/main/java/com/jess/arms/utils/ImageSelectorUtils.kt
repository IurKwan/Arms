package com.jess.arms.utils

import android.app.Activity
import android.os.Build
import androidx.fragment.app.Fragment
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.engine.ImageEngine
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.listener.OnResultCallbackListener

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

    private var mTheme: Int = 0

    fun init(theme: Int, engine: ImageEngine) {
        mTheme = theme
        mEngine = engine
    }

    fun takeOnePhoto(fragment: Fragment, isCut: Boolean, listener: OnResultCallbackListener<*>) {
        checkTheme()

        PictureSelector.create(fragment)
            .openGallery(PictureMimeType.ofImage())
            .imageEngine(mEngine)
            .theme(mTheme)
            .imageSpanCount(3)
            .isDragFrame(false)
            .scaleEnabled(true)
            .circleDimmedLayer(true)
            .selectionMode(PictureConfig.SINGLE)
            .isPreviewImage(true)
            .withAspectRatio(1, 1)
            .isCamera(true)
            .isAndroidQTransform(true)
            .isEnableCrop(isCut)
            .isCompress(true)
            .isGif(true)
            .isOpenClickSound(false)
            .minimumCompressSize(10)
            .forResult(listener)
    }

    fun starCamera(fragment: Fragment, listener: OnResultCallbackListener<*>) {
        checkTheme()

        PictureSelector.create(fragment)
            .openCamera(PictureMimeType.ofImage())
            .imageEngine(mEngine)
            .isEnableCrop(true)
            .circleDimmedLayer(true)
            .forResult(listener)
    }

    fun startPictureSelected(
        fragment: Fragment,
        mLocalMedia: List<LocalMedia>,
        max: Int,
        isPhoto: Boolean,
        listener: OnResultCallbackListener<*>
    ) {
        checkTheme()

        PictureSelector.create(fragment)
            .openGallery(if (isPhoto) PictureMimeType.ofImage() else PictureMimeType.ofVideo())
            .imageEngine(mEngine)
            .theme(mTheme)
            .maxSelectNum(if (max == 0) 1 else max)
            .minSelectNum(1)
            .isAndroidQTransform(true)
            .imageSpanCount(3)
            .isPreviewVideo(true)
            .isPreviewImage(true)
            .selectionMode(PictureConfig.MULTIPLE)
            .recordVideoSecond(10)
            .isZoomAnim(true)
            .isCamera(true)
            .isEnableCrop(false)
            .isCompress(true)
            .maxVideoSelectNum(999)
            .minVideoSelectNum(1)
            .videoMaxSecond(15)
            .videoMinSecond(1)
            .queryMaxFileSize(30f)
            .isGif(false)
            .isPreviewEggs(false)
            .isOpenClickSound(false)
            .selectionData(mLocalMedia)
            .minimumCompressSize(100)
            .forResult(listener)
    }

    fun startPictureSelected(
        fragment: Fragment,
        mLocalMedia: List<LocalMedia>,
        limitSize: Boolean,
        isPhoto: Boolean,
        listener: OnResultCallbackListener<*>
    ) {
        checkTheme()

        PictureSelector.create(fragment)
            .openGallery(if (isPhoto) PictureMimeType.ofImage() else PictureMimeType.ofVideo())
            .imageEngine(mEngine)
            .theme(mTheme)
            .maxSelectNum(if (limitSize) 9 else 999)
            .minSelectNum(1)
            .imageSpanCount(3)
            .isPreviewVideo(true)
            .isPreviewImage(true)
            .selectionMode(PictureConfig.MULTIPLE)
            .recordVideoSecond(10)
            .isZoomAnim(true)
            .isCamera(true)
            .isEnableCrop(false)
            .isCompress(true)
            .maxVideoSelectNum(999)
            .minVideoSelectNum(1)
            .videoMaxSecond(15)
            .videoMinSecond(1)
            .queryMaxFileSize(30f)
            .isAndroidQTransform(true)
            .isGif(false)
            .isPreviewEggs(false)
            .isOpenClickSound(false)
            .selectionData(mLocalMedia)
            .minimumCompressSize(100)
            .forResult(listener)
    }

    fun startPictureSelected(
        activity: Activity,
        mLocalMedia: List<LocalMedia>,
        limitSize: Boolean,
        isPhoto: Boolean,
        listener: OnResultCallbackListener<*>
    ) {
        checkTheme()

        PictureSelector.create(activity)
            .openGallery(if (isPhoto) PictureMimeType.ofImage() else PictureMimeType.ofVideo())
            .imageEngine(mEngine)
            .theme(mTheme)
            .maxSelectNum(if (limitSize) 9 else 999)
            .minSelectNum(1)
            .imageSpanCount(3)
            .isPreviewVideo(true)
            .isPreviewImage(true)
            .selectionMode(PictureConfig.MULTIPLE)
            .recordVideoSecond(10)
            .isZoomAnim(true)
            .isCamera(true)
            .isEnableCrop(false)
            .isAndroidQTransform(true)
            .isCompress(true)
            .maxVideoSelectNum(999)
            .minVideoSelectNum(1)
            .videoMaxSecond(15)
            .videoMinSecond(1)
            .queryMaxFileSize(30f)
            .isGif(false)
            .isPreviewEggs(false)
            .isOpenClickSound(false)
            .selectionData(mLocalMedia)
            .minimumCompressSize(100)
            .forResult(listener)
    }

    fun startMediaSelected(
        activity: Activity, mLocalMedia: List<LocalMedia>, listener: OnResultCallbackListener<*>
    ) {
        checkTheme()

        PictureSelector.create(activity)
            .openGallery(PictureMimeType.ofAll())
            .imageEngine(mEngine)
            .theme(mTheme)
            .maxSelectNum(9)
            .minSelectNum(1)
            .imageSpanCount(3)
            .isPreviewVideo(true)
            .isPreviewImage(true)
            .selectionMode(PictureConfig.MULTIPLE)
            .recordVideoSecond(10)
            .isZoomAnim(true)
            .isCamera(true)
            .isEnableCrop(false)
            .isAndroidQTransform(true)
            .isCompress(true)
            .maxVideoSelectNum(1)
            .minVideoSelectNum(1)
            .videoMaxSecond(15)
            .videoMinSecond(1)
            .isGif(false)
            .queryMaxFileSize(30f)
            .isPreviewEggs(false)
            .videoQuality(1)
            .isOpenClickSound(false)
            .selectionData(mLocalMedia)
            .minimumCompressSize(100)
            .forResult(listener)
    }

    fun getFinalPath(media: LocalMedia): String {
        if (media.isCompressed) {
            return media.compressPath
        }
        if (media.isCut) {
            return media.cutPath
        }
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.Q && media.androidQToPath != null) {
            return media.androidQToPath
        }
        return media.path
    }

    fun openExternalPreview(fragment: Fragment, position: Int, media: List<LocalMedia>) {
        checkTheme()

        PictureSelector.create(fragment)
            .themeStyle(mTheme)
            .imageEngine(mEngine)
            .openExternalPreview(position, media)
    }

    fun openExternalPreview(activity: Activity, position: Int, media: List<LocalMedia>) {
        checkTheme()

        PictureSelector.create(activity)
            .themeStyle(mTheme)
            .imageEngine(mEngine)
            .openExternalPreview(position, media)
    }

    private fun checkTheme() {
        if (mEngine == null) {
            throw RuntimeException("请先初始化主题")
        }
    }
}
