package com.spotlightapps.couchbasenote.data

import android.content.Context
import com.couchbase.lite.CouchbaseLiteException
import com.couchbase.lite.Database
import com.couchbase.lite.DatabaseConfiguration
import com.couchbase.lite.ListenerToken
import com.spotlightapps.couchbasenote.DATABASE_NAME

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

    fun getDatabase(userName: String): Database {
        return database ?: createDatabase(context, userName).also {
            database = it
        }
    }

    private fun createDatabase(context: Context?, userName: String): Database {
        val configuration = DatabaseConfiguration()
        configuration.directory = String.format(
            "%s/%s",
            context?.filesDir,
            userName
        )
        return Database(DATABASE_NAME, configuration)
    }

    fun registerForDatabaseChanges() {
        database?.addChangeListener { change ->
            for (docId in change.documentIDs) {
                val document = database?.getDocument(docId)
                document?.let {
                }
            }
        }
    }

    fun closeDatabaseForUser(userName: String) {
        try {
            database?.let {
                unRegisterForDatabaseChanges()
                database?.close()
                database = null
            }
        } catch (e: CouchbaseLiteException) {
            e.printStackTrace()
        }
    }

    private fun unRegisterForDatabaseChanges() {
        listener?.let {
            database?.removeChangeListener(it)
        }

    }
}