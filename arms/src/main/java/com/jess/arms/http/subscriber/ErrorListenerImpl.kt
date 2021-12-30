package com.jess.arms.http.subscriber

import android.content.Context
import com.google.gson.JsonIOException
import com.google.gson.JsonParseException
import com.jess.arms.http.convert.HandlerErrorGsonResponseBodyConverter
import com.jess.arms.integration.AppManager
import me.jessyan.rxerrorhandler.handler.listener.ResponseErrorListener
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import timber.log.Timber
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.ParseException

/**
 * 根据凯尔公司后台规范作出相应处理
 * @author IurKwan
 * @date 2021/12/30
 */
class ErrorListenerImpl : ResponseErrorListener{

    override fun handleResponseError(context: Context?, e: Throwable?) {
        Timber.tag("Catch-Error").w(e)

        var message = "未知错误"
        if (e is UnknownHostException){
            message = "无连接异常"
        } else if (e is SocketTimeoutException){
            message = "请求网络超时"
        } else if (e is HttpException){
            message = convertStatusCode(e)
        } else if (e is JsonParseException
            || e is android.net.ParseException
            || e is JSONException
            || e is JsonIOException
            || e is ParseException) {
            message = "数据解析错误"
        } else if (e is ConnectException){
            message = "无法连接到服务器"
        }

        AppManager.getAppManager().showSnackbar(message, true)
    }

    private fun convertStatusCode(httpException: HttpException): String {
        val msg: String = when {
            httpException.code() == 500 -> {
                "服务器发生错误"
            }
            httpException.code() == 404 -> {
                "请求地址不存在"
            }
            httpException.code() == 403 -> {
                "请求被服务器拒绝"
            }
            httpException.code() == 307 -> {
                "请求被重定向到其他页面"
            }
            else -> {
                httpException.message()
            }
        }
        return msg
    }

}