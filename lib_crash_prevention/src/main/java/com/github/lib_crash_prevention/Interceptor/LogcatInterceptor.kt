package com.github.lib_crash_prevention.Interceptor

import android.util.Log
import com.github.lib_crash_prevention.Chain
import com.github.lib_crash_prevention.CrashModel.CrashPortray
import com.github.lib_crash_prevention.LogInterceptor
import java.io.PrintWriter
import java.io.StringWriter

//打印和异常拦截器
class LogcatInterceptor : LogInterceptor<Any> {
    override fun log(tag: String, message: Any, priority: Int, chain: Chain,crashPortray: CrashPortray, vararg args: Any) {
        // 将日志输出到 logcat
        if (enable(crashPortray)) Log.println(priority, tag, getFormatLog(message, *args))
        chain.proceed(tag, message, priority, crashPortray, args)
    }

    // 格式化日志
    private fun getFormatLog(message: Any, vararg args: Any) =
        // 如果日志是 Throwable 则追加堆栈信息
        if (message is Throwable)
            getStackTraceString(message)
        else
        // 如果 format arguments 不为空则格式化
            if (args.isNotEmpty()) message.toString().format(args)
            // 否则直接将结构体转换为string
            else message.toString()

    // 获取堆栈信息
    private fun getStackTraceString(t: Throwable): String {
        val sw = StringWriter(256)
        val pw = PrintWriter(sw, false)
        t.printStackTrace(pw)
        pw.flush()
        return sw.toString()
    }

    // 支持 format arguments
    private fun String.format(args: Array<out Any>) =
        if (args.isEmpty()) this else String.format(this, *args)

    override fun enable(crashPortray: CrashPortray): Boolean {
        return true
    }
}
