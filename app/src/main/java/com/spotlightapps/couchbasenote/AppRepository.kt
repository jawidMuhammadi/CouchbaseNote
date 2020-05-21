package com.spotlightapps.couchbasenote

import android.content.Context
import com.couchbase.lite.*
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

    private fun getDatabase(userName: String = ""): Database? {
        return databaseManager.getDatabase()
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
        val map = document?.toMap()
        return map?.toDataClass()

    }

    fun getAllNotes(userName: String): List<NoteItem>? {
        val list = ArrayList<NoteItem>()
        getDatabase(userName)?.let {
            val query = QueryBuilder
                .select(SelectResult.all())
                .from(DataSource.database(it))

            val resultSet = query.execute()
            var result: Result? = resultSet.next()
            while (result != null) {
                val valueMap = result.getDictionary(getDatabase(userName)!!.name)
                val noteItem = valueMap.toMap().toDataClass<NoteItem>()
                list.add(noteItem)
                result = resultSet.next()
            }

//            query.addChangeListener { change ->
//                val resultSet = change.results
//                var result: Result? = resultSet.next()
//                while (result != null) {
//                    val valueMap = result.getDictionary(getDatabase(userName)!!.name)
//                    val noteItem = valueMap.toMap().toDataClass<NoteItem>()
//                    list.add(noteItem)
//                    result = resultSet.next()
//                }
//            }
        }
        return list
    }

    fun saveNote(noteItem: NoteItem) {
        val noteMap = noteItem.serializeToMap()
        val document = MutableDocument(noteMap)
        document.setValue("noteId", document.id)
        try {
            getDatabase()?.save(document)
        } catch (e: CouchbaseLiteException) {
            e.printStackTrace()
        }
    }

    fun deleteNote(document: Document) {
        databaseManager.getDatabase().delete(document)
    }

    fun getDocumentById(docId: String): Document? {
        return databaseManager.getDatabase().getDocument(docId)
    }
}