package com.iur.arms.imageloader.glide;

import android.content.Context;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.BaseImageLoaderStrategy;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DataHelper;

import java.io.File;
import java.io.InputStream;

import okhttp3.Call;

/**
 * {@link AppGlideModule} 的默认实现类
 * 用于配置缓存文件夹,切换图片请求框架等操作
 *
 * @author IurKwan
 * @date 1/19/21
 */
@GlideModule(glideName = "GlideArms")
public class GlideConfiguration extends AppGlideModule {

    /**
     * 图片缓存文件最大值为100Mb
     */
    public static final int IMAGE_DISK_CACHE_MAX_SIZE = 100 * 1024 * 1024;

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        final AppComponent appComponent = ArmsUtils.obtainAppComponentFromContext(context);
        builder.setDiskCache(() -> {
            // Careful: the external cache directory doesn't enforce permissions
            return DiskLruCacheWrapper.create(DataHelper.makeDirs(new File(appComponent.cacheFile(), "Glide")), IMAGE_DISK_CACHE_MAX_SIZE);
        });

        MemorySizeCalculator calculator = new MemorySizeCalculator.Builder(context).build();
        int defaultMemoryCacheSize = calculator.getMemoryCacheSize();
        int defaultBitmapPoolSize = calculator.getBitmapPoolSize();

        int customMemoryCacheSize = (int) (1.2 * defaultMemoryCacheSize);
        int customBitmapPoolSize = (int) (1.2 * defaultBitmapPoolSize);

        builder.setMemoryCache(new LruResourceCache(customMemoryCacheSize));
        builder.setBitmapPool(new LruBitmapPool(customBitmapPoolSize));

        //将配置 Glide 的机会转交给 GlideImageLoaderStrategy,如你觉得框架提供的 GlideImageLoaderStrategy
        //并不能满足自己的需求,想自定义 BaseImageLoaderStrategy,那请你最好实现 GlideAppliesOptions
        //因为只有成为 GlideAppliesOptions 的实现类,这里才能调用 applyGlideOptions(),让你具有配置 Glide 的权利
        BaseImageLoaderStrategy loadImgStrategy = appComponent.imageLoader().getLoadImgStrategy();
        if (loadImgStrategy instanceof GlideAppliesOptions) {
            ((GlideAppliesOptions) loadImgStrategy).applyGlideOptions(context, builder);
        }
    }

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        //Glide 默认使用 HttpURLConnection 做网络请求,在这切换成 Okhttp 请求
        AppComponent appComponent = ArmsUtils.obtainAppComponentFromContext(context);
        registry.replace(
                GlideUrl.class,
                InputStream.class,
                new OkHttpUrlLoader.Factory((Call.Factory) appComponent.okHttpClient())
        );

        BaseImageLoaderStrategy loadImgStrategy = appComponent.imageLoader().getLoadImgStrategy();
        if (loadImgStrategy instanceof GlideAppliesOptions) {
            ((GlideAppliesOptions) loadImgStrategy).registerComponents(context, glide, registry);
        }
    }

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}
