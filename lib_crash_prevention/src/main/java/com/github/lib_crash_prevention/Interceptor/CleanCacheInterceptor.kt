package com.github.lib_crash_prevention.Interceptor

import com.github.lib_crash_prevention.Chain
import com.github.lib_crash_prevention.CrashModel.CrashPortray
import com.github.lib_crash_prevention.LogInterceptor

class CleanCacheInterceptor: LogInterceptor<Any> {
    override fun log(
        tag: String,
        message: Any,
        priority: Int,
        chain: Chain,
        crashPortray: CrashPortray,
        vararg args: Any
    ) {
        if (enable(crashPortray)) {
            //执行
        }
        chain.proceed(tag, message, priority, crashPortray, args)
    }

    override fun enable(crashPortray: CrashPortray): Boolean {
        if (crashPortray.clearCacheCode == 1) return true
        return false
    }
}