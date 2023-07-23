package com.olexii8bit.notesapp.presentation.note

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.olexii8bit.notesapp.App
import com.olexii8bit.notesapp.data.repository.NotesRepository
import com.olexii8bit.notesapp.data.repository.model.Category
import com.olexii8bit.notesapp.data.repository.model.Note
import com.olexii8bit.notesapp.data.repository.model.NoteDetails

class NotesViewModel(app: Application): AndroidViewModel(app) {
    private val notesRepository: NotesRepository =
        NotesRepository.NotesRepositoryImpl((app as App).database.noteDao())

    private val _notes = MutableLiveData<List<Note>>()
    val notes: LiveData<List<Note>> get() = _notes

    init {
        loadNotes()
    }

    fun loadNotes() { _notes.value = notesRepository.getAllNotes() }

    fun addNote(note: Note) {
        notesRepository.addNote(note)
        loadNotes()
    }

    fun updateNote(note: Note) {
        notesRepository.updateNote(note)
        loadNotes()
    }

    fun deleteNote(note: Note) {
        notesRepository.deleteNote(note)
        loadNotes()
    }

    fun getAllNotesByCategory(category: Category) {
        _notes.value = notesRepository.getAllNotesByCategory(category)
    }

    fun getNoteWithCategory(note: Note): NoteDetails = notesRepository.getNoteWithCategory(note)
}