package com.olexii8bit.notesapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import com.olexii8bit.notesapp.data.repository.model.Category
import com.olexii8bit.notesapp.data.repository.model.Note
import com.olexii8bit.notesapp.databinding.ActivityMainBinding
import com.olexii8bit.notesapp.presentation.category.CategoriesFragment
import com.olexii8bit.notesapp.presentation.editNote.EditNoteFragment
import com.olexii8bit.notesapp.presentation.note.NotesFragment

class MainActivity : AppCompatActivity(), Navigator {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(binding.categoriesFragmentContainer.id, CategoriesFragment.newInstance())
                .commit()

            supportFragmentManager
                .beginTransaction()
                .add(binding.notesFragmentContainer.id, NotesFragment.newInstance())
                .commit()
        }


        binding.notesFragmentContainer
    }

    override fun showEditNoteFragment(note: Note?) {
        binding.categoriesFragmentContainer.isVisible = false
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(binding.notesFragmentContainer.id, EditNoteFragment.newInstance(note))
            .commit()
    }

    override fun showCategoryEditDialog(category: Category?) {
        TODO("Not yet implemented")
    }

    override fun toMainMenu() {
        binding.categoriesFragmentContainer.isVisible = true
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
}