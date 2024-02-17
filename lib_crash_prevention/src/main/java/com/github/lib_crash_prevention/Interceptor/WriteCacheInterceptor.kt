package com.github.lib_crash_prevention.Interceptor


import com.github.lib_crash_prevention.Chain
import com.github.lib_crash_prevention.CrashModel.CrashPortray
import com.github.lib_crash_prevention.LogInterceptor
import com.tencent.mmkv.MMKV
import com.google.protobuf.Message

//持久化存储拦截器
class WriteCacheInterceptor : LogInterceptor<Log<Message>> {
    companion object {
        val mmkv by lazy { MMKV.defaultMMKV() }
    }

    override fun log(
        tag: String,
        message: Log<Message>,
        priority: Int,
        chain: Chain,
        crashPortray: CrashPortray,
        vararg args: Any
    ) {
        // 将日志持久化为二进制
        if (enable(crashPortray)) mmkv.encode(message.id, message.data.toByteArray())
        // 将日志原样传递给下一个拦截器
        chain.proceed(tag, message, priority,crashPortray)
    }

    override fun enable(crashPortray: CrashPortray): Boolean {
        return true
    }
}
