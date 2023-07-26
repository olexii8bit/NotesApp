package com.olexii8bit.notesapp.presentation.editNote

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.olexii8bit.notesapp.data.repository.model.Note
import com.olexii8bit.notesapp.databinding.FragmentEditNoteBinding
import com.olexii8bit.notesapp.presentation.navigator


class EditNoteFragment : DialogFragment() {

    private lateinit var binding: FragmentEditNoteBinding


    companion object {
        const val NEW_NOTE_RESULT_KEY = "NEW_NOTE_RESULT_KEY"
        const val UPDATE_NOTE_RESULT_KEY = "UPDATE_NOTE_RESULT_KEY"
        const val DELETE_NOTE_RESULT_KEY = "DELETE_NOTE_RESULT_KEY"
        const val NOTE_ARG = "ARG_NOTE"

        fun newInstance(note: Note?): EditNoteFragment {
            val fragment = EditNoteFragment()
            fragment.arguments = bundleOf(NOTE_ARG to note)
            return fragment
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

        val receivedNote = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            this.arguments?.getParcelable(NOTE_ARG, Note::class.java)
        } else {
            @Suppress("DEPRECATION")
            this.arguments?.getParcelable(NOTE_ARG) as? Note
        }

        binding.backButton.setOnClickListener {
            if(receivedNote == null) {
                val newNote = Note(
                    binding.titleEditText.text.toString(),
                    binding.contentEditText.text.toString()
                )
                setFragmentResult(NEW_NOTE_RESULT_KEY, bundleOf(NOTE_ARG to newNote))
            } else {
                val updatedNote = receivedNote.copy(
                    title = binding.titleEditText.text.toString(),
                    content = binding.contentEditText.text.toString()
                )
                setFragmentResult(UPDATE_NOTE_RESULT_KEY, bundleOf(NOTE_ARG to updatedNote))
            }
            navigator().toMainMenu()
        }
        binding.deleteButton.setOnClickListener {
            val deleteNote = receivedNote!!.copy()
            setFragmentResult(DELETE_NOTE_RESULT_KEY, bundleOf(NOTE_ARG to deleteNote))
            navigator().toMainMenu()
        }

        if(receivedNote != null) {
            binding.titleEditText.setText(receivedNote.title, TextView.BufferType.EDITABLE)
            binding.contentEditText.setText(receivedNote.content, TextView.BufferType.EDITABLE)
        } else {
            binding.deleteButton.isVisible = false
        }
    }
}