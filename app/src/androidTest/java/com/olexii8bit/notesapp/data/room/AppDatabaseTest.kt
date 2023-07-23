package com.olexii8bit.notesapp.data.room

import android.content.Context
import androidx.test.InstrumentationRegistry
import com.olexii8bit.notesapp.data.room.dao.CategoryDao
import com.olexii8bit.notesapp.data.room.dao.NoteDao
import com.olexii8bit.notesapp.data.room.entities.CategoryEntity
import com.olexii8bit.notesapp.data.room.entities.NoteEntity
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

internal class AppDatabaseTest {

    private lateinit var context: Context
    private lateinit var appDatabase: AppDatabase
    private lateinit var noteDao: NoteDao
    private lateinit var categoryDao: CategoryDao

    @Before
    fun setUp() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
        appDatabase = AppDatabase.Mock(context).provideDataBase()
        noteDao = appDatabase.noteDao()
        categoryDao = appDatabase.categoryDao()
    }
    @After
    fun tearDown() = appDatabase.close()

    @Test
    fun inserts_note() {
        NoteEntity("Title", "Content").let {
            noteDao.insert(it)
            assertTrue(noteDao.getAll().size == 1)
            assertTrue(noteDao.getAll().contains(it))
        }
    }
    @Test
    fun deletes_note() {
        NoteEntity("Title1", "Content").let { noteDao.insert(it) }
        NoteEntity("Title2", "Content").let { noteDao.insert(it) }
        NoteEntity("Title3", "Content").let { noteDao.insert(it) }
        assertTrue(noteDao.getAll().size == 3)
        noteDao.getAll().random().let {
            noteDao.delete(it)
            assertFalse(noteDao.getAll().contains(it))
        }
        assertTrue(noteDao.getAll().size == 2)
    }
    @Test
    fun updates_note() {
        val note1 = NoteEntity("Title1", "Content")
        val note2 = NoteEntity("Title2", "Content")
        val note3 = NoteEntity("Title3", "Content")
        noteDao.insert(note1)
        noteDao.insert(note2)
        noteDao.insert(note3)
        noteDao.getAll().random().let {
            val old = it.copy()
            it.title = "Another title"
            it.content = "Another content"
            noteDao.update(it)
            assertTrue(noteDao.getAll().contains(it))
            assertFalse(noteDao.getAll().contains(old))
        }
    }
    @Test
    fun returns_all_notes() {
        val note1 = NoteEntity("Title1", "Content")
        val note2 = NoteEntity("Title2", "Content")
        val note3 = NoteEntity("Title3", "Content")
        noteDao.insert(note1)
        noteDao.insert(note2)
        noteDao.insert(note3)
        noteDao.getAll().let {
            assertTrue(it.size == 3)
            assertTrue(it.contains(note1))
            assertTrue(it.contains(note2))
            assertTrue(it.contains(note3))
        }
    }
    @Test
    fun returns_all_notes_by_category() {
        CategoryEntity("Work").let { categoryDao.insert(it) }
        CategoryEntity("Films").let { categoryDao.insert(it) }

        val notesWithWorkCategory = mutableListOf<NoteEntity>()
        (1..10).random().let { count: Int ->
            for (number in 1..count) {
                NoteEntity("Title", "withWorkCategory$number").let {
                    it.noteCategoryId = 1
                    noteDao.insert(it)
                    notesWithWorkCategory.add(it)
                }
            }
        }

        val notesWithFilmsCategory = mutableListOf<NoteEntity>()
        (1..10).random().let { count: Int ->
            for (number in 1..count) {
                NoteEntity("Title", "withFilmsCategory$number").let {
                    it.noteCategoryId = 2
                    noteDao.insert(it)
                    notesWithFilmsCategory.add(it)
                }
            }
        }

        val notesWithNullCategory = mutableListOf<NoteEntity>()
        (1..10).random().let { count: Int ->
            for (number in 1..count) {
                NoteEntity("Title", "withNullCategory$number").let {
                    it.noteCategoryId = null
                    noteDao.insert(it)
                    notesWithNullCategory.add(it)
                }
            }
        }

        noteDao.getNotesByCategory(1).let { notesByCategory ->
            assertTrue(notesByCategory.size == notesWithWorkCategory.size)
            notesByCategory.forEach {
                assertEquals(1L, it.noteCategoryId)
            }
            notesWithNullCategory.forEach {
                assertFalse(notesByCategory.contains(it))
            }
        }
        noteDao.getNotesByCategory(2).let { notesByCategory ->
            assertTrue(notesByCategory.size == notesWithFilmsCategory.size)
            notesByCategory.forEach {
                assertEquals(2L, it.noteCategoryId)
            }
            notesWithNullCategory.forEach {
                assertFalse(notesByCategory.contains(it))
            }
        }
    }
    @Test
    fun returns_note_with_category() {
        CategoryEntity("Work").let { categoryDao.insert(it) }
        CategoryEntity("Films").let { categoryDao.insert(it) }

        val note1 = NoteEntity("Title1", "Content")
        note1.noteCategoryId = 1L
        noteDao.insert(note1)

        val note2 = NoteEntity("Title2", "Content")
        note2.noteCategoryId = 2L
        noteDao.insert(note2)

        val note3 = NoteEntity("Title3", "Content")
        note3.noteCategoryId = null
        noteDao.insert(note3)

        noteDao.getNoteWithCategory(1).let {
            assertTrue(it.category?.categoryId == note1.noteCategoryId)
            assertTrue(it.note.title == note1.title)
        }
        noteDao.getNoteWithCategory(2).let {
            assertTrue(it.category?.categoryId == note2.noteCategoryId)
            assertTrue(it.note.title == note2.title)
        }
        noteDao.getNoteWithCategory(3).let {
            assertTrue(it.category == null)
            assertTrue(it.note.title == note3.title)
        }
    }
    @Test
    fun inserts_category() {
        CategoryEntity("Work").let {
            categoryDao.insert(it)
            assertTrue(categoryDao.getAll().size == 1)
            assertTrue(categoryDao.getAll().contains(it))
        }
    }
    @Test
    fun updates_category() {
        val category1 = CategoryEntity("Work")
        val category2 = CategoryEntity("Films")
        val category3 = CategoryEntity("TODO")
        categoryDao.insert(category1)
        categoryDao.insert(category2)
        categoryDao.insert(category3)
        categoryDao.getAll().random().let {
            val old = it.copy()
            it.name = "Study"
            categoryDao.update(it)
            assertTrue(categoryDao.getAll().contains(it))
            assertFalse(categoryDao.getAll().contains(old))
        }
    }
    @Test
    fun deletes_category() {
        val category1 = CategoryEntity("Work")
        val category2 = CategoryEntity("Films")
        categoryDao.insert(category1)
        categoryDao.insert(category2)
        val note = NoteEntity("Title1", "Content").also { it.noteCategoryId = 1 }
        noteDao.insert(note)

        categoryDao.getAll()[0].let {
            categoryDao.delete(it)
            assertFalse(categoryDao.getAll().contains(it))
        }
        assertTrue(categoryDao.getAll().size == 1)
        assertTrue(noteDao.getAll()[0].noteCategoryId == null)
    }
    @Test
    fun returns_all_categories() {
        val category1 = CategoryEntity("Work")
        val category2 = CategoryEntity("Films")
        val category3 = CategoryEntity("TODO")
        categoryDao.insert(category1)
        categoryDao.insert(category2)
        categoryDao.insert(category3)
        categoryDao.getAll().let {
            assertTrue(it.size == 3)
            assertTrue(it.contains(category1))
            assertTrue(it.contains(category2))
            assertTrue(it.contains(category3))
        }
    }
}