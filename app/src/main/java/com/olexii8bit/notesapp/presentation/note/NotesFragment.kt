package com.olexii8bit.notesapp.presentation.note

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.olexii8bit.notesapp.data.repository.model.Note
import com.olexii8bit.notesapp.databinding.FragmentNotesBinding
import com.olexii8bit.notesapp.presentation.editNote.EditNoteFragment
import com.olexii8bit.notesapp.presentation.navigator

class NotesFragment : Fragment() {

    private val model: NotesViewModel by viewModels()
    private lateinit var binding: FragmentNotesBinding

    companion object {
        fun newInstance() = NotesFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val onNoteClickListener = { note: Note ->
            //model.getNoteWithCategory(note)
            navigator().showEditNoteFragment(note)
        }
//        model.noteDetails.observe(viewLifecycleOwner) {
//            navigator().showEditNoteFragment(it)
//        }

        val adapter = NoteRecyclerAdapter(onNoteClickListener)
        binding.notesRecycler.adapter = adapter
        binding.notesRecycler.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL).also {
                it.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
            }
        binding.notesRecycler.itemAnimator = null

        model.notes.observe(viewLifecycleOwner) {
            adapter.set(it)
            Log.d("ddd", "Observed notes")
            it.forEach {
                Log.d("ddd", it.toString())
            }
        }

        parentFragmentManager.setFragmentResultListener(
            EditNoteFragment.NEW_NOTE_RESULT_KEY,
            viewLifecycleOwner
        )
        { _: String, bundle: Bundle ->
            @Suppress("DEPRECATION")
            val newNote: Note = (bundle.getParcelable(EditNoteFragment.NOTE_ARG) as? Note)!!
            model.addNote(newNote)
        }

        parentFragmentManager.setFragmentResultListener(
            EditNoteFragment.UPDATE_NOTE_RESULT_KEY,
            viewLifecycleOwner
        )
        { _: String, bundle: Bundle ->
            @Suppress("DEPRECATION")
            val updatedNote: Note = (bundle.getParcelable(EditNoteFragment.NOTE_ARG) as? Note)!!
            model.updateNote(updatedNote)
        }

        parentFragmentManager.setFragmentResultListener(
            EditNoteFragment.DELETE_NOTE_RESULT_KEY,
            viewLifecycleOwner
        )
        { _: String, bundle: Bundle ->
            @Suppress("DEPRECATION")
            val deleteNote: Note = (bundle.getParcelable(EditNoteFragment.NOTE_ARG) as? Note)!!
            model.deleteNote(deleteNote)
        }

        binding.addNoteButton.setOnClickListener {
            navigator().showEditNoteFragment(null)
        }
    }

}