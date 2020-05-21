package com.spotlightapps.couchbasenote.data

import android.content.Context
import com.couchbase.lite.CouchbaseLiteException
import com.couchbase.lite.Database
import com.couchbase.lite.DatabaseConfiguration
import com.couchbase.lite.ListenerToken
import com.spotlightapps.couchbasenote.NOTE_DATABASE_NAME

/**
 * Created by Ahmad Jawid Muhammadi on 19/5/20
 */


class DatabaseManager private constructor(private val context: Context?) {

    @Volatile
    private var database: Database? = null

    private var listener: ListenerToken? = null

    companion object {
        @Volatile
        private var INSTANCE: DatabaseManager? = null

        fun getInstance(context: Context?): DatabaseManager {
            return INSTANCE ?: DatabaseManager(context).also {
                INSTANCE = it
            }
        }
    }

    fun getDatabase(userName: String = "jawid"): Database {
        return database ?: createDatabase(context, userName, NOTE_DATABASE_NAME).also {
            database = it
            //registerForDatabaseChanges()
        }
    }
//    fun getNoteDatabase(userName: String):Database{
//        return database?:createDatabase(context)
//    }

    private fun createDatabase(
        context: Context?,
        userName: String,
        databaseName: String
    ): Database {
        val configuration = DatabaseConfiguration()
        configuration.directory = String.format(
            "%s/%s",
            context?.filesDir,
            userName
        )
        return Database(databaseName, configuration)

    }

    fun closeDatabaseForUser() {
        try {
            database?.let {
                database?.close()
                database = null
            }
        } catch (e: CouchbaseLiteException) {
            e.printStackTrace()
        }
    }
}