package com.kaancaliskan.guvenlinot

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.kaancaliskan.guvenlinot.db.GuvenliNotDatabase
import com.kaancaliskan.guvenlinot.db.Note
import com.kaancaliskan.guvenlinot.db.NotesRepository
import kotlinx.android.synthetic.main.main_activity.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity
/**
 * This activity saves note and encode/decode note.
 */
class MainActivity : AppCompatActivity() {
    private lateinit var adapter: GuvenliNotAdapter
    private lateinit var noteList: MutableList<Note>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setSupportActionBar(main_bar)
        add_note_fab.setColorFilter(Color.WHITE)

        /**
         * TO DO
         *
         * add buttons to cards
         * fix fab icons
         * change firstlogin
         * nested for changepassword and others
         */

        if (!check_for_intent) {
            //For restrict accessing without password check.
            Toast.makeText(this, R.string.restrict_access, Toast.LENGTH_LONG).show()
            finishAffinity()
        }

        noteList = NotesRepository(application).getAllNotes()
        isListEmpty()

        val itemDecoration = DividerItemDecoration(applicationContext, DividerItemDecoration.VERTICAL)
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
        isListEmpty()
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
        R.id.action_settings -> {
            startActivity<Settings>()
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