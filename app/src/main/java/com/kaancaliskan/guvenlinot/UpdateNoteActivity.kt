package com.kaancaliskan.guvenlinot

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.kaancaliskan.guvenlinot.db.Note
import com.kaancaliskan.guvenlinot.db.NotesRepository
import kotlinx.android.synthetic.main.activity_update_note.*
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onClick
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
        setSupportActionBar(update_note_bar)
        delete_fab.setColorFilter(Color.WHITE)

        update_note_content.requestFocus()

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        noteId = intent.getIntExtra(NOTE_ID, 0)
        noteTitle = Hash.decode(intent?.getStringExtra(NOTE_TITLE).toString())
        noteContent = Hash.decode(intent?.getStringExtra(NOTE_CONTENT).toString())
        date = intent?.getStringExtra(DATE).toString()

        loadNoteInfo()

        delete_fab.onClick { deleteNote() }
    }

    private fun loadNoteInfo() {
        update_note_title.setText(noteTitle)
        update_note_content.setText(noteContent)
        date_text.text = date
        update_note_content.selectionEnd
        update_note_content.requestFocus()
    }

    private fun deleteNote() {
        alert(R.string.ask_delete) {
            yesButton {
                val note = Note(noteId, noteTitle.toString(),noteContent.toString(), date.toString())
                finish()
                NotesRepository(application).deleteNote(note)
                Snackbar.make(update_note_content, R.string.note_delete_success, Snackbar.LENGTH_SHORT).show()
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
                finish()
            } else {
                Snackbar.make(update_note_content, R.string.field_empty, Snackbar.LENGTH_SHORT).show()
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