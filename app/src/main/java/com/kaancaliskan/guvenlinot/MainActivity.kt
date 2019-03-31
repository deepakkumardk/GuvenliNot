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
import com.google.android.material.snackbar.Snackbar
import com.kaancaliskan.guvenlinot.db.GuvenliNotDatabase
import com.kaancaliskan.guvenlinot.db.Note
import com.kaancaliskan.guvenlinot.db.NotesRepository
import kotlinx.android.synthetic.main.main_activity.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import android.app.ActivityOptions
import android.transition.Explode
import android.view.Window
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import org.jetbrains.anko.*
import android.util.Pair as UtilPair
/**
 * This activity saves note and encode/decode note.
 */
class MainActivity : AppCompatActivity() {
    private lateinit var adapter: GuvenliNotAdapter
    private lateinit var noteList: MutableList<Note>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(window) {
            requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)

            exitTransition = Explode()
            enterTransition = Explode()
        }
        setContentView(R.layout.main_activity)
        setSupportActionBar(main_bar)
        add_note_fab.setColorFilter(Color.WHITE)

        /**
         * TO DO
         * add gestures to cards
         * change layout with transition
         */

        if (!check_for_intent) {
            //For restrict accessing without password check.
            Toast.makeText(this, R.string.restrict_access, Toast.LENGTH_LONG).show()
            finishAffinity()
        }

        val swipeHandler = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deleteNote = noteList[viewHolder.adapterPosition]
                NotesRepository(applicationContext).deleteNote(deleteNote)
                adapter.notifyItemRemoved(viewHolder.adapterPosition)
                refreshRecyclerView()
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recycler_view)

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

        startActivity(intentFor<UpdateNoteActivity>(NOTE_ID to id, NOTE_TITLE to title, NOTE_CONTENT to content, DATE to date), ActivityOptions.makeSceneTransitionAnimation(this,
                UtilPair.create(find(R.id.item_note_title),"note_title"),
                UtilPair.create(find(R.id.item_note_content),"note_content"),
                UtilPair.create(find(R.id.date), "date")).toBundle())
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
        R.id.action_change_password -> {
            startActivity<ChangePassword>()
            true
        }
        R.id.action_delete_all -> {
            cleanAllNotes()
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
    private fun cleanAllNotes(){
        noteList = NotesRepository(application).getAllNotes()

        if (noteList.isEmpty()){
            Snackbar.make(add_note_fab, getString(R.string.delete_all_empty), Snackbar.LENGTH_SHORT).setAnchorView(add_note_fab).show()
        } else{
            alert (R.string.ask_delete_all){
                yesButton {
                    NotesRepository(application).deleteAll()
                    noteList.clear()
                    isListEmpty()
                    Snackbar.make(add_note_fab, R.string.delete_all_success, Snackbar.LENGTH_SHORT).setAnchorView(add_note_fab).show()
                }
                noButton { it.dismiss() }
            }.show()
        }
    }
}