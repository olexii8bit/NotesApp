package com.olexii8bit.notesapp.data.repository.model

import android.os.Parcelable
import com.olexii8bit.notesapp.data.room.entities.NoteEntity
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class Note(
    var title: String,
    var content: String,
    val createdAt: LocalDateTime? = null,
    val noteId: Long = 0,
    val noteCategoryId: Long? = null
): Parcelable {
    fun toEntity() = NoteEntity(
        this.title,
        this.content
    ).also {
        it.createdAt = this.createdAt ?: LocalDateTime.now()
        it.noteId = this.noteId
        it.noteCategoryId = this.noteCategoryId
    }
}
