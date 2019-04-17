package com.kaancaliskan.guvenlinot

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.kaancaliskan.guvenlinot.db.GuvenliNotDatabase
import com.kaancaliskan.guvenlinot.db.Note
import com.kaancaliskan.guvenlinot.db.NotesRepository
import kotlinx.android.synthetic.main.main_activity.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.kaancaliskan.guvenlinot.util.GuvenliNotAdapter
import com.kaancaliskan.guvenlinot.util.SwipeToDeleteCallback
import org.jetbrains.anko.*
/**
 * This activity saves note and encode/decode note.
 */
class MainActivity : AppCompatActivity() {
    private lateinit var adapter: GuvenliNotAdapter
    private lateinit var noteList: MutableList<Note>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        setContentView(R.layout.main_activity)
        setSupportActionBar(main_bar)

        if (!check_for_intent) {
            //For restrict accessing without password check.
            Toast.makeText(this, R.string.restrict_access, Toast.LENGTH_LONG).show()
            finishAffinity()
        }

        val swipeHandler = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deleteNote = noteList[viewHolder.adapterPosition]
                NotesRepository(applicationContext).deleteNote(deleteNote)
                noteList.removeAt(viewHolder.adapterPosition)
                adapter.notifyItemRemoved(viewHolder.adapterPosition)
                adapter.notifyItemRangeChanged(viewHolder.adapterPosition, noteList.size)
                //to change other note's position change accordingly
                isListEmpty()
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recycler_view)

        noteList = NotesRepository(application).getAllNotes()
        isListEmpty()

        val itemDecoration = DividerItemDecoration(applicationContext, DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider)!!)
        recycler_view.addItemDecoration(itemDecoration)
        recycler_view.layoutManager = LinearLayoutManager(applicationContext)
        adapter = GuvenliNotAdapter(noteList) { note -> onItemClick(note) }
        recycler_view.adapter = adapter
        adapter.notifyDataSetChanged()

        add_note_fab.onClick { startActivity<NewNoteActivity>() }
    }

    private fun onItemClick(note: Note?) {
        val id = note?.Id
        val title = note?.noteTitle.toString()
        val content = note?.noteContent.toString()
        val date = note?.date.toString()

        startActivity<UpdateNoteActivity>(NOTE_ID to id, NOTE_TITLE to title, NOTE_CONTENT to content, DATE to date)
    }

    override fun onResume() {
        super.onResume()
        refreshRecyclerView()
    }

    private fun refreshRecyclerView() {
        noteList = NotesRepository(application).getAllNotes()
        isListEmpty()
        adapter = GuvenliNotAdapter(noteList) { note -> onItemClick(note) }
        recycler_view.adapter = adapter
    }

    private fun isListEmpty() {
        if (noteList.isEmpty()) {
            recycler_view.visibility = GONE
            empty_view.visibility = VISIBLE
        } else {
            recycler_view.visibility = VISIBLE
            empty_view.visibility = GONE
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        GuvenliNotDatabase.destroyInstance()
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_change_password -> {
            startActivity<ChangePassword>()
            true
        }
        R.id.action_about -> {
            startActivity<AboutActivity>()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }
}