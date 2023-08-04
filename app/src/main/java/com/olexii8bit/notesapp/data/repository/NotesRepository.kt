package com.olexii8bit.notesapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.olexii8bit.notesapp.data.repository.model.Category
import com.olexii8bit.notesapp.data.repository.model.Note
import com.olexii8bit.notesapp.data.repository.model.NoteDetails
import com.olexii8bit.notesapp.data.room.dao.NoteDao
import com.olexii8bit.notesapp.data.room.entities.NoteEntity


interface NotesRepository {
    fun addNote(note: Note)
    fun updateNote(note: Note)
    fun deleteNote(note: Note)
    fun getLiveNotes(): LiveData<List<Note>>
    fun getLiveNotesByCategory(category: Category): LiveData<List<Note>>
    fun getNoteWithCategory(note: Note): NoteDetails

    class NotesRepositoryImpl(
        private val noteDao: NoteDao
    ): NotesRepository {

        override fun addNote(note: Note) = noteDao.insert(note.toEntity())

        override fun updateNote(note: Note) = noteDao.update(note.toEntity())

        override fun deleteNote(note: Note) = noteDao.delete(note.toEntity())

        override fun getLiveNotes(): LiveData<List<Note>> = noteDao.getAll()
            .map { entityList: List<NoteEntity> ->
                entityList.map { it.toModel() }
            }

        override fun getLiveNotesByCategory(category: Category): LiveData<List<Note>> =
            noteDao.getNotesByCategory(category.categoryId).map { entityList: List<NoteEntity> ->
                entityList.map { it.toModel() }
            }

        override fun getNoteWithCategory(note: Note): NoteDetails =
            noteDao.getNoteWithCategory(note.noteId).toModel()

    }
}