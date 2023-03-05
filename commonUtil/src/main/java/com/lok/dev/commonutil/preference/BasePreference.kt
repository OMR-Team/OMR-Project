package com.lok.dev.commonutil.preference

import android.content.SharedPreferences
import androidx.core.content.edit

abstract class BasePreference(
    private val sharedPreferences: SharedPreferences
) {

    protected fun get(key: String, def: Boolean): Boolean =
        sharedPreferences.getBoolean(key, def)

    protected fun get(key: String, def: Int): Int =
        sharedPreferences.getInt(key, def)

    protected fun get(key: String, def: Float): Float =
        sharedPreferences.getFloat(key, def)

    protected fun get(key: String, def: Long): Long =
        sharedPreferences.getLong(key, def)

    protected fun get(key: String, def: String = ""): String =
        sharedPreferences.getString(key, def) ?: def

    protected fun getSet(key: String, def: Set<String> = setOf()): Set<String> =
        sharedPreferences.getStringSet(key, def) ?: def

    protected fun put(key: String, value: Boolean) =
        sharedPreferences.edit {
            putBoolean(key, value)
        }

    protected fun put(key: String, value: Int) =
        sharedPreferences.edit {
            putInt(key, value)
        }

    protected fun put(key: String, value: Float) =
        sharedPreferences.edit {
            putFloat(key, value)
        }

    protected fun put(key: String, value: Long) =
        sharedPreferences.edit {
            putLong(key, value)
        }

    protected fun put(key: String, value: String) =
        sharedPreferences.edit {
            putString(key, value)
        }

    protected fun put(key: String, value: Set<String>) =
        sharedPreferences.edit {
            putStringSet(key, value)
        }

    protected fun contains(key: String): Boolean =
        sharedPreferences.contains(key)

}