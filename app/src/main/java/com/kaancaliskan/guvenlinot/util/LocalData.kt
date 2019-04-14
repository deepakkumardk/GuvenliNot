package com.kaancaliskan.guvenlinot.util

import android.content.Context
import android.content.SharedPreferences

private const val sharedPref = "preferences"

/**
 * This class makes easier to write SharedPreferences.
 */
object LocalData {
    /**
     * This function writes the parameters.
     */
    fun write(context: Context, title: String, content: String) {
        val sharedPreferences : SharedPreferences= context.getSharedPreferences(sharedPref, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor= sharedPreferences.edit()
        editor.putString(title, content).apply()
    }
    /**
     * This function reads note.
     */
    fun read(context: Context, title: String): String? {
        val sharedPreferences : SharedPreferences= context.getSharedPreferences(sharedPref, Context.MODE_PRIVATE)
        return sharedPreferences.getString(title, "")
    }
}