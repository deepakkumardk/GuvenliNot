package com.kaancaliskan.guvenlinot

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.main_activity.*

/**
 * This activity saves note and encode/decode note.
 * @author Hakkı Kaan Çalışkan
 */
class MainActivity: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setSupportActionBar(findViewById(R.id.toolbar))

        if(!check_for_intent){
            //For restrict accessing without password check.
            Toasty.error(this, getString(R.string.restrict_access), Toast.LENGTH_SHORT, true).show()
            finish()
        }

        val decodedNote=Hash.decode(LocalData.read(this, getString(R.string.encoded_note)))
        note_EditText.setText(decodedNote)

        note_save_button.setOnClickListener{
            val newNote=note_EditText.text.toString()
            val encodedNote= Hash.encode(newNote)
            LocalData.write(this, getString(R.string.encoded_note),encodedNote)
            Toasty.success(this, getString(R.string.saved), Toast.LENGTH_SHORT, true).show()
        }
    }
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_settings -> {
            val intent = Intent(applicationContext, Settings::class.java)
            startActivity(intent)
            true
        }
        R.id.action_about ->{
            val intent = Intent(applicationContext, AboutActivity::class.java)
            startActivity(intent)
            true
        }else -> {
            super.onOptionsItemSelected(item)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }
}