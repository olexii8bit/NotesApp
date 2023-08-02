package com.olexii8bit.notesapp.presentation

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import com.olexii8bit.notesapp.data.repository.model.Category
import com.olexii8bit.notesapp.data.repository.model.NoteDetails
import com.olexii8bit.notesapp.databinding.ActivityMainBinding
import com.olexii8bit.notesapp.presentation.category.CategoriesFragment
import com.olexii8bit.notesapp.presentation.editCategoryDialog.EditCategoryDialogFragment
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

        if (supportFragmentManager.backStackEntryCount != 0)
            binding.categoriesFragmentContainer.visibility = GONE

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                supportFragmentManager.popBackStackImmediate()
                if (supportFragmentManager.backStackEntryCount == 0)
                    binding.categoriesFragmentContainer.visibility = VISIBLE
            }

        })
    }

    override fun showEditNoteFragment(note: NoteDetails?, allCategories: List<Category>) {
        binding.categoriesFragmentContainer.visibility = GONE
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(
                binding.notesFragmentContainer.id,
                EditNoteFragment.newInstance(note, allCategories)
            )
            .commit()
    }

    override fun showNewCategoryDialog(
        lifecycleOwner: LifecycleOwner,
        onNewCategory: (Category) -> Unit,
    ) {
        EditCategoryDialogFragment.apply {
            this.show(supportFragmentManager, null)

            this.listenResult(NEW_CATEGORY_RESULT_KEY, supportFragmentManager, lifecycleOwner) {
                onNewCategory.invoke(it)
            }
        }
    }

    override fun showCategoryEditDialog(
        category: Category,
        lifecycleOwner: LifecycleOwner,
        onUpdateCategory: (Category) -> Unit,
        onDeleteCategory: (Category) -> Unit,
    ) {
        EditCategoryDialogFragment.apply {
            this.show(supportFragmentManager, category)

            this.listenResult(UPDATE_CATEGORY_RESULT_KEY, supportFragmentManager, lifecycleOwner) {
                onUpdateCategory.invoke(it)
            }

            this.listenResult(DELETE_CATEGORY_RESULT_KEY, supportFragmentManager, lifecycleOwner) {
                onDeleteCategory.invoke(it)
            }
        }
    }

    override fun goMainScreen() {
        binding.categoriesFragmentContainer.visibility = VISIBLE
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    override fun goBack() {
        onBackPressedDispatcher.onBackPressed()
    }

}