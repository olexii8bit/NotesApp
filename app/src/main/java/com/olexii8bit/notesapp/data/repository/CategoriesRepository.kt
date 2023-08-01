package com.olexii8bit.notesapp.data.repository

import com.olexii8bit.notesapp.data.repository.model.Category
import com.olexii8bit.notesapp.data.room.dao.CategoryDao

interface CategoriesRepository {
    fun addCategory(category: Category)
    fun updateCategory(category: Category)
    fun deleteCategory(category: Category)
    fun getAllCategories(): List<Category>

    class CategoryRepositoryImpl(
        private val categoryDao: CategoryDao,
    ) : CategoriesRepository {

        override fun addCategory(category: Category) = categoryDao.insert(category.toEntity())

        override fun updateCategory(category: Category) = categoryDao.update(category.toEntity())

        override fun deleteCategory(category: Category) = categoryDao.delete(category.toEntity())

        override fun getAllCategories(): List<Category> =
            mutableListOf<Category>().also { result: MutableList<Category> ->
                categoryDao.getAll().forEach {
                    result.add(it.toModel())
                }
            }
    }
}
