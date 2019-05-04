package com.kaancaliskan.guvenlinot

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.kaancaliskan.guvenlinot.util.Hash
import com.kaancaliskan.guvenlinot.util.LocalData
import kotlinx.android.synthetic.main.change_password.*
/**
 * This class provides us to change password.
 */
class ChangePassword: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.change_password)
        setSupportActionBar(change_password_bar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24)

        password_check.requestFocus()

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

        change_password_button.setOnClickListener {
            if (checkBox()){
                val newPw = Hash.sha512(new_password.text.toString())

                LocalData.write(this, getString(R.string.hashed_password), newPw)
                Snackbar.make(change_password_button, R.string.saved, Snackbar.LENGTH_SHORT).show()
            }
        }
    }
    override fun onBackPressed() {
        finishAfterTransition()
        super.onBackPressed()
    }
    private fun checkBox(): Boolean {
        val password = LocalData.read(this, getString(R.string.hashed_password))
        val pw = Hash.sha512(password_check.text.toString())

        when {
            password != pw -> {
                password_check_layout.error = getString(R.string.password_check_error)
                return false
            }
            new_password.text!!.isBlank() -> {
                new_password_layout.error = getString(R.string.empty)
                return false
            }
            new_password_check.text!!.isBlank() -> {
                new_password_check_layout.error = getString(R.string.empty)
                return false
            }
            new_password.text.toString() != new_password_check.text.toString() && !new_password.text!!.isBlank() -> {
                new_password_layout.error = getString(R.string.new_password_check_error)
                new_password_check_layout.error = getString(R.string.new_password_check_error)
                return false
            }
            else -> return true
        }
    }
}