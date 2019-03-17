package com.kaancaliskan.guvenlinot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity

var check_for_intent = false

/**
 * This class is the checkpoint of password.
 * @author Hakkı Kaan Çalışkan
 */
class LoginActivity : AppCompatActivity() {
    override fun onCreate(confirmdInstanceState: Bundle?) {
        super.onCreate(confirmdInstanceState)
        setContentView(R.layout.activity_login)
        setSupportActionBar(findViewById(R.id.toolbar))
        confirm_EditText.requestFocus()

        if (LocalData.read(this, getString(R.string.hashed_password)) == "" ){
            startActivity<FirstLogin>()
            finish()
        }
        confirm_button.setOnClickListener{
            if (Hash.sha512(confirm_EditText.text.toString())==LocalData.read(this, getString(R.string.hashed_password))){
                check_for_intent=true //For restrict accessing without password check.
                startActivity<MainActivity>()
                finish()
            } else{
                Toasty.error(this, getString(R.string.password_check_error), Toast.LENGTH_SHORT, true).show()
            }
        }
    }
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_settings -> {
            startActivity<Settings>()
            true
        }
        R.id.action_about ->{
            startActivity<AboutActivity>()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }
}