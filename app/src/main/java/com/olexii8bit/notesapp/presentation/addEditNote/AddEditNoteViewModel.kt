package com.olexii8bit.notesapp.presentation.addEditNote

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.olexii8bit.notesapp.App
import com.olexii8bit.notesapp.data.repository.CategoriesRepository
import com.olexii8bit.notesapp.data.repository.NotesRepository
import com.olexii8bit.notesapp.data.repository.model.Note
import com.olexii8bit.notesapp.data.repository.model.NoteDetails
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class AddEditNoteViewModel(app: Application) : AndroidViewModel(app) {
    private val notesRepository: NotesRepository =
        NotesRepository.NotesRepositoryImpl((app as App).database.noteDao())
    private val categoriesRepository: CategoriesRepository =
        CategoriesRepository.CategoryRepositoryImpl((app as App).database.categoryDao())


    fun addNote(note: Note) =
        viewModelScope.launch(Dispatchers.IO) { notesRepository.addNote(note) }

    fun updateNote(note: Note) =
        viewModelScope.launch(Dispatchers.IO) {
            notesRepository.updateNote(note)
        }

    fun deleteNote(note: Note) =
        viewModelScope.launch(Dispatchers.IO) { notesRepository.deleteNote(note) }

    fun getAllCategoriesAsync() = viewModelScope.async(Dispatchers.IO) {
        categoriesRepository.getAllCategories()
    }

    fun getNoteWithCategoryAsync(note: Note): Deferred<NoteDetails> =
        viewModelScope.async(Dispatchers.IO) {
            notesRepository.getNoteWithCategory(note)
        }
}