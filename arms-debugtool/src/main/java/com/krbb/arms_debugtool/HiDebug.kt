package com.krbb.arms_debugtool

/**
 * @author IurKwan
 * @date 3/8/21
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class HiDebug(val name: String, val desc: String)