package com.spotlightapps.couchbasenote.utils

import android.content.Context
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import com.spotlightapps.couchbasenote.KEY_USER_NAME

/**
 * Created by Ahmad Jawid Muhammadi on 19/5/20
 */

class PreferenceUtils private constructor(private val context: Context) {

    companion object {
        fun getInstance(context: Context) = PreferenceUtils(context)
    }

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

    fun getCurrentUser(): String? = PreferenceManager.getDefaultSharedPreferences(context)
        .getString(KEY_USER_NAME, "")
}