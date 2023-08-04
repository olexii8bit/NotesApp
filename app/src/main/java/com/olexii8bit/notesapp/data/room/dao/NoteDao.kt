package com.olexii8bit.notesapp.data.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.olexii8bit.notesapp.data.room.entities.NoteEntity
import com.olexii8bit.notesapp.data.room.entities.NoteWithCategoryEntity

@Dao
interface NoteDao {
    @Insert
    fun insert(note: NoteEntity)

    @Update
    fun update(note: NoteEntity)

    @Delete
    fun delete(note: NoteEntity)

    @Query("SELECT * FROM notes")
    fun getAll(): LiveData<List<NoteEntity>>

    @Query("SELECT * FROM notes " +
            "WHERE noteCategoryId LIKE :categoryId")
    fun getNotesByCategory(categoryId: Long): LiveData<List<NoteEntity>>

    @Query("SELECT categories.*, notes.* " +
            "FROM notes " +
            "LEFT JOIN categories ON notes.noteCategoryId = categories.categoryId " +
            "WHERE notes.noteId = :noteId")
    fun getNoteWithCategory(noteId: Long): NoteWithCategoryEntity


}