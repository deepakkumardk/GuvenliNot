package com.kaancaliskan.guvenlinot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kaancaliskan.guvenlinot.db.Note
import com.kaancaliskan.guvenlinot.db.NotesRepository
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_new_note.*
import org.jetbrains.anko.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Activity to write new notes and save it
 */
class NewNoteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_note)
        setSupportActionBar(find(R.id.toolbar))

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        note_title.requestFocus()
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
            val titleSave = Hash.encode(note_title.text.toString())
            val contentSave = Hash.encode(note_content.text.toString())
            val date = getDate()
            val note = Note(noteTitle = titleSave, noteContent = contentSave, date = date)
            if (validateInput(titleSave, contentSave)) {
                NotesRepository(application).insertNote(note)
                Toasty.success(applicationContext, getString(R.string.insert_note)).show()
                finish()
            } else {
                Toasty.warning(applicationContext, getString(R.string.field_empty)).show()
            }
        } else {
            finish()
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
