package com.kaancaliskan.guvenlinot

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.settings.*

/**
 * This class provides us to change password.
 * @author Hakkı Kaan Çalışkan
 */
class Settings: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings)

        val password=LocalData.read(this,getString(R.string.hashed_password))
        //this one is the password that already we have

        change_button.setOnClickListener{
            var a=1
            val new_pw=Hash.sha512(new_password.text.toString())
            val new_pw_check=Hash.sha512(new_password_check.text.toString())
            val pw=Hash.sha512(password_check.text.toString())
            //this one is the pw check

            if (password!=pw){
                Toasty.error(this, getString(R.string.password_check_error), Toast.LENGTH_SHORT, true).show()
                a++
            } else if (new_pw!=new_pw_check || new_pw==""){
                //I didn't check new_password_check for is it empty because unnecessary
                Toasty.error(this, getString(R.string.new_password_check_error), Toast.LENGTH_SHORT, true).show()
                a++
            }
            if (a==1){
                LocalData.write(this, getString(R.string.hashed_password),new_pw)
                Toasty.success(this, getString(R.string.saved), Toast.LENGTH_SHORT, true).show()
            }
        }
    }
}