package com.spotlightapps.couchbasenote.new_note

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.spotlightapps.couchbasenote.AppRepository
import com.spotlightapps.couchbasenote.EventObserver
import com.spotlightapps.couchbasenote.NOTE_ID
import com.spotlightapps.couchbasenote.R
import com.spotlightapps.couchbasenote.utils.PrefUtils
import kotlinx.android.synthetic.main.activity_new_note.*

class NewNoteActivity : AppCompatActivity() {

    private lateinit var viewModel: NewNoteViewModel
    private var noteAction: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_note)

        val factory = NewNoteViewModelFactory(AppRepository.getInstance(this))
        viewModel = ViewModelProviders.of(this, factory).get(NewNoteViewModel::class.java)

        noteAction = intent.action
        if (noteAction == NoteAction.EDIT.value) {
            viewModel.noteId.value = intent.getStringExtra(NOTE_ID)
        }

        subscribeUI()
    }

    private fun subscribeUI() {
        viewModel.onSaveDone.observe(this, EventObserver {
            // finish()
        })
        viewModel.noteItem.observe(this, Observer {
            it?.let {
                etTitle.setText(it.title)
                etDescription.setText(it.description)
                supportActionBar?.title = it.noteId
            }
        })

    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if (noteAction == NoteAction.CREATE.value) {
            menu?.findItem(R.id.menu_delete)?.isVisible = false
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.new_note_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_save -> {
                viewModel.saveNote(
                    PrefUtils.getInstance(this).getCurrentUser()!!,
                    etTitle.text.toString(),
                    etDescription.text.toString()
                )
                finish()
                true
            }
            R.id.menu_delete -> {
                viewModel.deleteNote()
                finish()
                true
            }
            else -> false
        }
    }
}
