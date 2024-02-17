package com.github.lib_crash_prevention

import android.content.Context
import java.io.File


interface IApp {

    fun showToast(context: Context, msg: String)
    fun getVersionName(context: Context): String

}