package com.olexii8bit.notesapp.presentation.note

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.olexii8bit.notesapp.App
import com.olexii8bit.notesapp.data.repository.NotesRepository
import com.olexii8bit.notesapp.data.repository.model.Category
import com.olexii8bit.notesapp.data.repository.model.Note

class NotesViewModel(app: Application) : AndroidViewModel(app) {
    private val notesRepository: NotesRepository =
        NotesRepository.NotesRepositoryImpl((app as App).database.noteDao())

    val notes: LiveData<List<Note>>
        get() = notesRepository.getLiveNotes()
    val notesByCategory: LiveData<List<Note>>
        get() = notesRepository.getLiveNotesByCategory(_selectedCategory ?: Category(""))

    private var _selectedCategory: Category? = null
    val hasSelectedCategory get() = _selectedCategory != null

    fun setCategory(category: Category?) {
        _selectedCategory = category
    }
}