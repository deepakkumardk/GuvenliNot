package com.kaancaliskan.guvenlinot

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.method.LinkMovementMethod
import kotlinx.android.synthetic.main.about.*

/**
 * This class is my about page and provides link to be clickable without showing whole link.
 */
class About: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.about)
        setSupportActionBar(findViewById(R.id.toolbar))

        source_code.movementMethod = LinkMovementMethod.getInstance()
    }
}