package com.kaancaliskan.guvenlinot

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.kaancaliskan.guvenlinot.util.Hash
import com.kaancaliskan.guvenlinot.util.LocalData
import kotlinx.android.synthetic.main.change_password.*
import me.jfenn.attribouter.Attribouter

/**
 * This class provides us to change password.
 */
class ChangePassword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.change_password)
        setSupportActionBar(change_password_bar)

        /**
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        contentView!!.systemUiVisibility =
        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        }
        Ready to android Q :)
         */

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

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
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.theme -> {
            MaterialAlertDialogBuilder(this)
                    .setTitle(getString(R.string.select_theme))
                    .setMessage(getString(R.string.choose_theme))
                    .setPositiveButton(getString(R.string.light)) { _, _ ->
                        LocalData.write(this, getString(R.string.night_mode), "false")
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                        delegate.applyDayNight()
                    }
                    .setNegativeButton(getString(R.string.dark)) { _, _ ->
                        LocalData.write(this, getString(R.string.night_mode), "true")
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                        delegate.applyDayNight()
                    }
                    .setNeutralButton("Set by Battery Saver") { _, _ ->
                        LocalData.write(this, getString(R.string.night_mode), "battery")
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)
                        delegate.applyDayNight()
                    }
                    .show()
            true
        }
        R.id.action_about -> {
            Attribouter.from(this)
                    .withGitHubToken(System.getenv("GITHUB_TOKEN"))
                    .show()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        // menu.removeItem(R.id.action_search)
        menu.removeItem(R.id.action_change_password)
        return true
    }
}
