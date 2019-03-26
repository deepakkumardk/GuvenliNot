package com.kaancaliskan.guvenlinot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity

var check_for_intent = false

/**
 * This class is the checkpoint of password.
 * @author Hakkı Kaan Çalışkan
 */
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setSupportActionBar(login_bar)

        confirm_layout.requestFocus()

        if (LocalData.read(this, getString(R.string.hashed_password)) == "" ){
            startActivity<FirstLogin>()
            finish()
        }
        confirm_fab.setOnClickListener{
            if (Hash.sha512(confirm_EditText.text.toString())==LocalData.read(this, getString(R.string.hashed_password))){
                check_for_intent=true //For restrict accessing without password check.
                startActivity<MainActivity>()
                finish()
            } else{
                confirm_layout.error = getString(R.string.password_check_error)
            }
        }
    }
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_about ->{
            startActivity<AboutActivity>()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        menu.removeItem(R.id.action_settings)
        return true
    }
}