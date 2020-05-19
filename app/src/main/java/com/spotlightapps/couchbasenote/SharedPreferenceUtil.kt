package com.spotlightapps.couchbasenote

import android.content.Context
import androidx.core.content.edit
import androidx.preference.PreferenceManager

/**
 * Created by Ahmad Jawid Muhammadi on 19/5/20
 */

class SharedPreferenceUtil private constructor(private val context: Context) {

    fun getInstance(context: Context) = SharedPreferenceUtil(context)

    fun addString(key: String, value: String) {
        val defaultPref = PreferenceManager.getDefaultSharedPreferences(context)
        defaultPref?.edit {
            putString(key, value)
        }
    }

    fun getString(key: String): String? {
        val defaultPref = PreferenceManager.getDefaultSharedPreferences(context)
        return defaultPref.getString(key, "")
    }
}