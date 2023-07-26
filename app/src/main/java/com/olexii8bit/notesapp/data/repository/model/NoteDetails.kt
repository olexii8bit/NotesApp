package com.olexii8bit.notesapp.data.repository.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NoteDetails(
    val category: Category? = null,
    val note: Note,
): Parcelable
