package com.kaancaliskan.guvenlinot.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Insert
import androidx.room.Update
import androidx.room.Transaction
import androidx.room.OnConflictStrategy

/**
 * Dao interface to contain all the CRUD methods
 */
@Dao
interface NoteDao {
    /**
     * Fetch all the notes from the database
     */
    @Query("SELECT * FROM guvenli_not")
    fun getAllNotes(): MutableList<Note>
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
     * search the note in database
     */
    /**
    @Query("SELECT * FROM guvenli_not WHERE noteContent or noteTitle LIKE :text")
    fun search(text: String): LiveData<MutableList<Note>>
     */
    @Transaction
    fun updateData(notes: MutableList<Note>) {
        deleteAllNotes()
        insertAll(notes)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(notes: MutableList<Note>)

    @Query("DELETE FROM guvenli_not")
    fun deleteAllNotes()
}
