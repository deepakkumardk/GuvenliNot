package com.kaancaliskan.guvenlinot

import android.content.Context
import android.content.SharedPreferences

private const val sharedPref = "preferences"

/**
 * This class makes easier to write SharedPreferences.
 * @author Hakkı Kaan Çalışkan
 */
class LocalData(private val context: Context) {

    private val sharedPreferences: SharedPreferences
        get() {
            return context.applicationContext.getSharedPreferences(sharedPref, Context.MODE_PRIVATE)
        }
    private val editor: SharedPreferences.Editor
        get() {
            return sharedPreferences.edit()
        }
    /**
     * This function writes the parameters.
     */
    fun write(title: String, content: String) {
        editor.putString(title, content).commit()
    }
    /**
     * This function reads note.
     */
    fun read(title: String): String {
        return sharedPreferences.getString(title, "")
    }
    /**
     * This is normal companion object. There is no trick :D
     */
    companion object {
        /**
         * Nothing important.
         */
        fun with(context: Context): LocalData{
            return LocalData(context)
        }
    }
}