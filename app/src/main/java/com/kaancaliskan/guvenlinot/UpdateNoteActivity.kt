package com.kaancaliskan.guvenlinot

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.kaancaliskan.guvenlinot.db.Note
import com.kaancaliskan.guvenlinot.db.NotesRepository
import com.kaancaliskan.guvenlinot.util.Hash
import com.kaancaliskan.guvenlinot.util.LocalData
import kotlinx.android.synthetic.main.activity_update_note.*
import me.jfenn.attribouter.Attribouter
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date

const val NOTE_ID = "NOTE_ID"
const val NOTE_TITLE = "NOTE_TITLE"
const val NOTE_CONTENT = "NOTE_CONTENT"
const val DATE = "DATE"

/**
 * Activity to update the existing notes
 */
class UpdateNoteActivity : AppCompatActivity() {
    private var noteId: Long = 0
    private var noteTitle: String? = null
    private var noteContent: String? = null
    private var date: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_note)
        setSupportActionBar(update_note_bar)

        /**
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        contentView!!.systemUiVisibility =
        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        }
        Ready to android Q :)
         */

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        noteId = intent.getLongExtra(NOTE_ID, 0)
        noteTitle = Hash.decode(intent?.getStringExtra(NOTE_TITLE).toString())
        noteContent = Hash.decode(intent?.getStringExtra(NOTE_CONTENT).toString())
        date = intent?.getStringExtra(DATE).toString()

        loadNoteInfo()

        update_note_content.selectionEnd
        update_note_content.requestFocus()
    }

    private fun loadNoteInfo() {
        update_note_title.setText(noteTitle)
        update_note_content.setText(noteContent)
        date_text.text = date
    }

    private fun validateInput(title: String, content: String): Boolean {
        return !(title.isEmpty() && content.isEmpty())
    }

    override fun onBackPressed() {
        val title = update_note_title.text.toString()
        val content = update_note_content.text.toString()

        if (title == noteTitle && content == noteContent) {
            finish()
        } else if (title.isNotEmpty() || content.isNotEmpty()) {
            val titleSave = Hash.encode(update_note_title.text.toString())
            val contentSave = Hash.encode(update_note_content.text.toString())
            val date = getDate()

            val note = Note(noteId, titleSave, contentSave, date)
            if (validateInput(titleSave, contentSave)) {
                NotesRepository(application).updateNote(note)
                finish()
            } else {
                Snackbar.make(update_note_content, R.string.field_empty, Snackbar.LENGTH_SHORT).show()
                if (titleSave.isEmpty()) {
                    update_note_title.requestFocus()
                } else {
                    update_note_content.requestFocus()
                }
            }
        }
    }
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
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
        // menu.removeItem(R.id.action_search)
        menu.removeItem(R.id.action_change_password)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return false
    }

    private fun getDate(): String {
        return SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.ROOT).format(Date()).toString()
    }
}
