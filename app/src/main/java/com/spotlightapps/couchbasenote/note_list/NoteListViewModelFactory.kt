package com.spotlightapps.couchbasenote.note_list

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.spotlightapps.couchbasenote.AppRepository

/**
 * Created by Ahmad Jawid Muhammadi on 21/5/20
 */
class NoteListViewModelFactory(
    private val context: Application,
    private val appRepository: AppRepository
) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NoteListViewModel(context, appRepository) as T
    }
}