package com.kaancaliskan.guvenlinot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.kaancaliskan.guvenlinot.db.Note
import com.kaancaliskan.guvenlinot.db.NotesRepository
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_update_note.*
import org.jetbrains.anko.*

const val NOTE_ID = "NOTE_ID"
const val NOTE_TITLE = "NOTE_TITLE"
const val NOTE_CONTENT = "NOTE_CONTENT"

/**
 * Activity to update the existing notes
 */
class UpdateNoteActivity : AppCompatActivity() {
    private var noteId: Int = 0
    private var noteTitle: String? = null
    private var noteContent: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_note)
        setSupportActionBar(find(R.id.toolbar))

        noteId = intent.getIntExtra(NOTE_ID, 0)
        noteTitle = Hash.decode(intent?.getStringExtra(NOTE_TITLE).toString())
        noteContent = Hash.decode(intent?.getStringExtra(NOTE_CONTENT).toString())

        loadNoteInfo()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.note_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_save_note -> updateNote()
            R.id.action_delete_note -> deleteNote()
            else -> return false
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loadNoteInfo() {
        update_note_title.setText(noteTitle)
        update_note_content.setText(noteContent)
        update_note_content.selectionEnd
        update_note_content.requestFocus()
    }

    private fun updateNote() {
        val title = Hash.encode(update_note_title.text.toString())
        val content = Hash.encode(update_note_content.text.toString())
        val note = Note(noteId, title, content)
        if (validateInput(title, content)) {
            NotesRepository(application).updateNote(note)
            Toasty.success(applicationContext, getString(R.string.update_success), Toast.LENGTH_SHORT, true).show()
            finish()
        } else {
            Toasty.error(applicationContext, getString(R.string.field_empty), Toast.LENGTH_SHORT, true).show()
        }
    }

    private fun deleteNote() {
        alert(getString(R.string.ask_delete)) {
            yesButton {
                val note = Note(noteId, noteTitle.toString(),noteContent.toString())
                finish()
                NotesRepository(application).deleteNote(note)
                Toasty.success(applicationContext, getString(R.string.note_delete_success), Toast.LENGTH_SHORT, true).show()
            }
            noButton { it.dismiss() }
        }.show()
    }

    private fun validateInput(title: String, content: String): Boolean {
        return !(title.isEmpty() && content.isEmpty())
    }

    override fun onBackPressed() {
        val title = update_note_title.text.toString()
        val content = update_note_content.text.toString()

        if (title == noteTitle && content == noteContent) {
            finish()
            super.onBackPressed()
        }else if (title.isNotEmpty() || content.isNotEmpty()) {
            alert(getString(R.string.discard_changes)) {
                yesButton {
                    finish()
                    super.onBackPressed()
                }
                noButton { it.dismiss() }
            }.show()
        }
    }
}