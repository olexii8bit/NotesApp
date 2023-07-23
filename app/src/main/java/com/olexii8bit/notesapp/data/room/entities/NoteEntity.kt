package com.olexii8bit.notesapp.data.room.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.olexii8bit.notesapp.data.repository.model.Note
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
    var title: String,
    var content: String,
    var createdAt: LocalDateTime = LocalDateTime.now()
) {
    @PrimaryKey(autoGenerate = true)
    var noteId: Long = 0
    var noteCategoryId: Long? = null

    fun toModel() = Note(
        this.title,
        this.content,
        this.createdAt,
        this.noteId,
        this.noteCategoryId
    )
}
