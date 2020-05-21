package com.spotlightapps.couchbasenote.note_list

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.spotlightapps.couchbasenote.AppRepository
import com.spotlightapps.couchbasenote.NOTE_ID
import com.spotlightapps.couchbasenote.R
import com.spotlightapps.couchbasenote.adapters.NoteListAdapter
import com.spotlightapps.couchbasenote.adapters.OnNoteItemClickListener
import com.spotlightapps.couchbasenote.new_note.NewNoteActivity
import com.spotlightapps.couchbasenote.new_note.NoteAction
import kotlinx.android.synthetic.main.activity_note_list.*

class NoteListActivity : AppCompatActivity() {

    private lateinit var viewModel: NoteListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_list)

        val factory = NoteListViewModelFactory(application, AppRepository.getInstance(this))
        viewModel = ViewModelProviders.of(this, factory).get(NoteListViewModel::class.java)

        val adapter = NoteListAdapter(OnNoteItemClickListener { noteId ->
            val intent = Intent(this, NewNoteActivity::class.java).apply {
                action = NoteAction.EDIT.value
                putExtra(NOTE_ID, noteId)
            }
            startActivity(intent)
        })
        rvNoteList.adapter = adapter
        subscribeUI(adapter)

        fab.setOnClickListener {
            val intent = Intent(this, NewNoteActivity::class.java).apply {
                action = NoteAction.CREATE.value
            }
            startActivity(intent)
        }
    }

    private fun subscribeUI(adapter: NoteListAdapter) {
        viewModel.noteList.observe(this, Observer { noteList ->
            adapter.submitList(noteList)
        })

    }
}
