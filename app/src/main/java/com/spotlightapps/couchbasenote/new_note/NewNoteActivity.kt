package com.spotlightapps.couchbasenote.new_note

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.spotlightapps.couchbasenote.AppRepository
import com.spotlightapps.couchbasenote.R
import kotlinx.android.synthetic.main.activity_new_note.*

class NewNoteActivity : AppCompatActivity() {

    private lateinit var viewModel: NewNoteViewModel
    private var noteAction: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_note)

        noteAction = intent.action

        val factory = NewNoteViewModelFactory(AppRepository.getInstance(this))
        viewModel = ViewModelProviders.of(this, factory).get(NewNoteViewModel::class.java)
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
                viewModel.saveNote(etTitle.text.toString(), etDescription.text.toString())
                true
            }
            R.id.menu_delete -> {
                viewModel.deleteNote()
                true
            }
            else -> false
        }
    }
}
