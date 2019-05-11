package com.kaancaliskan.guvenlinot.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kaancaliskan.guvenlinot.R
import com.kaancaliskan.guvenlinot.db.Note
import kotlinx.android.synthetic.main.item_note.view.*

/**
 * Adapter for the recyclerView to be shown in the main activity
 */
class GuvenliNotAdapter(private var noteList: MutableList<Note>, private val listener: (Note) -> Unit) : RecyclerView.Adapter<GuvenliNotAdapter.MyViewHolder>() {

    /**
     * override the onCreateViewHolder method
     */
    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): MyViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_note, viewGroup, false)
        return MyViewHolder(view)
    }

    /**
     * override the onBindViewHolder method
     */
    override fun onBindViewHolder(viewHolder: MyViewHolder, position: Int) {
        val note = noteList[position]
        viewHolder.noteTitle.text = Hash.decode(note.noteTitle)
        viewHolder.noteContent.text = Hash.decode(note.noteContent)
        viewHolder.date.text = note.date
        viewHolder.itemView.setOnClickListener { listener(note) }
    }

    /**
     * override the getItemCount method
     */
    override fun getItemCount(): Int = noteList.size

    /**
     * Update the list with given list
     */
    fun updateList(list: MutableList<Note>) {
        noteList = list
        notifyDataSetChanged()
    }

    /**
     * ViewHolder class to cache the views for fast accessing
     */
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var noteTitle: TextView = itemView.item_note_title
        var noteContent: TextView = itemView.item_note_content
        var date: TextView = itemView.date
    }
}
