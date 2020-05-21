package com.spotlightapps.couchbasenote.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.spotlightapps.couchbasenote.R
import com.spotlightapps.couchbasenote.data.NoteItem
import kotlinx.android.synthetic.main.note_item_layout.view.*

/**
 * Created by Ahmad Jawid Muhammadi on 21/5/20
 */
class NoteListAdapter(private val onNoteItemClickListener: OnNoteItemClickListener) :
    ListAdapter<NoteItem, NoteViewHolder>(NoteListItemCallback()) {

    companion object {
        class NoteListItemCallback : DiffUtil.ItemCallback<NoteItem>() {
            override fun areItemsTheSame(oldItem: NoteItem, newItem: NoteItem): Boolean {
                return oldItem.noteId == newItem.noteId
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: NoteItem, newItem: NoteItem): Boolean {
                return oldItem === newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(
            noteItem = getItem(position),
            onNoteItemClickListener = onNoteItemClickListener
        )
    }

}

class NoteViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

    companion object {
        fun from(parent: ViewGroup): NoteViewHolder {
            return NoteViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.note_item_layout, parent, false)
            )
        }
    }

    fun bind(noteItem: NoteItem, onNoteItemClickListener: OnNoteItemClickListener) {
        itemView.tvTitle.text = noteItem.title
        itemView.tvDescription.text = noteItem.description
    }
}

class OnNoteItemClickListener(val clickListener: (id: Long) -> Unit) {
    fun onClick(id: Long) = clickListener(id)
}