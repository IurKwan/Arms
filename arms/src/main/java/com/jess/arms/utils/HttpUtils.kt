package com.jess.arms.utils

import android.net.ParseException
import com.google.gson.JsonParseException
import com.jess.arms.http.subscriber.NetErrorException
import org.json.JSONException
import retrofit2.HttpException
import java.io.Closeable
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * 服务器错误
 */
const val SERVER_ERROR = 500

/**
 * 请求地址不存在
 */
const val NOT_FOUND = 404

/**
 * 服务器拒绝
 */
const val FORBIDDEN = 403

/**
 * 服务器重定向
 */
const val TEMPORARY_REDIRECT = 307

fun getHttpErrorText(t: Throwable): String {
    var msg = "未知错误"
    if (t is UnknownHostException) {
        msg = "网络不可用"
    } else if (t is SocketTimeoutException) {
        msg = "请求网络超时"
    } else if (t is HttpException) {
        msg = convertStatusCode(t)
    } else if (t is JsonParseException || t is ParseException || t is JSONException) {
        msg = "数据解析错误"
    } else if (t is NetErrorException) {
        msg = t.message ?: ""
    }
    return msg
}

fun convertStatusCode(t: HttpException): String {
    return when {
        t.code() == SERVER_ERROR -> "服务器发生错误"
        t.code() == NOT_FOUND -> "请求地址不存在"
        t.code() == FORBIDDEN -> "请求被服务器拒绝"
        t.code() == TEMPORARY_REDIRECT -> "请求被重定向到其他页面"
        else -> t.message()
    }
}

/** Closes this, ignoring any checked exceptions. */
fun Closeable.closeQuietly() {
    try {
        close()
    } catch (rethrown: RuntimeException) {
        throw rethrown
    } catch (_: Exception) {
    }
}