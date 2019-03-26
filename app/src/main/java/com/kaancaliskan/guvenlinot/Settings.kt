package com.kaancaliskan.guvenlinot

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.kaancaliskan.guvenlinot.db.Note
import com.kaancaliskan.guvenlinot.db.NotesRepository
import kotlinx.android.synthetic.main.settings.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.yesButton

/**
 * This class is our settings.
 * @author Hakkı Kaan Çalışkan
 */
class Settings: AppCompatActivity(){
    private lateinit var noteList: List<Note>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings)
        setSupportActionBar(settings_bar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        change_password.setOnClickListener {
            startActivity<ChangePassword>()
        }

        delete_all_notes.setOnClickListener {
            noteList = NotesRepository(application).getAllNotes()

            if (noteList.isEmpty()){
                Snackbar.make(delete_all_notes, getString(R.string.delete_all_empty), Snackbar.LENGTH_SHORT).show()
            } else{
                alert (R.string.ask_delete){
                    yesButton {
                        NotesRepository(application).deleteAll()
                        Snackbar.make(delete_all_notes, R.string.delete_all_success, Snackbar.LENGTH_SHORT).show()
                    }
                    noButton { it.dismiss() }
                }.show()
            }
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}