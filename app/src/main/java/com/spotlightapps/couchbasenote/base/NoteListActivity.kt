package com.spotlightapps.couchbasenote.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.spotlightapps.couchbasenote.R

class NoteListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_list)
    }
}
