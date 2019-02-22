package com.kaancaliskan.guvenlinot.db

import androidx.room.*

/**
 * Dao interface to contain all the CRUD methods
 */
@Dao
interface NoteDao {
    /**
     * Fetch all the notes from the database
     */
    @Query("SELECT * FROM guvenli_not")
    fun getAllNotes(): List<Note>

    /**
     * insert the note into database
     */
    @Insert
    fun insertNote(note: Note)

    /**
     * update the note in database
     */
    @Update
    fun updateNote(note: Note)

    /**
     * delete the note from the database
     */
    @Delete
    fun deleteNote(note: Note)
}