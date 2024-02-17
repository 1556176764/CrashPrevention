package com.github.lib_crash_prevention

import android.app.Application
import android.content.Context
import com.github.lib_crash_prevention.CrashModel.CrashPortray


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        CrashPortrayHelper.init(this, getCrashPortrayConfig(), getAppImpl())
    }

    fun getCrashPortrayConfig(): List<CrashPortray>? {
        //在此预定义所需处理异常，并以CrashPortray形式注册
        return ArrayList()
    }

    fun getAppImpl(): IApp {
        return object : IApp {
            override fun showToast(context: Context, msg: String) {

            }

            override fun getVersionName(context: Context): String {
                return ""
            }
        }
    }

}