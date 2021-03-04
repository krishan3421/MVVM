package com.mvvm.data.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

private const val KEY_SAVED_AT="key_saved_at"
class PreferenceProvider(
        context: Context
) {
    private val applicationContext=context.applicationContext

    private val preference:SharedPreferences
    get() = PreferenceManager.getDefaultSharedPreferences(applicationContext)

    fun saveLastsaveAt(saveAt:String){
        preference.edit().putString(
                KEY_SAVED_AT,
                saveAt
        ).apply()
    }

    fun getLastSavedAt():String?{
       return preference.getString(KEY_SAVED_AT,null)
    }
}