package com.kaancaliskan.guvenlinot

import android.app.ActivityOptions
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.kaancaliskan.guvenlinot.util.Hash
import com.kaancaliskan.guvenlinot.util.LocalData
import kotlinx.android.synthetic.main.activity_login.*
import me.jfenn.attribouter.Attribouter
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivity

var check_for_intent = false

/**
 * This class is the checkpoint of password.
 */
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (LocalData.read(this, getString(R.string.hashed_password)) == null ){
            startActivity<FirstLogin>()
            finish()
        }

        confirm_EditText.requestFocus()

        confirm_button.setOnClickListener{
            if (Hash.sha512(confirm_EditText.text.toString())==LocalData.read(this, getString(R.string.hashed_password))){
                check_for_intent = true //For restrict accessing without password check.
                startActivity(intentFor<MainActivity>(), ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
                finishAfterTransition()
            } else{
                confirm_layout.error = getString(R.string.password_check_error)
            }
        }
    }
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_change_password -> {
            startActivity(intentFor<ChangePassword>(), ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
            true
        }
        R.id.action_about -> {
            Attribouter.from(this, this)
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
}