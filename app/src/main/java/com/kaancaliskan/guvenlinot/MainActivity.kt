package com.kaancaliskan.guvenlinot

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.main_activity.*
import java.util.*

/**
 * This activity saves note and encode/decode note.
 * @author Hakkı Kaan Çalışkan
 */
class MainActivity: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setSupportActionBar(findViewById(R.id.toolbar))

        val decodedNote = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Base64.getDecoder().decode(LocalData.with(this).read(getString(R.string.encoded_note)).toByteArray(Charsets.UTF_8))
        } else {
            android.util.Base64.decode(LocalData.with(this).read(getString(R.string.encoded_note)).toByteArray(Charsets.UTF_8), android.util.Base64.DEFAULT)
        }
        note_EditText.setText(decodedNote.toString(Charsets.UTF_8))

        note_save_button.setOnClickListener{
            val newNote=note_EditText.text.toString()

            val encodedNote= if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Base64.getEncoder().encodeToString(newNote.toByteArray(Charsets.UTF_8))
            } else {
                android.util.Base64.encodeToString(newNote.toByteArray(Charsets.UTF_8), android.util.Base64.DEFAULT)
            }
            LocalData.with(this).write(getString(R.string.encoded_note),encodedNote.toString())
            Snackbar.make(note_save_button, getString(R.string.saved),Snackbar.LENGTH_LONG).show()
        }
    }
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_settings -> {
            val intent = Intent(applicationContext, Settings::class.java)
            startActivity(intent)
            true
        } else -> {
            super.onOptionsItemSelected(item)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }
}