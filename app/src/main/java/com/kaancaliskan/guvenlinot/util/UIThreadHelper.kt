package com.kaancaliskan.guvenlinot.util

import android.content.Context
import android.os.Handler
import android.os.HandlerThread
import android.os.IBinder
import android.os.Looper
import android.os.Message
import android.os.Process
import android.view.inputmethod.InputMethodManager

/**
 * Utility class for offloading some class from UI thread
 */
object UIThreadHelper {

    private var sHandlerThread: HandlerThread? = null
    private var sHandler: Handler? = null

    private const val MSG_HIDE_KEYBOARD = 1

    private val backgroundLooper: Looper
        get() {
            if (sHandlerThread == null) {
                sHandlerThread = HandlerThread("UIThreadHelper", Process.THREAD_PRIORITY_FOREGROUND)
                sHandlerThread!!.start()
            }
            return sHandlerThread!!.looper
        }

    private fun getHandler(context: Context): Handler {
        if (sHandler == null) {
            sHandler = Handler(backgroundLooper,
                    UiCallbacks(context.applicationContext))
        }
        return sHandler as Handler
    }

    fun hideKeyboardAsync(context: Context, token: IBinder) {
        Message.obtain(getHandler(context), MSG_HIDE_KEYBOARD, token).sendToTarget()
    }

    private class UiCallbacks internal constructor(context: Context) : Handler.Callback {

        private val mIMM: InputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        override fun handleMessage(message: Message): Boolean {
            when (message.what) {
                MSG_HIDE_KEYBOARD -> {
                    mIMM.hideSoftInputFromWindow(message.obj as IBinder, 0)
                    return true
                }
            }
            return false
        }
    }
}