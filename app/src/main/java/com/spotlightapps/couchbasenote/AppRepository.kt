package com.spotlightapps.couchbasenote

import android.content.Context
import com.couchbase.lite.Database
import com.couchbase.lite.MutableDocument
import com.spotlightapps.couchbasenote.data.DatabaseManager
import com.spotlightapps.couchbasenote.data.UserProfile
import com.spotlightapps.couchbasenote.data.UserType
import com.spotlightapps.couchbasenote.utils.PreferenceUtils
import com.spotlightapps.couchbasenote.utils.serializeToMap

/**
 * Created by Ahmad Jawid Muhammadi on 19/5/20
 */
class AppRepository private constructor(
    private val context: Context?,
    private val databaseManager: DatabaseManager
) {

    companion object {
        @Volatile
        private var INSTANCE: AppRepository? = null

        fun getInstance(context: Context?): AppRepository =
            AppRepository(context, DatabaseManager.getInstance(context))

        private fun getDatabase(userName: String): Database? {
            return INSTANCE?.databaseManager?.getDatabase(userName)
        }
    }

    fun saveUser(userName: String, password: String) {
        val profile = UserProfile(
            type = UserType.USER,
            name = userName,
            password = password
        )
        val profileMap = profile.serializeToMap()
        context?.let {
            val document = MutableDocument(
                PreferenceUtils.getInstance(context).getCurrentUser(),
                profileMap
            )
            getDatabase(userName)?.save(document)
        }


    }
}