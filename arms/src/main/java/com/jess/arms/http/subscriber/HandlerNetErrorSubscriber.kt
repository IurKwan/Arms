package com.jess.arms.http.subscriber

import com.google.gson.JsonParseException
import com.jess.arms.integration.AppManager
import com.jess.arms.http.convert.HandlerErrorGsonResponseBodyConverter
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.ParseException

/**
 * 统一异常处理，抛出 [com.krbb.mvpkrbb.http.exception.NetErrorException]
 * @author IurKwan
 * @date 12/9/20
 */
abstract class HandlerNetErrorSubscriber<T> : Observer<T>, NetErrorExceptionCallback {

    override fun onSubscribe(d: Disposable) {}

    override fun onError(e: Throwable) {
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
            error = NetErrorException(e.message, NetErrorException.OTHER)
        }

        error?.let {
            onFail(it)
        }

    }

    override fun onFail(error: NetErrorException) {
        AppManager.getAppManager().showSnackbar(error.message, false)
    }

    override fun onComplete() {}

}