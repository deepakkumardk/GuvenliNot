package com.kaancaliskan.guvenlinot

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.change_password.*
/**
 * This class provides us to change password.
 * @author Hakkı Kaan Çalışkan
 */
class ChangePassword: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.change_password)
        setSupportActionBar(change_password_bar)

        password_check_layout.requestFocus()

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        password_check.setOnTouchListener { _, _ ->
            password_check_layout.isErrorEnabled = false
            false
        }
        new_password.setOnTouchListener { _, _ ->
            new_password_layout.isErrorEnabled = false
            false
        }
        new_password_check.setOnTouchListener { _, _ ->
            new_password_check_layout.isErrorEnabled = false
            false
        }

        change_password_fab.setOnClickListener {
            if (checkBox()){
                val newPw = Hash.sha512(new_password.text.toString())

                LocalData.write(this, getString(R.string.hashed_password), newPw)
                change_password_fab.hide()
                Snackbar.make(change_password_fab, R.string.saved, Snackbar.LENGTH_SHORT).show()
            }
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    private fun checkBox(): Boolean {
        val password = LocalData.read(this, getString(R.string.hashed_password))
        val pw = Hash.sha512(password_check.text.toString())
        var a = 1

        if (password != pw) {
            password_check_layout.error = getString(R.string.password_check_error)
            a++
        }

        if (new_password.text!!.isBlank()) {
            new_password_layout.error = getString(R.string.empty)
            a++
        }

        if (new_password_check.text!!.isBlank()){
            new_password_check_layout.error = getString(R.string.empty)
            a++
        }

        if (new_password.text.toString() != new_password_check.text.toString() && !new_password.text!!.isBlank()) {
            new_password_layout.error = getString(R.string.new_password_check_error)
            new_password_check_layout.error = getString(R.string.new_password_check_error)
            a++
        }
        return a == 1
    }
}