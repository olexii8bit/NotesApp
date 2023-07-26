package com.olexii8bit.notesapp.data.repository.model

import android.os.Parcelable
import com.olexii8bit.notesapp.data.room.entities.CategoryEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    var name: String,
    val categoryId: Long = 0
): Parcelable {
    fun toEntity() = CategoryEntity(
        this.name,
    ).also { it.categoryId = this.categoryId }
}