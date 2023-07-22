package com.olexii8bit.notesapp.data.room.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "categories",
    indices = [Index(value = ["categoryId"], unique = true)]
)
data class CategoryEntity(
    var name: String
) {
    @PrimaryKey(autoGenerate = true)
    var categoryId: Long = 0
}