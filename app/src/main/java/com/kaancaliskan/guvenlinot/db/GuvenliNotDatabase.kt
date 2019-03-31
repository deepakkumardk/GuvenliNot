package com.kaancaliskan.guvenlinot.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context

/**
 * The abstract class to create the instance of the Database which extends the RoomDatabase
 */
@Database(entities = [Note::class], version = 1)
abstract class GuvenliNotDatabase : RoomDatabase() {
    /**
     * Abstract Dao method
     */
    abstract fun noteDao(): NoteDao

    /**
     * It behaves like a singleton in java to restrict the access of database instance to only one
     */
    companion object {
        private var INSTANCE: GuvenliNotDatabase? = null
        fun getInstance(context: Context): GuvenliNotDatabase? {
            if (INSTANCE == null) {
                synchronized(GuvenliNotDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context, GuvenliNotDatabase::class.java, "guvenli_not_db.sql").fallbackToDestructiveMigration().build()
                }
            }
            return INSTANCE
        }

        /**
         * Destroy the instance when activity get destroyed
         */
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}