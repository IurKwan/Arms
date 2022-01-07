package com.jess.arms.http.convert

import com.google.gson.TypeAdapter
import com.jess.arms.utils.CastUtils
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Converter
import java.io.IOException


/**
 * 自定义转换实体类
 * 判断ErrType是否成功
 * 成功则只返回实体类去除 BaseResponse
 * @author IurKwan
 * @date 12/8/20
 */
internal class HandlerErrorGsonResponseBodyConverter<T>(private val adapter: TypeAdapter<T>) :
    Converter<ResponseBody, T> {

    companion object {
        /**
         * 固定字段
         */
        const val STATUS = "Status"
        const val ERROR_TYPE = "ErrType"
        const val MESSAGE = "Message"
        const val DATA = "Data"
    }

    @Throws(IOException::class)
    override fun convert(value: ResponseBody): T {
        // 这里就是对返回结果进行处理
        val t = value.string()

        try {
            val jsonObject: JSONObject?
            try {
                jsonObject = JSONObject(t)
            } catch (e: Exception) {
                // 非Json对象，转成String返回
                return if (t.isNotEmpty()) {
                    CastUtils.cast(t)
                } else {
                    // 返回空字符串
                    CastUtils.cast(String())
                }
            }

            val status = jsonObject.optBoolean(STATUS)
            val errorType = jsonObject.optInt(ERROR_TYPE)
            val message = jsonObject.optString(MESSAGE)
            val response = jsonObject.opt(DATA)

            if (message.isEmpty() && response == null) {
                return CastUtils.cast(t)
            }

            // 存在不规范问题
            // Data不一定是Json转换的字符串
            // 也可能单纯是String类型
            // 也可能为null
            // 为null的情况下统一返回空字符串""
            if (status) {
                // 请求成功
                when (response) {
                    is JSONObject -> {
                        return adapter.fromJson(response.toString())
                    }
                    is JSONArray -> {
                        return adapter.fromJson(response.toString())
                    }
                    is Boolean -> {
                        return CastUtils.cast(response)
                    }
                    is String -> {
                        // 返回字符串内容
                        return CastUtils.cast(String(response.toCharArray()))
                    }
                    else -> {
                        // 直接抛出错误
                        throw CodeException(errorType, message)
                    }
                }
            } else {
                // 直接返回内容
                return CastUtils.cast(t)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return CastUtils.cast(t)
        } finally {
            value.close()
        }
    }
}