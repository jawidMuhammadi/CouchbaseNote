package com.spotlightapps.couchbasenote.note_list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.couchbase.lite.ListenerToken
import com.spotlightapps.couchbasenote.AppRepository
import com.spotlightapps.couchbasenote.data.NoteItem
import com.spotlightapps.couchbasenote.utils.PrefUtils
import com.spotlightapps.couchbasenote.utils.toDataClass

/**
 * Created by Ahmad Jawid Muhammadi on 21/5/20
 */

class NoteListViewModel(
    context: Application,
    private val appRepository: AppRepository
) : AndroidViewModel(context) {

    private var _noteList = MutableLiveData<List<NoteItem>>()
    val noteList: LiveData<List<NoteItem>>
        get() = _noteList

    private lateinit var listener: ListenerToken

    init {
        _noteList.value =
            appRepository.getAllNotes(PrefUtils.getInstance(context).getCurrentUser()!!)

        registerForDatabaseChanges()
    }

    private fun registerForDatabaseChanges() {
        val list = _noteList.value as ArrayList
        listener = appRepository.databaseManager.getDatabase().addChangeListener { change ->
            for (docId in change.documentIDs) {
                val document = appRepository.getDocumentById(docId)
                document?.let {
                    list.add(it.toMap().toDataClass())
                }
            }
        }
        _noteList.value = list
    }

    override fun onCleared() {
        super.onCleared()
        appRepository.databaseManager.getDatabase().removeChangeListener(listener)
    }

}