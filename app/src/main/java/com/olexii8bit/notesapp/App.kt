package com.olexii8bit.notesapp

import android.app.Application
import com.olexii8bit.notesapp.data.room.AppDatabase


class App : Application() {
    override fun onCreate() {
        super.onCreate()

        AppDatabase.init(applicationContext)
    }
}