package com.github.lib_crash_prevention

import com.github.lib_crash_prevention.CrashModel.CrashPortray

class Chain(
    // 责任链持有 star 投影的拦截器
    private val interceptors: List<LogInterceptor<*>>,
    private val index: Int = 0
) {

    // 责任链向后传递 Any 类型的日志
    fun proceed(
        tag: String,
        message: Any,
        priority: Int,
        crashPortray: CrashPortray,
        vararg args: Any
    ) {
        val next = Chain(interceptors, index + 1)
        try {
            // 将 star 投影的拦截器强转为 Any 类型
            (interceptors.getOrNull(index) as? LogInterceptor<Any>)?.log(
                tag,
                message,
                priority,
                next,
                crashPortray,
                *args
            )
        } catch (e: Exception) {

        }
    }

}

