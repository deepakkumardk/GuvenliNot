package com.kaancaliskan.guvenlinot

import android.app.Activity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import kotlinx.android.synthetic.main.licences.*
/**
 * Licenses class.
 */
class Licenses: Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.licences)

        iconics_library.movementMethod=LinkMovementMethod.getInstance()
        about_library.movementMethod=LinkMovementMethod.getInstance()
        toasty_library.movementMethod=LinkMovementMethod.getInstance()
    }
}