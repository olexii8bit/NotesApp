package com.olexii8bit.notesapp.presentation.note

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.olexii8bit.notesapp.App
import com.olexii8bit.notesapp.data.repository.NotesRepository
import com.olexii8bit.notesapp.data.repository.model.Category
import com.olexii8bit.notesapp.data.repository.model.Note
import com.olexii8bit.notesapp.data.repository.model.NoteDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesViewModel(app: Application): AndroidViewModel(app) {
    private val notesRepository: NotesRepository =
        NotesRepository.NotesRepositoryImpl((app as App).database.noteDao())

    private val _notes = MutableLiveData<List<Note>>()
    val notes: LiveData<List<Note>> get() = _notes

    private val _noteDetails = MutableLiveData<NoteDetails>()
    val noteDetails: LiveData<NoteDetails> get() = _noteDetails

    init {

    }

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
        _notes.postValue(notesRepository.getAllNotesByCategory(category))
    }

    fun getNoteWithCategory(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        _noteDetails.postValue(notesRepository.getNoteWithCategory(note))
    }
}