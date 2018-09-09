package com.kaancaliskan.guvenlinot

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_login.*
import java.security.MessageDigest

var password="1234"

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setSupportActionBar(findViewById(R.id.toolbar))

        if (LocalData.with(this@LoginActivity.applicationContext).read(getString(R.string.hashed_password))==""){
            LocalData.with(this@LoginActivity.applicationContext).write(getString(R.string.hashed_password),password.sha512())
        }
        save_button.setOnClickListener{
            val a=save_EditText.text.toString().sha512()
            val b=LocalData.with(this@LoginActivity.applicationContext).read(getString(R.string.hashed_password))

            if (a==b){
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                finish()
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
fun String.sha512(): String {
    return this.hashWithAlgorithm("SHA-512")
}
private fun String.hashWithAlgorithm(algorithm: String): String {
    val digest = MessageDigest.getInstance(algorithm)
    val bytes = digest.digest(this.toByteArray(Charsets.UTF_8))
    return bytes.fold("") { str, it -> str + "%02x".format(it) }
}