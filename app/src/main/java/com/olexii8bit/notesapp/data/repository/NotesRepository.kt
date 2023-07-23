package com.olexii8bit.notesapp.data.repository

import com.olexii8bit.notesapp.data.repository.model.Category
import com.olexii8bit.notesapp.data.repository.model.Note
import com.olexii8bit.notesapp.data.repository.model.NoteWithCategory
import com.olexii8bit.notesapp.data.room.dao.NoteDao



interface NotesRepository {
    fun addNote(note: Note)
    fun updateNote(note: Note)
    fun deleteNote(note: Note)
    fun getAllNotes(): List<Note>
    fun getAllNotesByCategory(category: Category): List<Note>
    fun getNoteWithCategory(category: Category): NoteWithCategory

    class NotesRepositoryImpl(
        private val noteDao: NoteDao
    ): NotesRepository {

        override fun addNote(note: Note) = noteDao.insert(note.toEntity())

        override fun updateNote(note: Note) = noteDao.update(note.toEntity())

        override fun deleteNote(note: Note) = noteDao.delete(note.toEntity())

        override fun getAllNotes(): List<Note> =
            mutableListOf<Note>().also { result: MutableList<Note> ->
                noteDao.getAll().forEach { result.add(it.toModel()) }
            }

        override fun getAllNotesByCategory(category: Category): List<Note> =
            mutableListOf<Note>().also { result: MutableList<Note> ->
                noteDao.getNotesByCategory(category.categoryId).forEach {
                    result.add(it.toModel())
                }
            }

        override fun getNoteWithCategory(category: Category): NoteWithCategory =
            noteDao.getNoteWithCategory(category.categoryId).toModel()

    }
}