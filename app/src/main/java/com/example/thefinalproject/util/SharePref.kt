

import android.content.Context
import android.content.SharedPreferences


object SharePref {
    private lateinit var prefe: SharedPreferences



    fun initial(context: Context) {
        prefe = context.getSharedPreferences(Enum.PREF_NAME.value, Context.MODE_PRIVATE)
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

    enum class Enum(val value:String) {
        PREF_NAME("token")
    }
}