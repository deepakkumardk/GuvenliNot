package com.kaancaliskan.guvenlinot

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_login.*
import java.security.MessageDigest

var password="1234"
var hashed_password = "d404559f602eab6fd602ac7680dacbfaadd13630335e951f097af3900e9de176b6db28512f2e000b9d04fba5133e8b1c6e8df59db3a8ab9d60be4b97cc9e81db" //1234
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

        if (LocalData.with(this).read(getString(R.string.hashed_password)) == "" ){
            LocalData.with(this).write(getString(R.string.hashed_password), password.sha512())
            Snackbar.make(confirm_EditText, getString(R.string.change_password), Snackbar.LENGTH_LONG).show()
        }
        confirm_button.setOnClickListener{
            if (confirm_EditText.text.toString().sha512()==LocalData.with(this).read(getString(R.string.hashed_password))){

                check_for_intent=true //For restrict accessing without password check.

                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else{
                confirm_EditText.error=getString(R.string.password_check_error)
            }
        }
    }
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_settings -> {
            val intent = Intent(applicationContext, Settings::class.java)
            startActivity(intent)
            true
        }
        R.id.action_about ->{
            val intent = Intent(applicationContext, About::class.java)
            startActivity(intent)
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
/**
 * This function makes easier to hash the password.
 */
fun String.sha512(): String {
    val algorithm= "SHA-512"
    val digest = MessageDigest.getInstance(algorithm)
    val bytes = digest.digest(this.toByteArray(Charsets.UTF_8))
    return bytes.fold("") { str, it -> str + "%02x".format(it) }
}