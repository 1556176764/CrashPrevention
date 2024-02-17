package com.github.lib_crash_prevention

import android.os.Looper
import java.lang.Thread.UncaughtExceptionHandler


object CrashUncaughtExceptionHandler : UncaughtExceptionHandler {
    private var oldHandler: UncaughtExceptionHandler? = null

    fun init() {
        oldHandler = Thread.getDefaultUncaughtExceptionHandler()
        oldHandler?.let {
            Thread.setDefaultUncaughtExceptionHandler(this)
        }
    }

    override fun uncaughtException(t: Thread, e: Throwable) {
        if (CrashPortrayHelper.needHandle(e)) {
            restore()
            return
        }

        //未命中预设异常，则崩溃
        oldHandler?.uncaughtException(t, e)
    }

    /**
     * 让当前线程恢复运行
     */
    private fun restore() {
        while (true) {
            try {
                if (Looper.myLooper() == null) {
                    Looper.prepare()
                }
                Looper.loop()
            } catch (e: Exception) {
                uncaughtException(Thread.currentThread(), e)
                break
            }
        }
    }
}