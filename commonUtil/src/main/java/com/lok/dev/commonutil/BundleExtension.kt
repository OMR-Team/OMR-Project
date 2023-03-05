package com.lok.dev.commonutil

import android.os.Build
import android.os.Bundle
import android.os.Parcelable

fun Bundle?.safeString(key: String, def: String = ""): String = this?.getString(key, def) ?: def

inline fun <reified T : Parcelable> Bundle?.safeParcelable(key: String): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        this?.getParcelable(key, T::class.java)
    } else {
        this?.getParcelable(key)
    }
}