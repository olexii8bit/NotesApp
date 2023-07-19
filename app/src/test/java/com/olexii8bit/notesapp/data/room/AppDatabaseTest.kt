package com.olexii8bit.notesapp.data.room

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.olexii8bit.notesapp.data.room.dao.CategoryDao
import com.olexii8bit.notesapp.data.room.dao.NoteDao
import com.olexii8bit.notesapp.data.room.entities.NoteEntity
import org.junit.After
import org.junit.Before
import org.junit.Test

internal class AppDatabaseTest {

    val note = NoteEntity(0, "Some title", "Some content", null)

    private lateinit var database: AppDatabase
    private lateinit var noteDao: NoteDao
    private lateinit var categoryDao: CategoryDao

    @Before
    fun before() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).build()
        noteDao = database.noteDao()
        categoryDao = database.categoryDao()
    }

    @After
    fun after() = database.close()

    @Test
    fun noteDao() {
    }

    @Test
    fun categoryDao() {
    }
}