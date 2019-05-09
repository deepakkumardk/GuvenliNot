package com.kaancaliskan.guvenlinot

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.kaancaliskan.guvenlinot.util.Hash
import com.kaancaliskan.guvenlinot.util.LocalData
import kotlinx.android.synthetic.main.first_login.*
import org.jetbrains.anko.startActivity

/**
 * This class helps us to take the first password.
 */
class FirstLogin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.first_login)

        /**
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        contentView!!.systemUiVisibility =
        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        }
        Ready to android Q :)
         */

        save_password_button.setOnClickListener {
            val newPassword = write_password.text.toString()
            val newPasswordConfirm = confirm_password.text.toString()

            if (newPassword == newPasswordConfirm && newPassword != "") {
                LocalData.write(this, getString(R.string.hashed_password), Hash.sha512(newPassword))

                check_for_intent = true
                startActivity<MainActivity>()
                finish()
            } else {
                Snackbar.make(save_password_button, R.string.cant_save, Snackbar.LENGTH_LONG).show()
            }
        }
    }
}
