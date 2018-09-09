package com.kaancaliskan.guvenlinot

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.main_activity.*
import java.util.*

class MainActivity: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setSupportActionBar(findViewById(R.id.toolbar))

        val decoded_note = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Base64.getDecoder().decode(LocalData.with(this@MainActivity.applicationContext).read(getString(R.string.hashed_note)).toByteArray(Charsets.UTF_8))
        } else {
            android.util.Base64.decode(LocalData.with(this@MainActivity.applicationContext).read(getString(R.string.hashed_note)).toByteArray(Charsets.UTF_8), android.util.Base64.DEFAULT)
        }
        val old_note = String(decoded_note, charset("UTF-8"))
        note_EditText.setText(old_note)

        note_save_button.setOnClickListener{
            val new_note=note_EditText.text.toString()

            val hashed_note= if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Base64.getEncoder().encodeToString(new_note.toByteArray(charset("UTF-8")))
            } else {
                android.util.Base64.encodeToString(new_note.toByteArray(charset("UTF-8")), android.util.Base64.DEFAULT)
            }

            LocalData.with(this@MainActivity.applicationContext).write(getString(R.string.hashed_note),hashed_note.toString())
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