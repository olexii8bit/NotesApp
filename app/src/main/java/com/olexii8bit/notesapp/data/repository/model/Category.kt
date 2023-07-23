package com.olexii8bit.notesapp.data.repository.model

import com.olexii8bit.notesapp.data.room.entities.CategoryEntity

data class Category(
    var name: String,
    val categoryId: Long = 0
) {
    fun toEntity() = CategoryEntity(
        this.name,
    ).also { it.categoryId = this.categoryId }
}