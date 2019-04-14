package com.kaancaliskan.guvenlinot

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.kaancaliskan.guvenlinot.db.Note
import org.jetbrains.anko.find

/**
 * Adapter for the recyclerView to be shown in the main activity
 */
class GuvenliNotAdapter(private val noteList: MutableList<Note>, private val listener: (Note) -> Unit) : RecyclerView.Adapter<GuvenliNotAdapter.MyViewHolder>() {
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
     * ViewHolder class to cache the views for fast accessing
     */
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var noteTitle: TextView = itemView.find(R.id.item_note_title)
        var noteContent: TextView = itemView.find(R.id.item_note_content)
        var date: TextView = itemView.find(R.id.date)
    }
}