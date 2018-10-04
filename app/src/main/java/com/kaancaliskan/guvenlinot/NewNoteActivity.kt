package com.kaancaliskan.guvenlinot

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.kaancaliskan.guvenlinot.db.Note
import com.kaancaliskan.guvenlinot.db.NotesRepository
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_new_note.*
import org.jetbrains.anko.*

/**
 * Activity to write new notes and save it
 */
class NewNoteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_note)
        setSupportActionBar(find(R.id.toolbar))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.note_menu, menu)
        menu.findItem(R.id.action_delete_note).isVisible = false
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_save_note -> insertNote()
            else -> return false
        }
        return super.onOptionsItemSelected(item)
    }

    private fun insertNote() {
        val title = note_title.text.toString()
        val content = note_content.text.toString()
        val note = Note(noteTitle = title, noteContent = content)
        if (validateInput(title, content)) {
            NotesRepository(application).insertNote(note)
            Toasty.success(applicationContext, getString(R.string.insert_note)).show()
            finish()
        } else {
            Toasty.info(applicationContext, getString(R.string.field_empty)).show()
        }
    }

    private fun validateInput(title: String, content: String): Boolean {
        return !(title.isEmpty() || content.isEmpty())
    }

    override fun onResume() {
        super.onResume()
        invalidateOptionsMenu()
    }

    override fun onBackPressed() {
        val title = note_title.text.toString()
        val content = note_content.text.toString()

        if (title.isNotEmpty() || content.isNotEmpty()) {
            alert(getString(R.string.discard_changes)) {
                yesButton {
                    finish()
                    super.onBackPressed()
                }
                noButton { it.dismiss() }
            }.show()
        } else {
            finish()
        }
    }
}
