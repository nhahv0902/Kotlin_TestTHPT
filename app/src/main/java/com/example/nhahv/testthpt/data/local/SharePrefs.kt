package com.example.nhahv.testthpt.data.local

import android.content.Context
import android.content.SharedPreferences


/**
 * Created by nhahv on 09/11/2017.
 */
class SharePrefs private constructor(context: Context) {


    val sharePrefs: SharedPreferences = context.getSharedPreferences("Test_THPT", Context.MODE_PRIVATE)

    companion object {
        private var INSTANCE: SharePrefs? = null

        fun getInstance(context: Context): SharePrefs =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: SharePrefs(context).also { INSTANCE = it }
                }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> get(key: String, type: TypeSharePrefs): T? =
            when (type) {
                TypeSharePrefs.STRING -> sharePrefs.getString(key, null) as T
                TypeSharePrefs.BOOLEAN -> sharePrefs.getBoolean(key, false) as T
                TypeSharePrefs.INT -> sharePrefs.getInt(key, 0) as T
                TypeSharePrefs.FLOAT -> sharePrefs.getFloat(key, 0f) as T
                TypeSharePrefs.DOUBLE -> sharePrefs.getFloat(key, 0f).toDouble() as T
                else -> null as T
            }

    fun <T> put(key: String, value: T) {
        when (value) {
            is String -> sharePrefs.edit().putString(key, value).apply()
            is Int -> sharePrefs.edit().putInt(key, value).apply()
            is Float -> sharePrefs.edit().putFloat(key, value).apply()
            is Boolean -> sharePrefs.edit().putBoolean(key, value).apply()
            is Double -> sharePrefs.edit().putFloat(key, value.toFloat()).apply()
        }
    }

    fun remove (key: String){
        sharePrefs.edit().remove(key).apply()
    }

    enum class TypeSharePrefs {
        STRING, INT, LONG, FLOAT, BOOLEAN, DOUBLE
    }
}


