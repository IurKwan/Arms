package com.jess.arms.utils

import com.blankj.utilcode.util.NetworkUtils
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response

/**
 * @author IurKwan
 * @date 2022/4/20
 */
class NoNetworkInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()
        // 当网络不可用
        if (!NetworkUtils.isConnected()) {
            // 设置缓存策略，强制缓存
            builder.cacheControl(CacheControl.FORCE_CACHE)
        }
        // 把请求传递到下一个拦截器
        return chain.proceed(builder.build())
    }
}