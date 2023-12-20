package com.example.thefinalproject.util

import android.content.Context
import android.content.SharedPreferences

object SharePref {
    private lateinit var prefe: SharedPreferences

    fun initial(context: Context) {
        prefe = context.getSharedPreferences(Enum.PREF_NAME.value, Context.MODE_PRIVATE)
    }

    fun getPref(key: String, value: String? = null): String? {
        return prefe.getString(key, value)
    }

    fun setPref(key: String, value: String?) {
        val editor: SharedPreferences.Editor = prefe.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun clearPrefs() {
        prefe.edit().clear().apply()
    }

    fun isLoggedIn(): Boolean {
        return getPref(Enum.PREF_NAME.value) != null
    }

    fun setLoginStatus(isLoggedIn: Boolean) {
        val editor: SharedPreferences.Editor = prefe.edit()
        editor.putBoolean("isLoggedIn", isLoggedIn)
        editor.apply()
    }

    enum class Enum(val value: String) {
        PREF_NAME("token")
    }
}
