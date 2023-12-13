package com.example.thefinalproject.util

import android.content.Context
import android.content.SharedPreferences

object SharePref {
    private lateinit var prefe: SharedPreferences
    private const val PREF_NAME = "token"
    private const val KEY_LOGIN = "isLogin"


    fun initial(context: Context) {
        prefe = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun getPref(key: String, value: String? = null): String?{
        return prefe.getString(key, value)
    }



    fun setPref(key: String, value: Boolean) {
        val editor: SharedPreferences.Editor = prefe.edit()
        with(editor){
            putBoolean(key, value)
            apply()
            commit()
        }
    }
}