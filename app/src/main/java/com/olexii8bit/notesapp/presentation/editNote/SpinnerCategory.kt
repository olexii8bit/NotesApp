package com.olexii8bit.notesapp.presentation.editNote

import com.olexii8bit.notesapp.data.repository.model.Category

data class SpinnerCategory(
    val name: String,
    val categoryId: Long? = null
) {
    override fun toString() = name
}

fun Category.toSpinnerCategory() = SpinnerCategory(this.name, this.categoryId)