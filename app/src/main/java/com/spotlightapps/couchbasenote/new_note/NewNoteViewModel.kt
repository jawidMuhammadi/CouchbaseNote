package com.spotlightapps.couchbasenote.new_note

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.spotlightapps.couchbasenote.AppRepository
import com.spotlightapps.couchbasenote.Event
import com.spotlightapps.couchbasenote.data.NoteItem
import com.spotlightapps.couchbasenote.utils.toDataClass
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by Ahmad Jawid Muhammadi on 21/5/20
 */

class NewNoteViewModel(
    private val appRepository: AppRepository
) : ViewModel() {

    private val uiScope = CoroutineScope(Dispatchers.Main)
    var noteId = MutableLiveData<String>()

    private val noteDocument = Transformations.map(noteId) {
        appRepository.getDocumentById(it)
    }

    val noteItem = Transformations.map(noteDocument) {
        it?.toMutable()?.toMap()?.toDataClass<NoteItem>()
    }


    private var _onSaveDone = MutableLiveData(Event(Unit))
    val onSaveDone: LiveData<Event<Unit>> = _onSaveDone


    fun saveNote(tile: String, description: String) {
        val noteItem = NoteItem(
            title = tile, description = description
        )
        uiScope.launch {
            withContext(Dispatchers.IO) {
                appRepository.saveNote(noteItem)
            }
        }
        _onSaveDone.value = Event(Unit)
    }

    fun deleteNote() {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                appRepository.deleteNote(noteDocument.value!!)
            }
        }
    }
}