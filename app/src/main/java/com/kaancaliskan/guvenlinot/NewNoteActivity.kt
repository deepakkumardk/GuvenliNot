package com.kaancaliskan.guvenlinot

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.kaancaliskan.guvenlinot.db.Note
import com.kaancaliskan.guvenlinot.db.NotesRepository
import com.kaancaliskan.guvenlinot.util.Hash
import kotlinx.android.synthetic.main.activity_new_note.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.yesButton
import java.text.SimpleDateFormat
import java.util.*

/**
 * Activity to write new notes and save it
 */
class NewNoteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_note)
        setSupportActionBar(new_note_bar)

        note_title.requestFocus()

        save_fab.onClick {
            val titleSave = Hash.encode(note_title.text.toString())
            val contentSave = Hash.encode(note_content.text.toString())
            val date = getDate()
            val note = Note(noteTitle = titleSave, noteContent = contentSave, date = date)
            if (validateInput(titleSave, contentSave)) {
                NotesRepository(application).insertNote(note)
                finishAfterTransition()
            } else {
                Snackbar.make(note_content, R.string.field_empty, Snackbar.LENGTH_SHORT).setAnchorView(save_fab).show()
            }
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
                    finishAfterTransition()
                }
                noButton { it.dismiss() }
            }.show()
        }else {
            finishAfterTransition()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        super.onBackPressed()
        return true
    }

    private fun getDate(): String{
        return SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.ROOT).format(Date()).toString()
    }
}
