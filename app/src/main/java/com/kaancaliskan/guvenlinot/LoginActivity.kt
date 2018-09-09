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

/**
 * This class is the checkpoint of password. If the password is correct, user can get into deeper in app.
 * @author Hakkı Kaan Çalışkan
 */
class LoginActivity : AppCompatActivity() {
    override fun onCreate(confirmdInstanceState: Bundle?) {
        super.onCreate(confirmdInstanceState)
        setContentView(R.layout.activity_login)
        setSupportActionBar(findViewById(R.id.toolbar))

        if (LocalData.with(this).read(getString(R.string.hashed_password))==""){
            LocalData.with(this).write(getString(R.string.hashed_password),password.sha512())
            Snackbar.make(confirm_EditText, getString(R.string.first_login), Snackbar.LENGTH_LONG).show()
        }
        confirm_button.setOnClickListener{
            if (confirm_EditText.text.toString().sha512()==LocalData.with(this).read(getString(R.string.hashed_password))){
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
        } else -> {
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
    return this.hashWithAlgorithm("SHA-512")
}
private fun String.hashWithAlgorithm(algorithm: String): String {
    val digest = MessageDigest.getInstance(algorithm)
    val bytes = digest.digest(this.toByteArray(Charsets.UTF_8))
    return bytes.fold("") { str, it -> str + "%02x".format(it) }
}