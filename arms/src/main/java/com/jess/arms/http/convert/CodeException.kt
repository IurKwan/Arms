package com.jess.arms.http.convert

/**
 * @author IurKwan
 * @date 2022/1/7
 */
class CodeException : Exception {

    private var code: Int = 1

    constructor(code: Int, message: String) : super(message) {
        this.code = code
    }

    constructor(message: String) : super(message)

    constructor(message: String, cause: Throwable) : super(message, cause)

    constructor(code: Int, cause: Throwable) : super(cause) {
        this.code = code
    }

    constructor(code: Int, message: String, cause: Throwable) : super(message, cause) {
        this.code = code
    }

    constructor(cause: Throwable) : super(cause)

    fun getCode(): Int {
        return this.code
    }

}