package com.jess.arms.base

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

/**
 * @author IurKwan
 * @date 2022/4/20
 */
class AppLifecycleObserver : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onAppForeground() {
        // 应用进入前台
    }
}