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
import java.util.Locale
import java.util.Date

/**
 * Activity to write new notes and save it
 */
class NewNoteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_note)
        setSupportActionBar(new_note_bar)

        note_title.requestFocus()

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24)

        save_fab.onClick {
            val title = Hash.encode(note_title.text.toString())
            val content = Hash.encode(note_content.text.toString())
            val date = getDate()
            val note = Note(noteTitle = title, noteContent = content, date = date)
            if (validateInput(title, content)) {
                NotesRepository(application).insertNote(note)
                finish()
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
                    finish()
                }
                noButton { it.dismiss() }
            }.show()
        } else {
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        super.onBackPressed()
        return false
    }

    private fun getDate(): String {
        return SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.ROOT).format(Date()).toString()
    }
}