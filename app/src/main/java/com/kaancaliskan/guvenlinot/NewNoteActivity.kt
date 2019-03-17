package com.kaancaliskan.guvenlinot

import androidx.appcompat.app.AppCompatActivity
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
            val title_save = Hash.encode(note_title.text.toString())
            val content_save = Hash.encode(note_content.text.toString())
            val note = Note(noteTitle = title_save, noteContent = content_save)
            if (validateInput(title_save, content_save)) {
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
}
