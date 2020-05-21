package com.spotlightapps.couchbasenote.note_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spotlightapps.couchbasenote.AppRepository
import com.spotlightapps.couchbasenote.data.NoteItem

/**
 * Created by Ahmad Jawid Muhammadi on 21/5/20
 */

class NoteListViewModel(private val appRepository: AppRepository) : ViewModel() {

    private var _noteList = MutableLiveData<List<NoteItem>>()
    val noteList: LiveData<List<NoteItem>>
        get() = _noteList

    init {
        _noteList.value = appRepository.getAllNotes()
    }
}