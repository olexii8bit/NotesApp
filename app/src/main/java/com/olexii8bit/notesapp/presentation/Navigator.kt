package com.olexii8bit.notesapp.presentation

import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.olexii8bit.notesapp.data.repository.model.Category
import com.olexii8bit.notesapp.data.repository.model.NoteDetails

fun Fragment.navigator(): Navigator {
    return requireActivity() as Navigator
}

interface Navigator {

    fun showEditNoteFragment(note: NoteDetails?, allCategories: List<Category>)

    fun showNewCategoryDialog(
        lifecycleOwner: LifecycleOwner,
        onNewCategory: (Category) -> Unit,
    )

    fun showCategoryEditDialog(
        category: Category,
        lifecycleOwner: LifecycleOwner,
        onUpdateCategory: (Category) -> Unit,
        onDeleteCategory: (Category) -> Unit,
    )

    fun goMainScreen()
    fun goBack()

}