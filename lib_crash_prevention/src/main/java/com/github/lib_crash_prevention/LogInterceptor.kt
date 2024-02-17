package com.github.lib_crash_prevention

import com.github.lib_crash_prevention.CrashModel.CrashPortray

// 处理泛型数据的日志拦截器
interface LogInterceptor<T> {
    // 日志处理逻辑
    fun log(tag: String, message: T, priority: Int, chain: Chain,crashPortray: CrashPortray, vararg args: Any)
    // 是否启动当前拦截器
    fun enable(crashPortray: CrashPortray):Boolean
}
