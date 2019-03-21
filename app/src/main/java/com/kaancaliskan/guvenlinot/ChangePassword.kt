package com.kaancaliskan.guvenlinot

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.change_password.*
import org.jetbrains.anko.find

/**
 * This class provides us to change password.
 * @author Hakkı Kaan Çalışkan
 */
class ChangePassword: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.change_password)
        setSupportActionBar(find(R.id.change_password_toolbar))

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        password_check.requestFocus()

        val password = LocalData.read(this, getString(R.string.hashed_password))
        //this one is the password that already we have

        change_button.setOnClickListener {
            var a = 1
            val newPw = Hash.sha512(new_password.text.toString())
            val newPwCheck = Hash.sha512(new_password_check.text.toString())
            val pw = Hash.sha512(password_check.text.toString())
            //this one is the pw check

            if (password != pw) {
                Toasty.error(this, getString(R.string.password_check_error), Toast.LENGTH_SHORT, true).show()
                a++
            } else if (newPw != newPwCheck || newPw == "") {
                //I didn't check new_password_check for is it empty because unnecessary
                Toasty.error(this, getString(R.string.new_password_check_error), Toast.LENGTH_SHORT, true).show()
                a++
            }
            if (a == 1) {
                LocalData.write(this, getString(R.string.hashed_password), newPw)
                Toasty.success(this, getString(R.string.saved), Toast.LENGTH_SHORT, true).show()
            }
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}