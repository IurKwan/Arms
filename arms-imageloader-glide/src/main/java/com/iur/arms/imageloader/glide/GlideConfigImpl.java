package com.iur.arms.imageloader.glide;

import android.widget.ImageView;

import com.jess.arms.http.imageloader.ImageConfig;

/**
 * @author IurKwan
 * @date 1/19/21
 */
public class GlideConfigImpl extends ImageConfig {

    private @CacheStrategy.Strategy int cacheStrategy;

    private boolean isCenterCrop;

    private boolean isCircle;

    /**
     * 清理内存缓存
     */
    private boolean isClearMemory;

    /**
     * 清理本地缓存
     */
    private boolean isClearDiskCache;

    /**
     * 剪裁图片
     */
    private int width;
    private int height;

    private GlideConfigImpl(Builder builder) {
        this.url = builder.url;
        this.imageView = builder.imageView;
        this.placeholder = builder.placeholder;
        this.errorPic = builder.errorPic;
        this.cacheStrategy = builder.cacheStrategy;
        this.isCenterCrop = builder.isCenterCrop;
        this.isCircle = builder.isCircle;
        this.isClearMemory = builder.isClearMemory;
        this.isClearDiskCache = builder.isClearDiskCache;
        this.width = builder.width;
        this.height = builder.height;
    }

    public static Builder builder() {
        return new Builder();
    }

    public @CacheStrategy.Strategy int getCacheStrategy() {
        return cacheStrategy;
    }

    public boolean isClearMemory() {
        return isClearMemory;
    }

    public boolean isClearDiskCache() {
        return isClearDiskCache;
    }

    public boolean isCenterCrop() {
        return isCenterCrop;
    }

    public boolean isCircle() {
        return isCircle;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public static final class Builder {
        private String url;
        private ImageView imageView;
        private int placeholder;
        private int errorPic;
        private @CacheStrategy.Strategy int cacheStrategy;
        private boolean isCenterCrop;
        private boolean isCircle;
        private boolean isClearMemory;
        private boolean isClearDiskCache;
        private int width;
        private int height;

        private Builder() {
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder placeholder(int placeholder) {
            this.placeholder = placeholder;
            return this;
        }

        public Builder errorPic(int errorPic) {
            this.errorPic = errorPic;
            return this;
        }

        public Builder imageView(ImageView imageView) {
            this.imageView = imageView;
            return this;
        }

        public Builder cacheStrategy(@CacheStrategy.Strategy int cacheStrategy) {
            this.cacheStrategy = cacheStrategy;
            return this;
        }

        public Builder centerCrop() {
            this.isCenterCrop = true;
            return this;
        }

        public Builder isCircle(boolean isCircle) {
            this.isCircle = isCircle;
            return this;
        }

        public Builder isClearMemory(boolean isClearMemory) {
            this.isClearMemory = isClearMemory;
            return this;
        }

        public Builder isClearDiskCache(boolean isClearDiskCache) {
            this.isClearDiskCache = isClearDiskCache;
            return this;
        }

        public Builder override(int width, int height) {
            this.width = width;
            this.height = height;
            return this;
        }

        public GlideConfigImpl build() {
            return new GlideConfigImpl(this);
        }
    }
}