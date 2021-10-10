package com.astery.vtb.repository.preferences

import android.content.Context
import com.astery.vtb.model.GameValue

object TransportPreferences {
    private const val PREFERENCE_NAME = "preference"
    private fun setPref(context: Context, pref: String, value: Int) {
        context.getSharedPreferences(PREFERENCE_NAME, 0).edit().putInt(pref, value).apply()
    }

    private fun setPref(context: Context, pref: String, value: Boolean) {
        context.getSharedPreferences(PREFERENCE_NAME, 0).edit().putBoolean(pref, value).apply()
    }

    private fun setPref(context: Context, pref: String, value: String) {
        context.getSharedPreferences(PREFERENCE_NAME, 0).edit().putString(pref, value).apply()
    }

    private fun getInt(context: Context, pref: String): Int {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).getInt(pref, 0)
    }

    private fun getBoolean(context: Context, pref: String): Boolean {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).getBoolean(pref, false)
    }

    private fun getString(context: Context, pref: String): String? {
        return context.getSharedPreferences(PREFERENCE_NAME, 0).getString(pref, "")
    }


    fun getValue(context:Context, valueType:GameValue):Int{
        return getInt(context, valueType.name)
    }
    fun setValue(context:Context, valueType:GameValue, value:Int){
        setPref(context, valueType.name, value)
    }
}