package com.kaancaliskan.guvenlinot

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.settings.*

/**
 * This class provides us to change password.
 * @author Hakkı Kaan Çalışkan
 */
class Settings: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings)
        setSupportActionBar(findViewById(R.id.toolbar))

        change_button.setOnClickListener{
            if (password!=password_check.text.toString()){
                password_check.error=getString(R.string.password_check_error)
            }
            if (new_password.text.toString()!=new_password_check.text.toString()){
                new_password_check.error=getString(R.string.new_password_check_error)
                new_password.error=getString(R.string.new_password_check_error)
            }
            else {
                password=new_password.text.toString().sha512()
                LocalData.with(this).write(getString(R.string.hashed_password),password)
                Snackbar.make(change_button, getString(R.string.saved), Snackbar.LENGTH_LONG).show()
            }
        }
    }
}