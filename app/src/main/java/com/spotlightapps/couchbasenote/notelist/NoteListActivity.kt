package com.spotlightapps.couchbasenote.notelist

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.spotlightapps.couchbasenote.AppRepository
import com.spotlightapps.couchbasenote.R
import com.spotlightapps.couchbasenote.adapters.NoteListAdapter
import com.spotlightapps.couchbasenote.adapters.OnNoteItemClickListener
import kotlinx.android.synthetic.main.activity_note_list.*

class NoteListActivity : AppCompatActivity() {

    private lateinit var viewModel: NoteListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_list)

        val factory = NoteListViewModelFactory(AppRepository.getInstance(this))
        viewModel = ViewModelProviders.of(this, factory).get(NoteListViewModel::class.java)

        val adapter = NoteListAdapter(OnNoteItemClickListener { position ->
            Toast.makeText(this, "$position item clicked", Toast.LENGTH_SHORT).show()
        })
        rvNoteList.adapter = adapter
        subscribeUI(adapter)
    }

    private fun subscribeUI(adapter: NoteListAdapter) {
        viewModel.noteList.observe(this, Observer { noteList ->
            adapter.submitList(noteList)
        })
    }
}
