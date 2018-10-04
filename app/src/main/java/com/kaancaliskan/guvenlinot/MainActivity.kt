package com.kaancaliskan.guvenlinot

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.kaancaliskan.guvenlinot.db.GuvenliNotDatabase
import com.kaancaliskan.guvenlinot.db.Note
import com.kaancaliskan.guvenlinot.db.NotesRepository
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.main_activity.*
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity

/**
 * This activity saves note and encode/decode note.
 */
class MainActivity : AppCompatActivity() {
    private lateinit var adapter: GuvenliNotAdapter
    private lateinit var noteList: List<Note>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setSupportActionBar(find(R.id.toolbar))

        if (!check_for_intent) {
            //For restrict accessing without password check.
            Toasty.error(this, getString(R.string.restrict_access), Toast.LENGTH_SHORT, true).show()
            finish()
        }

        noteList = NotesRepository(application).getAllNotes()

        val itemDecoration = DividerItemDecoration(applicationContext, DividerItemDecoration.VERTICAL)
        recycler_view.addItemDecoration(itemDecoration)
        recycler_view.layoutManager = LinearLayoutManager(applicationContext)
        recycler_view.hasFixedSize()
        adapter = GuvenliNotAdapter(noteList) { note -> onItemClick(note) }
        recycler_view.adapter = adapter
        adapter.notifyDataSetChanged()

        fab.onClick { startActivity<NewNoteActivity>() }
    }

    private fun onItemClick(note: Note?) {
        val id = note?.Id
        val title = note?.noteTitle.toString()
        val content = note?.noteContent.toString()

        startActivity<UpdateNoteActivity>(NOTE_ID to id, NOTE_TITLE to title, NOTE_CONTENT to content)
    }

    override fun onResume() {
        super.onResume()
        refreshRecyclerView()
    }

    private fun refreshRecyclerView() {
        noteList = NotesRepository(application).getAllNotes()
        adapter = GuvenliNotAdapter(noteList) { note -> onItemClick(note) }
        recycler_view.adapter = adapter
        adapter.notifyDataSetChanged()
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
