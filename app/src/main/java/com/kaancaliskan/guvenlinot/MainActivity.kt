package com.kaancaliskan.guvenlinot

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.kaancaliskan.guvenlinot.db.GuvenliNotDatabase
import com.kaancaliskan.guvenlinot.db.Note
import com.kaancaliskan.guvenlinot.db.NotesRepository
import com.kaancaliskan.guvenlinot.util.GuvenliNotAdapter
import com.kaancaliskan.guvenlinot.util.LocalData
import com.kaancaliskan.guvenlinot.util.SwipeToDeleteCallback
import kotlinx.android.synthetic.main.main_activity.*
import me.jfenn.attribouter.Attribouter
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity
import java.util.Collections

/**
 * This activity saves note and encode/decode note.
 */
class MainActivity : AppCompatActivity() {
    private lateinit var adapter: GuvenliNotAdapter
    private lateinit var noteList: MutableList<Note> // should converted to LiveData<MutableList<Note>>
    // private lateinit var searchList: LiveData<MutableList<Note>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setSupportActionBar(main_bar)

        /**
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        contentView!!.systemUiVisibility =
        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        }
         Ready to android Q :)
         */
        if (!check_for_intent) {
            // For restrict accessing without password check.
            Toast.makeText(this, R.string.restrict_access, Toast.LENGTH_LONG).show()
            System.exit(0)
        }
        val swipeHandler = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deleteNote = noteList[viewHolder.adapterPosition]
                val position = viewHolder.adapterPosition
                noteList.removeAt(position)
                adapter.notifyItemRemoved(position)
                adapter.notifyItemRangeChanged(position, noteList.size - position + 1)
                isListEmpty()

                Snackbar.make(recycler_view, getString(R.string.note_delete_success), Snackbar.LENGTH_LONG)
                    .setAnchorView(add_note_fab)
                    .setAction(getString(R.string.undo)) {
                        noteList.add(deleteNote)
                        adapter.notifyItemInserted(noteList.size)
                        isListEmpty()
                    }
                    .show()
            }
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                Collections.swap(noteList, viewHolder.adapterPosition, target.adapterPosition)
                adapter.notifyItemMoved(viewHolder.adapterPosition, target.adapterPosition)
                /*
                 database e sırayla yazıyor ama okuyunca farklı çıkıyor
                  yazılanların id leri de doğru ama bi fark yok olmuyor database de :(
                 */
                return true
            }
        }
        ItemTouchHelper(swipeHandler).attachToRecyclerView(recycler_view)

        val itemDecoration = DividerItemDecoration(applicationContext, DividerItemDecoration.VERTICAL)
        recycler_view.addItemDecoration(itemDecoration)
        recycler_view.layoutManager = LinearLayoutManager(applicationContext)
        refreshRecyclerView()
        adapter.notifyDataSetChanged()

        add_note_fab.onClick {
            startActivity<NewNoteActivity>()
        }
    }
    private fun onItemClick(note: Note?) {
        startActivity<UpdateNoteActivity>(
                NOTE_ID to note?.noteId,
                NOTE_TITLE to note?.noteTitle.toString(),
                NOTE_CONTENT to note?.noteContent.toString(),
                DATE to note?.date.toString())
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
        NotesRepository(applicationContext).updateData(noteList)
        // not saving changes onMove
        super.onDestroy()
        GuvenliNotDatabase.destroyInstance()
    }
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_change_password -> {
            startActivity<ChangePassword>()
            true
        }
        R.id.theme -> {
            MaterialAlertDialogBuilder(this)
                    .setTitle(getString(R.string.select_theme))
                    .setMessage(getString(R.string.choose_theme))
                    .setPositiveButton(getString(R.string.light)) { _, _ ->
                        LocalData.write(this, getString(R.string.night_mode), "false")
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                        delegate.applyDayNight()
                    }
                    .setNegativeButton(getString(R.string.dark)) { _, _ ->
                        LocalData.write(this, getString(R.string.night_mode), "true")
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                        delegate.applyDayNight()
                    }
                    .setNeutralButton("Set by Battery Saver") { _, _ ->
                        LocalData.write(this, getString(R.string.night_mode), "battery")
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)
                        delegate.applyDayNight()
                    }
                    .show()
            true
        }
        R.id.action_about -> {
            Attribouter.from(this)
                    .withGitHubToken(System.getenv("GITHUB_TOKEN"))
                    .show()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        /**
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val search = menu.findItem(R.id.action_search).actionView as SearchView

        search.apply {
        setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

        override fun onQueryTextChange(newText: String): Boolean {
        // WORKING
        // use searchList
        return false
        }

        override fun onQueryTextSubmit(query: String): Boolean {
        // WORKING
        // use searchList
        return false
        }
        })
         */
        return true
    }
}
