package com.spotlightapps.couchbasenote

import android.content.Context
import com.spotlightapps.couchbasenote.data.DatabaseManager

/**
 * Created by Ahmad Jawid Muhammadi on 19/5/20
 */
class AppRepository private constructor(private val databaseManager: DatabaseManager) {

    companion object {
        @Volatile
        private var INSTANCE: AppRepository? = null

        fun getInstance(context: Context, userName: String): AppRepository {
            return AppRepository(DatabaseManager)
        }
    }
}