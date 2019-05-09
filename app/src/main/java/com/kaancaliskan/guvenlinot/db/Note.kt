package com.kaancaliskan.guvenlinot.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.annotation.NonNull

/**
 * Note model class using the Room-persistence
 */
@Entity(tableName = "guvenli_not")
data class Note(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    var noteId: Long = 0,
    var noteTitle: String,
    var noteContent: String,
    var date: String
)
