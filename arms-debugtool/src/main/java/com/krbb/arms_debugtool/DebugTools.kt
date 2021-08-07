package com.krbb.arms_debugtool

import android.content.Context
import android.os.Build
import android.os.Process

/**
 * @author IurKwan
 * @date 3/8/21
 */
class DebugTools {

    private var mChangeModeListener : ChangeReleaseModeListener? = null

    fun buildTime(): String {
        //new date() 当前你在运行时拿到的时间，这个包，被打出来的时间
        return "构建时间：" + BuildConfig.BUILD_TIME
    }

    fun buildDevice(): String {
        // 构建版本 ： 品牌-sdk-abi
        return "设备信息:" + Build.BRAND + "-" + Build.VERSION.SDK_INT + "-" + Build.CPU_ABI
    }

    @HiDebug(name = "一键切换线上线下环境", desc = "将切换服务器地址，方便调试")
    fun degradeToReleaseMode(context: Context) {

        mChangeModeListener?.onChange()

        // 切换地址
//        SPManager.setReleaseMode(b = false, commit = true)

        // 退出登录
//        SPManager.setLogin(false, commit = true)

        val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)
        context.startActivity(intent)

        //杀掉当前进程,并主动启动新的 启动页，以完成重启的动作
        Process.killProcess(Process.myPid())
    }

}

interface ChangeReleaseModeListener{
    fun onChange()
}