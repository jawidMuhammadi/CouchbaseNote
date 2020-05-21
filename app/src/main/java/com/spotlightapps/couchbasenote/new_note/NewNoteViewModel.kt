package com.spotlightapps.couchbasenote.new_note

import androidx.lifecycle.ViewModel
import com.spotlightapps.couchbasenote.AppRepository
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
    var noteId: String? = null


    fun saveNote(tile: String, description: String) {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                appRepository.saveNote()
            }
        }
    }

    fun deleteNote() {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                appRepository.deleteNote()
            }
        }
    }
}