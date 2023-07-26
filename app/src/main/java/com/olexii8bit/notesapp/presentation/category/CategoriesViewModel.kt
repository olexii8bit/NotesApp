package com.olexii8bit.notesapp.presentation.category

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.olexii8bit.notesapp.App
import com.olexii8bit.notesapp.data.repository.CategoriesRepository
import com.olexii8bit.notesapp.data.repository.model.Category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoriesViewModel(app: Application): AndroidViewModel(app) {
    private val categoriesRepository: CategoriesRepository =
        CategoriesRepository.CategoryRepositoryImpl((app as App).database.categoryDao())

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> get() = _categories

    init {

    }

    fun loadCategories() = viewModelScope.launch(Dispatchers.IO) {
        _categories.postValue(categoriesRepository.getAllCategories())
    }

    fun addCategory(category: Category) = viewModelScope.launch(Dispatchers.IO) {
        categoriesRepository.addCategory(category)
    }.invokeOnCompletion { loadCategories() }

    fun updateCategory(category: Category) = viewModelScope.launch(Dispatchers.IO) {
        categoriesRepository.updateCategory(category)
    }.invokeOnCompletion { loadCategories() }

    fun deleteCategory(category: Category) = viewModelScope.launch(Dispatchers.IO) {
        categoriesRepository.deleteCategory(category)
    }.invokeOnCompletion { loadCategories() }
}