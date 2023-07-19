package com.olexii8bit.notesapp.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.olexii8bit.notesapp.data.room.entities.NoteEntity
import com.olexii8bit.notesapp.data.room.entities.NoteWithCategory

@Dao
interface NoteDao {
    @Insert
    fun insert(note: NoteEntity)

    @Update
    fun update(note: NoteEntity)

    @Delete
    fun delete(note: NoteEntity)

    @Query("SELECT * FROM notes")
    fun getAll(): List<NoteEntity>

    @Query("SELECT notes.* FROM notes " +
            "JOIN categories ON notes.noteCategoryId = categories.categoryId " +
            "WHERE categories.categoryId = :categoryId")
    fun getNotesByCategory(categoryId: Int): List<NoteEntity>

    @Query("SELECT categories.*, notes.* " +
            "FROM notes " +
            "LEFT JOIN categories ON notes.noteCategoryId = categories.categoryId " +
            "WHERE notes.noteId = :noteId")
    fun getNoteWithCategory(noteId: Int): NoteWithCategory?


}