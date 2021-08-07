package com.iur.arms.imageloader.glide

import androidx.annotation.IntDef

/**
 * 配置图片的缓存类型,各大框架是大致相同的
 * @author IurKwan
 * @date 1/19/21
 */
interface CacheStrategy {

    companion object {

        const val ALL = 0

        const val NONE = 1

        const val RESOURCE = 2

        const val DATA = 3

        const val AUTOMATIC = 4

    }

    @IntDef(ALL, NONE, RESOURCE, DATA, AUTOMATIC)
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    annotation class Strategy

}