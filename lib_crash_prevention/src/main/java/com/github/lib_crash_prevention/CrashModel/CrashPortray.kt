package com.github.lib_crash_prevention.CrashModel

import com.google.gson.annotations.SerializedName


data class CrashPortray(
    @SerializedName("class_name")
    val className: String = "",
    val message: String = "",
    val stack: List<String> = emptyList(),
    @SerializedName("app_version")
    val appVersion: List<String> = emptyList(),
    @SerializedName("os_version")
    val osVersion: List<Int> = emptyList(),
    val model: List<String> = emptyList(),
    val type: String = "all",
    @SerializedName("clear_cache")
    val clearCacheCode: Int = 0,
    @SerializedName("finish_page")
    val finishPageCode: Int = 0,
    val toast: String = ""
) {
    constructor() : this(
        "",
        "",
        emptyList(),
        emptyList(),
        emptyList(),
        emptyList(),
        "all",
        0,
        0,
        ""
    )

    fun valid(): Boolean {
        return className.isNotEmpty() || message.isNotEmpty() || stack.isNotEmpty()
    }
}