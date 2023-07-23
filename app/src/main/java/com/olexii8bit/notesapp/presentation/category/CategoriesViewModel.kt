package com.olexii8bit.notesapp.presentation.category

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.olexii8bit.notesapp.App
import com.olexii8bit.notesapp.data.repository.CategoriesRepository
import com.olexii8bit.notesapp.data.repository.model.Category

class CategoriesViewModel(app: Application): AndroidViewModel(app) {
    private val categoriesRepository: CategoriesRepository =
        CategoriesRepository.CategoryRepositoryImpl((app as App).database.categoryDao())

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> get() = _categories

    init {
        loadCategories()
    }

    fun loadCategories() { _categories.value = categoriesRepository.getAllCategories() }

    fun addCategory(category: Category) {
        categoriesRepository.addCategory(category)
        loadCategories()
    }

    fun updateCategory(category: Category) {
        categoriesRepository.updateCategory(category)
        loadCategories()
    }

    fun deleteCategory(category: Category) {
        categoriesRepository.deleteCategory(category)
        loadCategories()
    }
}