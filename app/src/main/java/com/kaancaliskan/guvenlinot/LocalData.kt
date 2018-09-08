package com.kaancaliskan.guvenlinot

import android.content.Context
import android.content.SharedPreferences


private const val sharedPref = "preferences"

class LocalData(private val context: Context) {

    private val sharedPreferences: SharedPreferences
        get() {
            return context.applicationContext.getSharedPreferences(sharedPref, Context.MODE_PRIVATE)
        }
    private val editor: SharedPreferences.Editor
        get() {
            return sharedPreferences.edit()
        }
    fun write(title: String, content: String) {
        editor.putString(title, content).commit()
    }
    fun read(title: String): String {
        return sharedPreferences.getString(title, "")
    }
    companion object {
        fun with(context: Context): LocalData{
            return LocalData(context)
        }
    }
}