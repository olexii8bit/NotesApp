package com.olexii8bit.notesapp.data.repository.model

data class NoteWithCategory(
    val category: Category? = null,
    val note: Note,
)
