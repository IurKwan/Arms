package com.jess.arms.http.subscriber

import android.text.TextUtils
import retrofit2.HttpException
import java.io.IOException

/**
 * @author IurKwan
 * @date 12/9/20
 */
class NetErrorException : IOException {

    var mErrorType = NO_CONNECT_ERROR
    var mServiceErrorCode : Int? = null
    private var mException: Throwable? = null
    private var mErrorMessage: String? = ""

    constructor(exception: Throwable?, mErrorType: Int,serviceCode : Int? = null) {
        mException = exception
        this.mErrorType = mErrorType
        this.mServiceErrorCode = serviceCode
    }

    constructor(message: String?, mErrorType: Int) : super(message) {
        this.mErrorType = mErrorType
        mErrorMessage = message
    }

    override val message: String?
        get() {
            if (!TextUtils.isEmpty(mErrorMessage)) {
                return mErrorMessage
            }
            if (mException is HttpException){
                return convertStatusCode(mException as HttpException)
            }
            when (mErrorType) {
                PARSE_ERROR -> return "数据解析异常"
                NO_CONNECT_ERROR -> return "无连接异常"
                OTHER -> return mErrorMessage
                UNKNOW -> return "当前无网络连接"
                CONNECT_EXCEPTION_ERROR -> return "无法连接到服务器，请检查网络连接后再试！"
                HTTP_EXCEPTION -> return convertStatusCode(mException as? HttpException?)
                else -> {
                }
            }
            return try {
                mException!!.message
            } catch (e: Exception) {
                "未知错误"
            }
        }

    private fun convertStatusCode(httpException: HttpException?): String {
        if (httpException == null){
            return "未知错误"
        }
        return when (httpException.code()) {
            500 -> "服务器发生错误"
            401 -> "登录用户认证失败"
            404 -> "请求地址不存在"
            403 -> "请求被服务器拒绝"
            307 -> "请求被重定向到其他页面"
            405 -> "请求方法错误"
            400 -> "服务器错误"
            else -> httpException.message()
        }
    }

    companion object {
        /**
         * 失败
         */
        const val FAIL = 0

        /**
         * 成功
         */
        const val SUCCESS = 1

        /**
         * 没有登录
         */
        const val ERROR_NOT_LOGIN = -10010

        /**
         * 登录过期
         */
        const val ERROR_LOGIN_OVERDUE = -10011

        /**
         * 登录验证失败
         */
        const val ERROR_VERIFICATION_FAILURE = -10012

        /**
         * 登录用户认证失败
         */
        const val ERROR_USER_VERIFY_FAIL = 401

        /**
         * 不允许操作
         */
        const val ERROR_BAN_ACTION = 403

        /**
         * 404
         */
        const val ERROR_NOT_FOUND = 404

        /**
         * 无连接异常
         */
        const val NO_CONNECT_ERROR = 101

        /**
         * 数据解析异常
         */
        const val PARSE_ERROR = 102

        /**
         * 网络连接超时
         */
        const val SOCKET_TIMEOUT_ERROR = 103

        /**
         * 无法连接到服务
         */
        const val CONNECT_EXCEPTION_ERROR = 104

        /**
         * 服务器错误
         */
        const val HTTP_EXCEPTION = 105

        /**
         * 登陆失效
         */
        const val LOGIN_OUT = 401

        /**
         * 其他
         */
        const val OTHER = -99

        /**
         * 没有网络
         */
        const val UNKNOW = -1
    }
}