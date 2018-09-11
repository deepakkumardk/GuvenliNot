package com.kaancaliskan.guvenlinot

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class About: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.about)
        setSupportActionBar(findViewById(R.id.toolbar))
    }
}