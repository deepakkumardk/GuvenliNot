package com.kaancaliskan.guvenlinot.db

import android.content.Context
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.jetbrains.anko.doAsync

/**
 * The repository class to manage all the CRUD operations
 */
class NotesRepository(context: Context) {
    private var database: GuvenliNotDatabase? = GuvenliNotDatabase.getInstance(context)
    private lateinit var noteList: MutableList<Note>
    private lateinit var searchList: LiveData<MutableList<Note>>

    /**
     * fetch all notes from database
     */
    fun getAllNotes(): MutableList<Note> {
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
     * update the note on database
     */
    fun updateNote(note: Note) = doAsync { database?.noteDao()?.updateNote(note) }

    /**
     * delete note from database
     */
    fun deleteNote(note: Note) = doAsync { database?.noteDao()?.deleteNote(note) }

    fun search(query: String): LiveData<MutableList<Note>> {
        runBlocking {
            async(Dispatchers.Default) {
                searchList = database?.noteDao()?.search(query)!!
                return@async searchList
            }.await()
        }
        return searchList
    }
}