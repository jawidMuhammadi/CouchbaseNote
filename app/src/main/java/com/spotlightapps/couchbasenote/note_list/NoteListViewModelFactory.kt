package com.spotlightapps.couchbasenote.note_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.spotlightapps.couchbasenote.AppRepository

/**
 * Created by Ahmad Jawid Muhammadi on 21/5/20
 */
class NoteListViewModelFactory(private val appRepository: AppRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NoteListViewModel(appRepository) as T
    }
}