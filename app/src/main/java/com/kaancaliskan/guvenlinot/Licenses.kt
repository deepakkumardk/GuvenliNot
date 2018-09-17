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

        toasty_license.movementMethod=LinkMovementMethod.getInstance()
        toasty_text.movementMethod=LinkMovementMethod.getInstance()
    }
}