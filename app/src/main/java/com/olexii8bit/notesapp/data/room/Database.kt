package com.olexii8bit.notesapp.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.olexii8bit.notesapp.data.room.dao.CategoryDao
import com.olexii8bit.notesapp.data.room.dao.NoteDao
import com.olexii8bit.notesapp.data.room.entities.CategoryEntity
import com.olexii8bit.notesapp.data.room.entities.NoteEntity

const val DB_NAME = "notes-db"

@Database(entities = [NoteEntity::class, CategoryEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao
    abstract fun categoryDao(): CategoryDao

    class Base(context: Context = applicationContext) {
        private val database by lazy {
            return@lazy Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                DB_NAME
            ).build()
        }

        fun provideDataBase(): AppDatabase = database
    }

    class Mock(context: Context = applicationContext) {
        private val database by lazy {
            return@lazy Room.inMemoryDatabaseBuilder(
                context,
                AppDatabase::class.java,
            ).build()
        }

        fun provideDataBase(): AppDatabase = database
    }

    companion object {
        fun init(applicationContext: Context) {
            this.applicationContext = applicationContext
        }
        private lateinit var applicationContext: Context
    }
}