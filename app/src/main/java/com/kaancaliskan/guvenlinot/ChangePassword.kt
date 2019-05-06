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
class ChangePassword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        delegate.applyDayNight()
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
            if (checkBox()) {
                val newPassword = Hash.sha512(new_password.text.toString())

                LocalData.write(this, getString(R.string.hashed_password), newPassword)
                Snackbar.make(
                            change_password_button,
                            R.string.saved,
                            Snackbar.LENGTH_SHORT)
                        .setAnchorView(change_password_bar)
                        .show()
            }
        }
    }
    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    /**
     * this function checks this conditions
     * 1- requested current password is correct
     * 2- new password strings are same
     * 3- new password string isn't empty
     */
    private fun checkBox(): Boolean {
        val currentPassword = LocalData.read(this, getString(R.string.hashed_password))
        val currentPasswordCheck = Hash.sha512(password_check.text.toString())
        val newPassword = new_password.text.toString()
        val newPasswordCheck = new_password_check.text.toString()
        var boolean = false

        when {
            currentPassword != currentPasswordCheck -> {
                password_check_layout.error = getString(R.string.password_check_error)
            }
            newPassword.isBlank() -> {
                new_password_layout.error = getString(R.string.empty)
            }
            newPasswordCheck.isBlank() -> {
                new_password_check_layout.error = getString(R.string.empty)
            }
            newPassword != newPasswordCheck && !newPassword.isBlank() -> {
                new_password_layout.error = getString(R.string.new_password_check_error)
                new_password_check_layout.error = getString(R.string.new_password_check_error)
            }
            else -> boolean = true
        }
        return boolean
    }
}