package com.jess.arms.http.factory

/**
 * @author IurKwan
 * @date 2022/4/20
 * okhttp 事件统计对象,储存请求和响应相关的数据
 */
class OkHttpEvent {

    /**
     * DNS 解析开始时间
     */
    var dnsStartTime = 0L

    /**
     * DNS 解析结束时间
     */
    var dnsEndTime = 0L

    /**
     * 响应体大小
     */
    var responseBodySize = 0L

    /**
     * 请求是否成功
     */
    var success = false

    /**
     * 错误原因
     */
    var errorStack : String? = null

}