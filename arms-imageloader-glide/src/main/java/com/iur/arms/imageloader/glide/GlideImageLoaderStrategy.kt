package com.iur.arms.imageloader.glide

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions
import com.jess.arms.http.imageloader.BaseImageLoaderStrategy
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

/**
 * @author IurKwan
 * @date 1/19/21
 */
class GlideImageLoaderStrategy : BaseImageLoaderStrategy<GlideConfigImpl>, GlideAppliesOptions {

    @SuppressLint("CheckResult")
    override fun loadImage(context: Context, config: GlideConfigImpl) {

        // 使用context的生命周期，glide会自动绑定释放
        val requests = GlideArms.with(context)
            .asBitmap()

        val glideRequest : GlideRequest<Bitmap> = requests.load(config.url)

        // 配置glide的缓存策略,默认从ALL找
        val strategy: DiskCacheStrategy = when(config.cacheStrategy){
            CacheStrategy.NONE -> DiskCacheStrategy.NONE
            CacheStrategy.RESOURCE -> DiskCacheStrategy.RESOURCE
            CacheStrategy.DATA -> DiskCacheStrategy.DATA
            CacheStrategy.AUTOMATIC -> DiskCacheStrategy.AUTOMATIC
            else -> DiskCacheStrategy.ALL
        }
        glideRequest.diskCacheStrategy(strategy)

        if (config.isCenterCrop){
            glideRequest.centerCrop()
        }

        if (config.isCircle){
            glideRequest.circleCrop()
        }

        if (config.placeholder != 0){
            glideRequest.placeholder(config.placeholder)
        } else {
            // 默认占位图
            glideRequest.placeholder(android.R.color.white)
        }

        if (config.errorPic != 0){
            glideRequest.error(config.errorPic)
        }

        if (config.width != 0 && config.height != 0){
            glideRequest.override(config.width,config.height)
        }

        glideRequest.transition(BitmapTransitionOptions.withCrossFade())
            .into(config.imageView)
    }

    override fun clear(context: Context, config: GlideConfigImpl) {
        if (config.imageView != null){
            GlideArms.get(context)
                .requestManagerRetriever
                .get(context)
                .clear(config.imageView)
        }

        // 清除本地缓存，在子线程进行
        if (config.isClearDiskCache){
            Completable.fromAction {
                Glide.get(context)
                    .clearDiskCache()
            }
                .subscribeOn(Schedulers.io())
                .subscribe()
        }

        // 清除内存缓存
        // 注意：需要在主线程进行
        // 否则可能会出现正在显示，但内存清除了得情况
        if (config.isClearMemory){
            Completable.fromAction {
                Glide.get(context)
                    .clearMemory()
            }
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe()
        }
    }

    override fun applyGlideOptions(context: Context, builder: GlideBuilder) {
        Timber.i("applyGlideOptions")
    }

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        Timber.i("registerComponents")
    }
}