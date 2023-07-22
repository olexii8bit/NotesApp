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

@Database(entities = [NoteEntity::class, CategoryEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao
    abstract fun categoryDao(): CategoryDao

    companion object {
        private const val DB_NAME = "notes-db"
        private lateinit var applicationContext: Context

        fun init(applicationContext: Context) {
            this.applicationContext = applicationContext
        }

        val instance: AppDatabase by lazy {
            Room.databaseBuilder(applicationContext,
                AppDatabase::class.java, DB_NAME)
                .build()
        }
    }
}