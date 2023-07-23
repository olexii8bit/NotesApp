package com.olexii8bit.notesapp.data.room.entities

import androidx.room.Embedded
import com.olexii8bit.notesapp.data.repository.model.NoteWithCategory

data class NoteWithCategoryEntity(
    @Embedded val category: CategoryEntity? = null,
    @Embedded val note: NoteEntity,
) {
    fun toModel() = NoteWithCategory(
        category = this.category?.toModel(),
        note = this.note.toModel()
    )
}
