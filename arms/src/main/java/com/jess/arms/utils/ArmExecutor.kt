package com.jess.arms.utils

import android.annotation.SuppressLint
import okhttp3.internal.threadFactory
import timber.log.Timber
import java.util.concurrent.*
import java.util.concurrent.atomic.AtomicLong
import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.ReentrantLock

/**
 * 支持线程池暂停.恢复(批量文件下载，上传)
 * 异步结果主动回调主线程
 * TODO 线程池能力监控,耗时任务检测,定时,延迟
 * @author IurKwan
 * @date 2022/1/28
 */
object ArmExecutor {

    private var isPaused: Boolean = false
    private var armExecutor: ThreadPoolExecutor
    private var lock: ReentrantLock = ReentrantLock()
    private var pauseCondition: Condition = lock.newCondition()

    init {

        val cpuCount = Runtime.getRuntime().availableProcessors()
        val corePoolSize = cpuCount + 1
        val maxPoolSize = cpuCount * 2 + 1
        val blockingQueue: SynchronousQueue<Runnable> = SynchronousQueue()
        val keepAliveTime = 60L
        val unit = TimeUnit.SECONDS

        val seq = AtomicLong()
        val threadFactory = threadFactory("Arms Executor" + seq.getAndIncrement(),false)

        armExecutor = object : ThreadPoolExecutor(
            corePoolSize,
            maxPoolSize,
            keepAliveTime,
            unit,
            blockingQueue as BlockingQueue<Runnable>,
            threadFactory
        ) {
            override fun beforeExecute(t: Thread?, r: Runnable?) {
                if (isPaused) {
                    lock.lock()
                    try {
                        pauseCondition.await()
                    } finally {
                        lock.unlock()
                    }
                }
            }

            @SuppressLint("BinaryOperationInTimber")
            override fun afterExecute(r: Runnable?, t: Throwable?) {
                //监控线程池耗时任务,线程创建数量,正在运行的数量

            }
        }
    }

    fun execute(runnable: Runnable) {
        armExecutor.execute(runnable)
    }

    fun pause() {
        lock.lock()
        try {
            isPaused = true
            Timber.e("ArmExecutor is paused")
        } finally {
            lock.unlock()
        }
    }

    fun resume() {
        lock.lock()
        try {
            isPaused = false
            pauseCondition.signalAll()
        } finally {
            lock.unlock()
        }
        Timber.e("ArmExecutor is resumed")
    }

    fun instance(): ThreadPoolExecutor{
        return armExecutor
    }

}