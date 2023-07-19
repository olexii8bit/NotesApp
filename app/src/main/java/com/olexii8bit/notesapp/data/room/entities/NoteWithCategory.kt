package com.olexii8bit.notesapp.data.room.entities

import androidx.room.Embedded

data class NoteWithCategory(
    @Embedded val category: CategoryEntity? = null,
    @Embedded val note: NoteEntity,
)
