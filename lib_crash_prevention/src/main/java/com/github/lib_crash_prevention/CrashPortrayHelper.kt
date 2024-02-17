package com.github.lib_crash_prevention

import android.app.Application
import android.os.Build
import com.github.lib_crash_prevention.CrashModel.CrashPortray
import com.github.lib_crash_prevention.Interceptor.*

object CrashPortrayHelper {
    private var crashPortrayConfig: List<CrashPortray>? = null
    private lateinit var application: Application
    private lateinit var actionImpl: IApp

    // 拦截器
    private val logInterceptors = mutableListOf<LogInterceptor<*>>()

    // 将所有日志拦截器传递给链条
    private val interceptorChain = Chain(logInterceptors)

    fun init(application: Application, config: List<CrashPortray>?, actionImpl: IApp) {
        CrashPortrayHelper.application = application
        crashPortrayConfig = config
        CrashPortrayHelper.actionImpl = actionImpl
        CrashUncaughtExceptionHandler.init()
        addInterceptor(LogcatInterceptor())
        addInterceptor(UUIDInterceptor())
        addInterceptor(WriteCacheInterceptor())
        addInterceptor(CleanCacheInterceptor())
        addInterceptor(FinishInterceptor())
    }

    fun addInterceptor(interceptor: LogInterceptor<*>) {
        logInterceptors.add(interceptor)
    }

    fun addFirstInterceptor(interceptor: LogInterceptor<*>) {
        logInterceptors.add(0, interceptor)
    }

    fun removeInterceptor(interceptor: LogInterceptor<*>) {
        logInterceptors.remove(interceptor)
    }

    @Synchronized
    private fun log(
        priority: Int,
        message: Any,
        tag: String,
        crashPortray: CrashPortray,
        vararg args: Any,
    ) {
        // 日志请求传递给链条
        interceptorChain.proceed(tag, message, priority,crashPortray, args)
    }

    fun needHandle(throwable: Throwable): Boolean {
        if (crashPortrayConfig.isNullOrEmpty()) {
            return false
        }

        val config: List<CrashPortray>? = crashPortrayConfig
        if (config.isNullOrEmpty()) {
            return false
        }
        for (i in config.indices) {
            val crashPortray = config[i]
            if (!crashPortray.valid()) {
                continue
            }

            if (crashPortray.appVersion.isNotEmpty()
                && !crashPortray.appVersion.contains(actionImpl.getVersionName(application))
            ) {
                continue
            }

            if (crashPortray.osVersion.isNotEmpty()
                && !crashPortray.osVersion.contains(Build.VERSION.SDK_INT)
            ) {
                continue
            }

            if (crashPortray.model.isNotEmpty()
                && crashPortray.model.firstOrNull { Build.MODEL.equals(it, true) } == null
            ) {
                continue
            }

            val throwableName = throwable.javaClass.simpleName
            val message = throwable.message ?: ""

            if (crashPortray.className.isNotEmpty()
                && crashPortray.className != throwableName
            ) {
                continue
            }

            if (crashPortray.message.isNotEmpty() && !message.contains(crashPortray.message)
            ) {
                continue
            }

            if (crashPortray.stack.isNotEmpty()) {
                var match = false
                throwable.stackTrace.forEach { element ->
                    val str = element.toString()
                    if (crashPortray.stack.find { str.contains(it) } != null) {
                        match = true
                        //找到即退出该循环向后执行
                        return@forEach
                    }
                }
                if (!match) {
                    continue
                }
            }

            log(0, throwable, crashPortray.className+throwableName, crashPortray)

            return true
        }
        return false
    }
}