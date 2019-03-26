package com.kaancaliskan.guvenlinot

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.first_login.*
import org.jetbrains.anko.startActivity

/**
 * This class helps us to take the first password.
 */
class FirstLogin: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.first_login)
        write_password.requestFocus()

        confirm_password_button.setOnClickListener {
            if (write_password.text.toString()==confirm_password.text.toString() &&  write_password.text.toString()!=""){
                LocalData.write(this, getString(R.string.hashed_password), Hash.sha512(write_password.text.toString()))

                check_for_intent=true
                startActivity<MainActivity>()
                finish()
            }
            else{
                Snackbar.make(confirm_password_button, R.string.cant_save, Snackbar.LENGTH_LONG).show()
            }
        }
    }
}