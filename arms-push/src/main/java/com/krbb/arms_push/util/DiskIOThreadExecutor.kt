package com.krbb.arms_push.util

import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * @author IurKwan
 * @date 2021/7/19
 */
class DiskIOThreadExecutor : Executor{

    private val diskIO = Executors.newSingleThreadExecutor()

    override fun execute(p0: Runnable?) {
        diskIO.execute(p0)
    }
}