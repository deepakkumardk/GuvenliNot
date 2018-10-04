package com.kaancaliskan.guvenlinot

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.first_login.*
import org.jetbrains.anko.startActivity

/**
 * This class helps us to take the first password.
 */
class FirstLogin: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.first_login)

        confirm_password_button.setOnClickListener {
            if (write_password.text.toString()==confirm_password.text.toString() &&  write_password.text.toString()!=""){
                LocalData.write(this, getString(R.string.hashed_password), Hash.sha512(write_password.text.toString()))
                Toasty.success(this, getString(R.string.saved), Toast.LENGTH_LONG).show()

                check_for_intent=true
                startActivity<MainActivity>()
                finish()
            }
            else{
                Toasty.error(this, getString(R.string.cant_save), Toast.LENGTH_LONG).show()
            }
        }
    }
}