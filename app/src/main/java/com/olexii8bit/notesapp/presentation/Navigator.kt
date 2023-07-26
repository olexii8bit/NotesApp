package com.olexii8bit.notesapp.presentation

import androidx.fragment.app.Fragment
import com.olexii8bit.notesapp.data.repository.model.Category
import com.olexii8bit.notesapp.data.repository.model.Note

fun Fragment.navigator(): Navigator {
    return requireActivity() as Navigator
}

interface Navigator {
    
    fun showEditNoteFragment(note: Note?)
    fun showCategoryEditDialog(category: Category?)
    fun toMainMenu()

}