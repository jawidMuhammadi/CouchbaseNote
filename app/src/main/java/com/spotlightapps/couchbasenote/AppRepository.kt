package com.spotlightapps.couchbasenote

import android.content.Context
import com.couchbase.lite.CouchbaseLiteException
import com.couchbase.lite.Database
import com.couchbase.lite.MutableDocument
import com.spotlightapps.couchbasenote.data.DatabaseManager
import com.spotlightapps.couchbasenote.data.NoteItem
import com.spotlightapps.couchbasenote.data.UserProfile
import com.spotlightapps.couchbasenote.data.UserType
import com.spotlightapps.couchbasenote.utils.serializeToMap
import com.spotlightapps.couchbasenote.utils.toDataClass

/**
 * Created by Ahmad Jawid Muhammadi on 19/5/20
 */
class AppRepository private constructor(
    private val context: Context?,
    val databaseManager: DatabaseManager
) {

    companion object {

        fun getInstance(context: Context?): AppRepository =
            AppRepository(context, DatabaseManager.getInstance(context))
    }

    private fun getDatabase(userName: String): Database? {
        return databaseManager.getDatabase(userName)
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
                userName,
                profileMap
            )
            try {
                getDatabase(userName)?.save(document)
            } catch (e: CouchbaseLiteException) {
                e.printStackTrace()
            }
        }

    }

    fun getUser(userName: String): UserProfile? {
        val document = getDatabase(userName)?.getDocument(userName)
        document?.let {
            val map = HashMap<String, Any>()
            map["type"] = document.getString("type")
            map["name"] = document.getString("name")
            map["password"] = document.getString("password")
            map["address"] = document.getString("address")
            return map.toDataClass()
        } ?: return null
    }

    fun getAllNotes(): List<NoteItem>? {
        val list = ArrayList<NoteItem>()
        list.add(NoteItem(1, "First title", "This is my first note written ever"))
        list.add(NoteItem(2, "First title", "This is my first note written ever"))
        list.add(NoteItem(3, "First title", "This is my first note written ever"))
        return list
    }
}