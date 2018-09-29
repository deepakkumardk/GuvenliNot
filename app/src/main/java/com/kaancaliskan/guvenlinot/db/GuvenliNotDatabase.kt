package com.kaancaliskan.guvenlinot.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

/*
The abstract class to create the instance of the Database which extends the RoomDatabase
 */
@Database(entities = [Note::class], version = 1)
abstract class GuvenliNotDatabase : RoomDatabase() {
    /*
    Abstract Dao method
     */
    abstract fun noteDao(): NoteDao

    /*
    It behaves like a singleton in java to restrict the access of database instance to only one
     */
    companion object {
        private var INSTANCE: GuvenliNotDatabase? = null
        fun getInstance(context: Context): GuvenliNotDatabase? {
            if (INSTANCE == null) {
                synchronized(GuvenliNotDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context, GuvenliNotDatabase::class.java, "guvenli_notes_db").fallbackToDestructiveMigration().build()
                }
            }
            return INSTANCE
        }

        /*
        Destroy the instance when activity get destroyed
         */
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}