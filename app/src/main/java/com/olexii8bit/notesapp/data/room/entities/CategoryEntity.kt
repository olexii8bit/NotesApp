package com.olexii8bit.notesapp.data.room.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.olexii8bit.notesapp.data.repository.model.Category

@Entity(
    tableName = "categories",
    indices = [Index(value = ["categoryId"], unique = true)]
)
data class CategoryEntity(
    var name: String
) {
    @PrimaryKey(autoGenerate = true)
    var categoryId: Long = 0

    fun toModel() = Category(
        this.name,
        this.categoryId
    )
}