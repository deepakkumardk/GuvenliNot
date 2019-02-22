package com.kaancaliskan.guvenlinot.db

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.jetbrains.anko.doAsync

/**
 * The repository class to manage all the CRUD operations
 */
class NotesRepository(context: Context) {
    private var database: GuvenliNotDatabase? = GuvenliNotDatabase.getInstance(context)
    private lateinit var noteList: List<Note>

    /**
     * fetch all notes from database
     */
    fun getAllNotes(): List<Note> {
        runBlocking {
            async(Dispatchers.Default) {
                noteList = database?.noteDao()?.getAllNotes()!!
                return@async noteList
            }.await()
        }
        return noteList
    }

    /**
     * insert the note into database
     */
    fun insertNote(note: Note) = doAsync { database?.noteDao()?.insertNote(note) }

    /**
     * update the note into database
     */
    fun updateNote(note: Note) = doAsync { database?.noteDao()?.updateNote(note) }

    /**
     * delete the note into database
     */
    fun deleteNote(note: Note) = doAsync { database?.noteDao()?.deleteNote(note) }
}