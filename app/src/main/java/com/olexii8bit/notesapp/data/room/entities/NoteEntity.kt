package com.olexii8bit.notesapp.data.room.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(
    tableName = "notes",
    foreignKeys = [ForeignKey(
        entity = CategoryEntity::class,
        parentColumns = ["categoryId"],
        childColumns = ["noteCategoryId"],
        onDelete = ForeignKey.SET_NULL
    )],
    indices = [Index(value = ["noteId"], unique = true), Index(value = ["noteCategoryId"])]
)
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val noteId: Int = 0,
    val title: String,
    val content: String,
    val noteCategoryId: Int? = null,
    val createdAt: LocalDateTime = LocalDateTime.now()
)
