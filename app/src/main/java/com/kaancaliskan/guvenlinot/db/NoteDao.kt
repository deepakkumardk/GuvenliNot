package com.kaancaliskan.guvenlinot.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

@Dao
interface NoteDao {
    @Query("SELECT * FROM guvenli_not")
    fun getAllNotes(): List<Note>

    @Insert
    fun insertNote(note: Note)

    @Update
    fun updateNote(note: Note)

    @Delete
    fun deleteNote(note: Note)
}