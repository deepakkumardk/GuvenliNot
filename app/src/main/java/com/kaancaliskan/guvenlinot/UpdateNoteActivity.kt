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
import java.text.SimpleDateFormat
import java.util.*

const val NOTE_ID = "NOTE_ID"
const val NOTE_TITLE = "NOTE_TITLE"
const val NOTE_CONTENT = "NOTE_CONTENT"
const val DATE = "DATE"

/**
 * Activity to update the existing notes
 */
class UpdateNoteActivity : AppCompatActivity() {
    private var noteId: Int = 0
    private var noteTitle: String? = null
    private var noteContent: String? = null
    private var date: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_note)
        setSupportActionBar(find(R.id.toolbar))

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        noteId = intent.getIntExtra(NOTE_ID, 0)
        noteTitle = Hash.decode(intent?.getStringExtra(NOTE_TITLE).toString())
        noteContent = Hash.decode(intent?.getStringExtra(NOTE_CONTENT).toString())
        date = intent?.getStringExtra(DATE).toString()

        loadNoteInfo()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.note_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete_note -> deleteNote()
            else -> return false
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loadNoteInfo() {
        update_note_title.setText(noteTitle)
        update_note_content.setText(noteContent)
        date_text.text = date
        update_note_content.selectionEnd
        update_note_content.requestFocus()
    }

    private fun deleteNote() {
        alert(getString(R.string.ask_delete)) {
            yesButton {
                val note = Note(noteId, noteTitle.toString(),noteContent.toString(), date.toString())
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
            val titleSave = Hash.encode(update_note_title.text.toString())
            val contentSave = Hash.encode(update_note_content.text.toString())
            val date = getDate()

            val note = Note(noteId, titleSave, contentSave, date)
            if (validateInput(titleSave, contentSave)) {
                NotesRepository(application).updateNote(note)
                Toasty.success(applicationContext, getString(R.string.update_success), Toast.LENGTH_SHORT, true).show()
                finish()
            } else {
                Toasty.error(applicationContext, getString(R.string.field_empty), Toast.LENGTH_SHORT, true).show()
            }
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    private fun getDate(): String{
        return SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.ROOT).format(Date()).toString()
    }
}