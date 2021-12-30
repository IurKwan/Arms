package com.jess.arms.http.subscriber

import android.content.Context
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

        if (e is NetErrorException){

        }




        var error: NetErrorException? = null
        // 对不是自定义抛出的错误进行解析
        if (e !is NetErrorException) {
            if (e is UnknownHostException) {
                error = NetErrorException(e, NetErrorException.NO_CONNECT_ERROR)
            } else if (e is JSONException || e is JsonParseException || e is ParseException) {
                error = NetErrorException(e, NetErrorException.PARSE_ERROR)
            } else if (e is SocketTimeoutException) {
                error = NetErrorException(e, NetErrorException.SOCKET_TIMEOUT_ERROR)
            } else if (e is ConnectException) {
                error = NetErrorException(e, NetErrorException.CONNECT_EXCEPTION_ERROR)
            } else if (e is HttpException) {
                // 输出服务器返回的错误信息
                if (e.response() != null && e.response()!!.errorBody() != null) {
                    e.response()?.errorBody()?.let {
                        try {
                            val json = JSONObject(it.string())
                            val errorMessage =
                                json.optString(HandlerErrorGsonResponseBodyConverter.MESSAGE)
                            error = if (!errorMessage.isNullOrEmpty()) {
                                NetErrorException(
                                    errorMessage,
                                    NetErrorException.HTTP_EXCEPTION
                                )
                            } else {
                                NetErrorException(
                                    e,
                                    NetErrorException.HTTP_EXCEPTION
                                )
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                            error = NetErrorException(
                                e,
                                NetErrorException.HTTP_EXCEPTION
                            )
                        }
                    }
                } else {
                    error = NetErrorException(e, NetErrorException.HTTP_EXCEPTION)
                }
            } else {
                error = NetErrorException(e, NetErrorException.OTHER)
            }
        } else {
            error = if (e is HttpException) {
                NetErrorException(e, e.code())
            } else {
                NetErrorException(e.message, e.mErrorType)
            }
        }

        error?.let {
            AppManager.getAppManager().showSnackbar(error?.message, false)
        }
    }

}