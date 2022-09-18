package com.hvasoftware.wikifilm.help

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.hvasoftware.wikifilm.base.MyApplication


class SharedPrefs private constructor() {

    private val mSharedPreferences: SharedPreferences =
        MyApplication.self()!!.getSharedPreferences(Constants.APP_NAME, Context.MODE_PRIVATE)

    @Suppress("UNCHECKED_CAST")
    operator fun <T> get(key: String?, anonymousClass: Class<T>, defaultValue: T): T? {
        return when (anonymousClass) {
            String::class.java -> {
                mSharedPreferences.getString(key, defaultValue as String) as T?
            }
            Boolean::class.java -> {
                java.lang.Boolean.valueOf(
                    mSharedPreferences.getBoolean(
                        key,
                        (defaultValue as Boolean)
                    )
                ) as T
            }
            Float::class.java -> {
                java.lang.Float.valueOf(
                    mSharedPreferences.getFloat(
                        key,
                        (defaultValue as Float)
                    )
                ) as T
            }
            Int::class.java -> {
                Integer.valueOf(mSharedPreferences.getInt(key, (defaultValue as Int))) as T
            }
            Long::class.java -> {
                java.lang.Long.valueOf(
                    mSharedPreferences.getLong(
                        key,
                        (defaultValue as Long)
                    )
                ) as T
            }
            else -> {
                Gson().fromJson(mSharedPreferences.getString(key, ""), anonymousClass)
            }
        }
    }

    fun <T> put(key: String, data: T) {
        val editor = mSharedPreferences.edit()
        when (data) {
            is String -> editor.putString(key, data as String)
            is Boolean -> editor.putBoolean(key, data as Boolean)
            is Float -> editor.putFloat(key, data as Float)
            is Int -> editor.putInt(key, data as Int)
            is Long -> editor.putLong(key, data as Long)
            else -> editor.putString(key, Gson().toJson(data))
        }
        editor.apply()
    }

    fun clearCache() {
        mSharedPreferences.edit().clear().apply()
    }

    companion object {
        private var mInstance: SharedPrefs? = null
        val instance: SharedPrefs
            get() {
                if (mInstance == null) {
                    mInstance = SharedPrefs()
                }
                return mInstance as SharedPrefs
            }
    }
}