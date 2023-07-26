package com.olexii8bit.notesapp

import android.app.Application
import com.olexii8bit.notesapp.data.room.AppDatabase


class App : Application() {
    lateinit var database: AppDatabase
    override fun onCreate() {
        super.onCreate()
        database = AppDatabase.Mock(applicationContext).provideDataBase()
    }
}