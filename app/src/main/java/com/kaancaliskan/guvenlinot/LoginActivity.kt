package com.kaancaliskan.guvenlinot

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.kaancaliskan.guvenlinot.util.Hash
import com.kaancaliskan.guvenlinot.util.LocalData
import kotlinx.android.synthetic.main.activity_login.*
import me.jfenn.attribouter.Attribouter
import org.jetbrains.anko.startActivity

var check_for_intent = false

/**
 * This class is the checkpoint of password.
 */
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        when (LocalData.read(this, getString(R.string.night_mode))) {
            "true" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            "false" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            "battery" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)
        }
        delegate.applyDayNight()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setSupportActionBar(login_bar)

        val hashedCurrentPassword = LocalData.read(this, getString(R.string.hashed_password))
        if (LocalData.read(this, getString(R.string.hashed_password)) == null) {
            intent(2)
        }
        confirm_password_fab.setOnClickListener {
            val hashedPassword = Hash.sha512(confirm_EditText.text.toString())
            if (hashedPassword == hashedCurrentPassword) {
                intent(0)
            } else {
                confirm_layout.error = getString(R.string.password_check_error)
                confirm_EditText.text?.clear()
                confirm_layout.requestFocus()
            }
        }
        confirm_layout.requestFocus()
    }
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_change_password -> {
            intent(1)
            true
        }
        R.id.theme -> {
            MaterialAlertDialogBuilder(this)
                    .setTitle(getString(R.string.select_theme))
                    .setMessage(getString(R.string.choose_theme))
                    .setPositiveButton(getString(R.string.light)) { _, _ ->
                        LocalData.write(this, getString(R.string.night_mode), "false")
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                        delegate.applyDayNight() }
                    .setNegativeButton(getString(R.string.dark)) { _, _ ->
                        LocalData.write(this, getString(R.string.night_mode), "true")
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                        delegate.applyDayNight() }
                    .setNeutralButton("Set by Battery Saver") { _, _ ->
                        LocalData.write(this, getString(R.string.night_mode), "battery")
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)
                        delegate.applyDayNight() }
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
        menu.removeItem(R.id.action_search)
        return true
    }
    private fun intent(i: Int) {
        when (i) {
            0 -> {
                check_for_intent = true
                startActivity<MainActivity>()
                finish() }
            1 -> startActivity<ChangePassword>()
            2 -> {
                startActivity<FirstLogin>()
                finish() }
        }
    }
}
