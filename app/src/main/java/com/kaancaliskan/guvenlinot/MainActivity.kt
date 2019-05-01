package com.kaancaliskan.guvenlinot

import android.app.ActivityOptions
import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kaancaliskan.guvenlinot.db.GuvenliNotDatabase
import com.kaancaliskan.guvenlinot.db.Note
import com.kaancaliskan.guvenlinot.db.NotesRepository
import com.kaancaliskan.guvenlinot.util.GuvenliNotAdapter
import com.kaancaliskan.guvenlinot.util.SwipeToDeleteCallback
import kotlinx.android.synthetic.main.main_activity.*
import me.jfenn.attribouter.Attribouter
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.sdk27.coroutines.onClick
import java.util.Collections

/**
 * This activity saves note and encode/decode note.
 */
class MainActivity : AppCompatActivity() {
    private lateinit var adapter: GuvenliNotAdapter
    private lateinit var noteList: MutableList<Note> //should converted to LiveData<MutableList<Note>>
    private lateinit var searchList: LiveData<MutableList<Note>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setSupportActionBar(main_bar)

        if (!check_for_intent) {
            //For restrict accessing without password check.
            Toast.makeText(this, R.string.restrict_access, Toast.LENGTH_LONG).show()
            System.exit(0)
        }
        val swipeHandler = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deleteNote = noteList[viewHolder.adapterPosition]
                NotesRepository(applicationContext).deleteNote(deleteNote)
                noteList.removeAt(viewHolder.adapterPosition)
                adapter.notifyItemRemoved(viewHolder.adapterPosition)
                adapter.notifyItemRangeChanged(viewHolder.adapterPosition, noteList.size - viewHolder.adapterPosition + 1)
                //to change other note's position change accordingly
                isListEmpty()
            }

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                Collections.swap(noteList, viewHolder.adapterPosition, target.adapterPosition)
                adapter.notifyItemMoved(viewHolder.adapterPosition, target.adapterPosition)

                //swap content
                val currentNote = Note(
                        Id = noteList[viewHolder.adapterPosition].Id,
                        noteTitle = noteList[target.adapterPosition].noteTitle,
                        noteContent = noteList[target.adapterPosition].noteContent,
                        date = noteList[target.adapterPosition].date)

                val newNote = Note(
                        Id = noteList[target.adapterPosition].Id,
                        noteTitle = noteList[viewHolder.adapterPosition].noteTitle,
                        noteContent = noteList[viewHolder.adapterPosition].noteContent,
                        date = noteList[viewHolder.adapterPosition].date)

                //save changes to database
                NotesRepository(applicationContext).updateNote(currentNote)
                NotesRepository(applicationContext).updateNote(newNote)

                //this method works with just 1 swipe up or down :(

                return super.onMove(recyclerView, viewHolder, target)
            }
        }
        ItemTouchHelper(swipeHandler).attachToRecyclerView(recycler_view)

        noteList = NotesRepository(application).getAllNotes()
        isListEmpty()

        val itemDecoration = DividerItemDecoration(applicationContext, DividerItemDecoration.VERTICAL)
        recycler_view.addItemDecoration(itemDecoration)
        recycler_view.layoutManager = LinearLayoutManager(applicationContext)
        adapter = GuvenliNotAdapter(noteList) { note -> onItemClick(note) }
        recycler_view.adapter = adapter
        adapter.notifyDataSetChanged()

        add_note_fab.onClick {
            startActivity(
                    intentFor<NewNoteActivity>(),
                    ActivityOptions
                            .makeSceneTransitionAnimation(
                                    this@MainActivity)
                            .toBundle())
        }
    }

    private fun onItemClick(note: Note?) {
        val id = note?.Id
        val title = note?.noteTitle.toString()
        val content = note?.noteContent.toString()
        val date = note?.date.toString()

        startActivity(
                intentFor<UpdateNoteActivity>(
                        NOTE_ID to id,
                        NOTE_TITLE to title,
                        NOTE_CONTENT to content,
                        DATE to date),
                ActivityOptions
                        .makeSceneTransitionAnimation(
                                this)
                        .toBundle())

    }

    override fun onResume() {
        refreshRecyclerView()
        super.onResume()
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

    override fun onDestroy() {
        super.onDestroy()
        GuvenliNotDatabase.destroyInstance()
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_change_password -> {
            startActivity(
                    intentFor<ChangePassword>(),
                    ActivityOptions
                            .makeSceneTransitionAnimation(this).toBundle())
            true
        }
        R.id.action_about -> {
            Attribouter.from(this, this)
                    .withGitHubToken(System.getenv("GITHUB_TOKEN"))
                    .show()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val search = menu.findItem(R.id.action_search).actionView as SearchView

        search.apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                //DO SEARCH!!
                //use searchList
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                //DO SEARCH!!
                //use searchList
                return false
            }
        })
        return true
    }
}