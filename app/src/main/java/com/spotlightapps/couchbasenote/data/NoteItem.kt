package com.spotlightapps.couchbasenote.data

import java.util.*

/**
 * Created by Ahmad Jawid Muhammadi on 21/5/20
 */

data class NoteItem(
    var noteId: String = UUID.randomUUID().toString(),
    var title: String?,
    var description: String?
)