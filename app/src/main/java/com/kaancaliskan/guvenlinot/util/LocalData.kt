package com.kaancaliskan.guvenlinot.util

import android.content.Context

private const val SHARED_PREFERENCES: String = "preferences"

/**
 * This class makes easier to write SharedPreferences.
 */
object LocalData {
    /**
     * This function writes the string.
     */
    fun write(context: Context, title: String, content: String) {
        context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE).edit().putString(title, content).apply()
    }
    /**
     * This function reads string.
     */
    fun read(context: Context, title: String): String? {
        return context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE).getString(title, null)
    }
}
