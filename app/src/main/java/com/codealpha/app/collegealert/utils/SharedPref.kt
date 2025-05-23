package com.codealpha.app.collegealert.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

object SharedPref {
    private lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context) {
        sharedPreferences=
            context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
    }
    fun putString(key: String, value: String) {
        sharedPreferences.edit() { putString(key, value) }
    }
    fun getString(key: String): String? {
        return sharedPreferences!!.getString(key, null)
    }
    fun putBoolean(key: String, value: Boolean) {
        sharedPreferences!!.edit { putBoolean(key, value) }
    }
    fun getBoolean(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }
    fun contains(key: String): Boolean{
        return sharedPreferences.contains(key)
    }
    fun clear(){
        sharedPreferences.edit().clear().apply()

    }
}