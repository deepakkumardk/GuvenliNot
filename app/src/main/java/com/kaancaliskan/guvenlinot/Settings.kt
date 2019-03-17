package com.kaancaliskan.guvenlinot

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kaancaliskan.guvenlinot.db.Note
import com.kaancaliskan.guvenlinot.db.NotesRepository
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.settings.*
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity

/**
 * This class is our settings.
 * @author Hakkı Kaan Çalışkan
 */
class Settings: AppCompatActivity(){
    private lateinit var noteList: List<Note>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings)
        setSupportActionBar(find(R.id.settings_toolbar))

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        change_password.setOnClickListener {
            startActivity<ChangePassword>()
        }

        delete_all_notes.setOnClickListener {
            noteList = NotesRepository(application).getAllNotes()

            if (noteList.isEmpty()){
                Toasty.warning(applicationContext, getString(R.string.delete_all_empty)).show()
            } else{
                NotesRepository(application).deleteAll()
                Toasty.success(applicationContext, getString(R.string.delete_all_success)).show()
            }
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}