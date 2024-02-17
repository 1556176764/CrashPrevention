package com.github.lib_crash_prevention.Interceptor

import com.github.lib_crash_prevention.Chain
import com.github.lib_crash_prevention.CrashModel.CrashPortray
import com.github.lib_crash_prevention.LogInterceptor
import java.util.*

//标识拦截器
class UUIDInterceptor : LogInterceptor<Any> {
    override fun log(tag: String, message: Any, priority: Int, chain: Chain,crashPortray: CrashPortray, vararg args: Any) {
        if (enable(crashPortray)) {
            // 把每条日志包装成带唯一标识符的日志
            val log = Log(UUID.randomUUID().toString(), message)
            // 将日志沿责任链传递
            chain.proceed(tag, log, priority, crashPortray, args)
        }
    }

    override fun enable(crashPortray: CrashPortray): Boolean {
        return true
    }
}

// 带唯一标识符的日志
data class Log<T>(val id: String, val data: T)
