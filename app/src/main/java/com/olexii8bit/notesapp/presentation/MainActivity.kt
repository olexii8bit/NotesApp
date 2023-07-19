package com.olexii8bit.notesapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.olexii8bit.notesapp.R
import com.olexii8bit.notesapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}