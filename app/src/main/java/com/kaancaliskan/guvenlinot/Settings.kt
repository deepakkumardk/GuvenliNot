package com.kaancaliskan.guvenlinot

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
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

        var password="1234"

        change_button.setOnClickListener{
            var a=1
            if (password!=password_check.text.toString()){
                Toasty.error(this, getString(R.string.password_check_error), Toast.LENGTH_SHORT, true).show()
                a++
            } else if (new_password.text.toString()!=new_password_check.text.toString() || new_password.text.toString()==""){
                //I didn't check new_password_check for is it empty because unnecessary
                Toasty.error(this, getString(R.string.new_password_check_error), Toast.LENGTH_SHORT, true).show()
                a++
            }
            if (a==1){
                password=Hash.sha512(new_password.text.toString())
                LocalData.write(this, getString(R.string.hashed_password),password)
                Toasty.success(this, getString(R.string.saved), Toast.LENGTH_SHORT, true).show()
            }
        }
    }
}