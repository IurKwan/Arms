package com.krbb.arms_push.util

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor

/**
 * @author IurKwan
 * @date 2021/7/19
 */
open class AppExecutors constructor(
    val diskIO: Executor = DiskIOThreadExecutor(),
    val mainThread: Executor = MainThreadExecutor()
) {
    private class MainThreadExecutor : Executor {

        private val mainThreadHandler = Handler(Looper.getMainLooper())

        override fun execute(p0: Runnable) {
            mainThreadHandler.post(p0)
        }
    }
}