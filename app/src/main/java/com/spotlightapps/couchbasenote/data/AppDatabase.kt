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


object AppDatabase {

    @Volatile
    private var INSTANCE: Database? = null

    private var listener: ListenerToken? = null

    fun getInstance(context: Context, userName: String): Database {
        return INSTANCE ?: createDatabase(context, userName).also {
            INSTANCE = it
        }
    }

    private fun createDatabase(context: Context, userName: String): Database {
        val configuration = DatabaseConfiguration()
        configuration.directory = String.format(
            "%s/%s",
            context.filesDir,
            userName
        )
        return Database(DATABASE_NAME, configuration)
    }

    fun registerForDatabaseChanges() {
        INSTANCE?.addChangeListener { change ->
            for (docId in change.documentIDs) {
                val document = INSTANCE?.getDocument(docId)
                document?.let {
                }
            }
        }
    }

    fun closeDatabaseForUser(userName: String) {
        try {
            INSTANCE?.let {
                unRegisterForDatabaseChanges()
                INSTANCE?.close()
                INSTANCE = null
            }
        } catch (e: CouchbaseLiteException) {
            e.printStackTrace()
        }
    }

    private fun unRegisterForDatabaseChanges() {
        listener?.let {
            INSTANCE?.removeChangeListener(it)
        }

    }
}