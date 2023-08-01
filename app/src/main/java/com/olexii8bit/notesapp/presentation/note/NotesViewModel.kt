package com.olexii8bit.notesapp.presentation.note

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.olexii8bit.notesapp.App
import com.olexii8bit.notesapp.data.repository.CategoriesRepository
import com.olexii8bit.notesapp.data.repository.NotesRepository
import com.olexii8bit.notesapp.data.repository.model.Category
import com.olexii8bit.notesapp.data.repository.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class NotesViewModel(app: Application) : AndroidViewModel(app) {
    private val notesRepository: NotesRepository =
        NotesRepository.NotesRepositoryImpl((app as App).database.noteDao())
    private val categoriesRepository: CategoriesRepository =
        CategoriesRepository.CategoryRepositoryImpl((app as App).database.categoryDao())

    private val _notes = MutableLiveData<List<Note>>()
    val notes: LiveData<List<Note>> get() = _notes

    fun loadNotes() =
        viewModelScope.launch(Dispatchers.IO) { _notes.postValue(notesRepository.getAllNotes()) }

    fun addNote(note: Note) =
        viewModelScope.launch(Dispatchers.IO) { notesRepository.addNote(note) }
            .invokeOnCompletion { loadNotes() }

    fun updateNote(note: Note) =
        viewModelScope.launch(Dispatchers.IO) { notesRepository.updateNote(note) }
            .invokeOnCompletion { loadNotes() }

    fun deleteNote(note: Note) =
        viewModelScope.launch(Dispatchers.IO) { notesRepository.deleteNote(note) }
            .invokeOnCompletion { loadNotes() }

    fun getAllNotesByCategory(category: Category) = viewModelScope.launch(Dispatchers.IO) {
        notesRepository.getAllNotesByCategory(category).let {
            Log.d("bbb", "it - " + it)
            _notes.postValue(it)
        }
    }

    fun getNoteWithCategoryAsync(note: Note) = viewModelScope.async(Dispatchers.IO) {
        notesRepository.getNoteWithCategory(note)
    }

    fun getAllCategoriesAsync() = viewModelScope.async(Dispatchers.IO) {
        categoriesRepository.getAllCategories()
    }
}