package com.kaancaliskan.guvenlinot.db

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull

/*
Note model class using the Room-persistence
 */
@Entity(tableName = "guvenli_not")
data class Note(
        @PrimaryKey(autoGenerate = true)
        @NonNull
        var id: Int = 0,
        var noteTitle: String,
        var noteContent: String
)