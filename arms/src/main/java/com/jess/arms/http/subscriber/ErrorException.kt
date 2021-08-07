package com.jess.arms.http.subscriber


/**
 * @author IurKwan
 * @date 12/9/20
 */
internal interface NetErrorExceptionCallback {

    fun onFail(error : NetErrorException)

}