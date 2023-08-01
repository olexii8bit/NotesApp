package com.olexii8bit.notesapp.presentation.editNote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.google.android.material.R.*
import com.olexii8bit.notesapp.data.repository.model.Category
import com.olexii8bit.notesapp.data.repository.model.Note
import com.olexii8bit.notesapp.data.repository.model.NoteDetails
import com.olexii8bit.notesapp.databinding.FragmentEditNoteBinding
import com.olexii8bit.notesapp.presentation.navigator

class EditNoteFragment : DialogFragment() {

    private lateinit var binding: FragmentEditNoteBinding
    private lateinit var spinnerAdapter: ArrayAdapter<SpinnerCategory>

    @Suppress("DEPRECATION")
    private val receivedAllCategories by lazy {
        this.arguments?.getParcelableArrayList<Category>(ALL_CATEGORIES_ARG)
    }

    @Suppress("DEPRECATION")
    private val receivedCategory by lazy {
        (this.arguments?.getParcelable(CATEGORY_ARG) as? Category)
    }

    @Suppress("DEPRECATION")
    private val receivedNote by lazy {
        (this.arguments?.getParcelable(NOTE_ARG) as? Note)
    }

    companion object {
        const val NEW_NOTE_RESULT_KEY = "NEW_NOTE_RESULT_KEY"
        const val UPDATE_NOTE_RESULT_KEY = "UPDATE_NOTE_RESULT_KEY"
        const val DELETE_NOTE_RESULT_KEY = "DELETE_NOTE_RESULT_KEY"
        const val ALL_CATEGORIES_ARG = "ALL_CATEGORIES_ARG"
        const val NOTE_ARG = "NOTE_ARG"
        const val CATEGORY_ARG = "CATEGORY_ARG"

        fun newInstance(data: NoteDetails?, allCategories: List<Category>): EditNoteFragment {
            return EditNoteFragment().also {
                it.arguments = bundleOf(
                    ALL_CATEGORIES_ARG to allCategories,
                    CATEGORY_ARG to data?.category,
                    NOTE_ARG to data?.note
                )
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


        val spinnerValues = ArrayList<SpinnerCategory>(
            receivedAllCategories?.map { it.toSpinnerCategory() } ?: ArrayList()
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
            if (receivedCategory != null) {
                binding.categorySpinner.setSelection(
                    spinnerAdapter.getPosition(receivedCategory!!.toSpinnerCategory())
                )
            }
            binding.titleEditText.setText(receivedNote!!.title, TextView.BufferType.EDITABLE)
            binding.contentEditText.setText(receivedNote!!.content, TextView.BufferType.EDITABLE)
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
                    setFragmentResult(
                        NEW_NOTE_RESULT_KEY,
                        bundleOf(NOTE_ARG to newNote)
                    )
                } else {
                    val updatedNote = receivedNote!!.copy(
                        title = binding.titleEditText.text.toString(),
                        content = binding.contentEditText.text.toString(),
                        noteCategoryId = (binding.categorySpinner.selectedItem as SpinnerCategory).categoryId
                    )
                    setFragmentResult(
                        UPDATE_NOTE_RESULT_KEY,
                        bundleOf(NOTE_ARG to updatedNote)
                    )
                }
                navigator().goBack()
            }
        }
    }

    private fun setOnDelete() {
        binding.deleteButton.setOnClickListener {
            val deleteNote = receivedNote!!.copy()
            setFragmentResult(DELETE_NOTE_RESULT_KEY, bundleOf(NOTE_ARG to deleteNote))
            navigator().goMainScreen()
        }
    }
}