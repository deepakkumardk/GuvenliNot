package com.kaancaliskan.guvenlinot

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.method.LinkMovementMethod
import kotlinx.android.synthetic.main.about.*
/**
 * This class is my about page and provides link to be clickable without showing whole link.
 */
class About: Activity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.about)

        source_code.movementMethod = LinkMovementMethod.getInstance()

        licenses_button.setOnClickListener {
            val intent = Intent(applicationContext, Licenses::class.java)
            startActivity(intent)
        }
    }
}