package com.kaancaliskan.guvenlinot.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(entities = [Note::class], version = 1)
abstract class GuvenliNotDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao

    companion object {
        private var INSTANCE: GuvenliNotDatabase? = null
        fun getInstance(context: Context): GuvenliNotDatabase? {
            if (INSTANCE == null) {
                synchronized(GuvenliNotDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context,GuvenliNotDatabase::class.java,"guvenli_notes_db").fallbackToDestructiveMigration().build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}