package com.olexii8bit.notesapp.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.olexii8bit.notesapp.data.room.entities.CategoryEntity

@Dao
interface CategoryDao {
    @Insert
    fun insert(category: CategoryEntity)

    @Update
    fun update(category: CategoryEntity)

    @Delete
    fun delete(category: CategoryEntity)

    @Query("SELECT * FROM categories")
    fun getAll(): List<CategoryEntity>
}