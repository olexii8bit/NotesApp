package com.olexii8bit.notesapp.data.repository.model

import com.olexii8bit.notesapp.data.room.entities.NoteEntity
import java.time.LocalDateTime

data class Note(
    var title: String,
    var content: String,
    val createdAt: LocalDateTime? = null,
    val noteId: Long = 0,
    val noteCategoryId: Long? = null
) {
    fun toEntity() = NoteEntity(
        this.title,
        this.content
    ).also {
        it.createdAt = this.createdAt ?: LocalDateTime.now()
        it.noteId = this.noteId
        it.noteCategoryId = this.noteCategoryId
    }
}
