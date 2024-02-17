package com.github.lib_crash_prevention.Interceptor

import com.github.lib_crash_prevention.Chain
import com.github.lib_crash_prevention.CrashModel.CrashPortray
import com.github.lib_crash_prevention.LogInterceptor

//崩溃拦截器，在此设置异常结束后是否销毁
class FinishInterceptor: LogInterceptor<Any> {
    override fun log(
        tag: String,
        message: Any,
        priority: Int,
        chain: Chain,
        crashPortray: CrashPortray,
        vararg args: Any
    ) {
        if (enable(crashPortray)) {
            //让软件崩溃
        }
    }

    override fun enable(crashPortray: CrashPortray): Boolean {
        if (crashPortray.finishPageCode == 1) return true
        return false
    }
}