package com.kaancaliskan.guvenlinot

import android.support.v7.app.AppCompatActivity
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

/*
 * Activity to update the existing notes
 */
class UpdateNoteActivity : AppCompatActivity() {
    private var id: Int = 0
    private var title: String? = null
    private var content: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_note)

        id = intent.getIntExtra(NOTE_ID,0)
        title = intent?.getStringExtra(NOTE_TITLE).toString()
        content = intent?.getStringExtra(NOTE_CONTENT).toString()

        loadNoteInfo()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.note_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) : Boolean  {
        when(item.itemId) {
            R.id.action_save_note -> updateNote()
            else -> return false
        }
        return  super.onOptionsItemSelected(item)
    }

    private fun loadNoteInfo() {
        update_note_title.setText(title)
        update_note_content.setText(content)
        update_note_content.selectionEnd
        update_note_content.requestFocus()
    }

    private fun updateNote() {
        val title = update_note_title.text.toString()
        val content = update_note_content.text.toString()
        val note = Note(id,title,content)
        if (validateInput(title,content)) {
            NotesRepository(application).updateNote(note)
            Toasty.success(applicationContext,"Notes updated successfully...", Toast.LENGTH_SHORT,true).show()
            finish()
            startActivity<MainActivity>()
        }else {
            Toasty.info(applicationContext,"Field is Empty...", Toast.LENGTH_SHORT,true).show()
        }
    }

    private fun validateInput(title: String,content: String): Boolean {
        return !(title.isEmpty() && content.isEmpty())
    }

    override fun onBackPressed() {
        val title = update_note_title.text.toString()
        val content = update_note_content.text.toString()

        if (title.isNotEmpty() || content.isNotEmpty()) {
            alert("Are you sure you want to discard your changes?") {
                yesButton {
                    finish()
                    super.onBackPressed()
                }
                noButton { it.dismiss() }
            }.show()
        }
    }
}
