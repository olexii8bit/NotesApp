package com.olexii8bit.notesapp.presentation.addEditNote

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.R.*
import com.olexii8bit.notesapp.data.repository.model.Category
import com.olexii8bit.notesapp.data.repository.model.Note
import com.olexii8bit.notesapp.databinding.FragmentEditNoteBinding
import com.olexii8bit.notesapp.presentation.navigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddEditNoteFragment : DialogFragment() {

    private val model by lazy { ViewModelProvider(requireActivity())[AddEditNoteViewModel::class.java] }
    private lateinit var binding: FragmentEditNoteBinding
    private lateinit var spinnerAdapter: ArrayAdapter<SpinnerCategory>

    @Suppress("DEPRECATION")
    private val receivedNote by lazy {
        (this.arguments?.getParcelable(NOTE_ARG) as? Note)
    }

    companion object {
        const val NOTE_ARG = "NOTE_ARG"

        fun newInstance(note: Note?): AddEditNoteFragment {
            return AddEditNoteFragment().also {
                it.arguments = bundleOf(NOTE_ARG to note)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentEditNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var noteCategory: Category? = null
        var allCategories: List<Category> = emptyList()

        lifecycleScope.launch(Dispatchers.Main) {
            if (receivedNote != null) {
                noteCategory = withContext(Dispatchers.IO) {
                    model.getNoteWithCategoryAsync(receivedNote!!).await().category
                }
            }
            allCategories = withContext(Dispatchers.IO) {
                model.getAllCategoriesAsync().await()
            }
        }.invokeOnCompletion {
            val spinnerValues = ArrayList<SpinnerCategory>(
                allCategories.map { it.toSpinnerCategory() }
            )
            spinnerValues.add(0, SpinnerCategory("Without Category", null))
            spinnerAdapter =
                ArrayAdapter(
                    requireContext(),
                    layout.support_simple_spinner_dropdown_item,
                    spinnerValues
                )
            binding.categorySpinner.adapter = spinnerAdapter

            if (receivedNote == null) {
                binding.categorySpinner.setSelection(0)
                binding.deleteButton.isVisible = false
            } else {
                if (noteCategory != null) {
                    binding.categorySpinner.setSelection(
                        spinnerAdapter.getPosition(noteCategory!!.toSpinnerCategory())
                    )
                }
                binding.titleEditText.setText(receivedNote!!.title, TextView.BufferType.EDITABLE)
                binding.contentEditText.setText(receivedNote!!.content, TextView.BufferType.EDITABLE)
            }
        }

        setOnBack()
        setOnDelete()
    }

    private fun setOnBack() {
        binding.backButton.setOnClickListener {
            if (binding.titleEditText.text.isBlank() && binding.contentEditText.text.isBlank()) {
                navigator().goBack()
            } else {
                if (receivedNote == null) {
                    val newNote = Note(
                        title = binding.titleEditText.text.toString(),
                        content = binding.contentEditText.text.toString(),
                        noteCategoryId = (binding.categorySpinner.selectedItem as SpinnerCategory).categoryId
                    )
                    Log.d("NOTE_COMMUNICATION", "NEW_NOTE - $newNote")
                    model.addNote(newNote)
                } else {
                    val updatedNote = receivedNote!!.copy(
                        title = binding.titleEditText.text.toString(),
                        content = binding.contentEditText.text.toString(),
                        noteCategoryId = (binding.categorySpinner.selectedItem as SpinnerCategory).categoryId
                    )
                    Log.d("NOTE_COMMUNICATION", "UPDATE_NOTE - $updatedNote")
                    model.updateNote(updatedNote)
                }
                navigator().goBack()
            }
        }
    }

    private fun setOnDelete() {
        binding.deleteButton.setOnClickListener {
            val deleteNote = receivedNote!!.copy()
            Log.d("NOTE_COMMUNICATION", "DELETE_NOTE - $deleteNote")
            model.deleteNote(deleteNote)
        }
    }
}