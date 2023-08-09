package com.olexii8bit.notesapp.presentation.note

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.olexii8bit.notesapp.data.repository.model.Category
import com.olexii8bit.notesapp.data.repository.model.Note
import com.olexii8bit.notesapp.databinding.FragmentNotesBinding
import com.olexii8bit.notesapp.presentation.category.CategoriesFragment
import com.olexii8bit.notesapp.presentation.navigator

class NotesFragment : Fragment() {

    private val model by lazy { ViewModelProvider(requireActivity())[NotesViewModel::class.java] }
    private lateinit var binding: FragmentNotesBinding

    companion object { fun newInstance() = NotesFragment() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val onNoteClickListener: (Note) -> Unit = {
            navigator().showEditNoteFragment(it)
        }

        binding.addNoteButton.setOnClickListener {
            navigator().showEditNoteFragment(null)
        }

        val adapter = NoteRecyclerAdapter(onNoteClickListener)
        binding.notesRecycler.adapter = adapter
        binding.notesRecycler.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL).also {
                it.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
            }
        binding.notesRecycler.itemAnimator = null

        val notesObserver = Observer<List<Note>> {
            adapter.set(it)
            Log.d("NOTE_COMMUNICATION", "Observed notes")
            it.forEach {
                Log.d("NOTE_COMMUNICATION", it.toString())
            }
        }

        if (!model.hasSelectedCategory) model.notes.observe(viewLifecycleOwner, notesObserver)
        else model.notesByCategory.observe(viewLifecycleOwner, notesObserver)

        parentFragmentManager.setFragmentResultListener(
            CategoriesFragment.FIND_ALL_RESULT_KEY,
            viewLifecycleOwner
        ) { _: String, _: Bundle ->
            model.setCategory(null)
            model.notesByCategory.removeObserver(notesObserver)
            model.notes.observe(viewLifecycleOwner, notesObserver)
            Log.d("NOTE_COMMUNICATION", "FIND_ALL")
        }

        parentFragmentManager.setFragmentResultListener(
            CategoriesFragment.FIND_ALL_BY_CATEGORY_RESULT_KEY,
            viewLifecycleOwner
        )
        { _: String, bundle: Bundle ->
            @Suppress("DEPRECATION")
            val category: Category =
                (bundle.getParcelable(CategoriesFragment.CATEGORY_ARG) as? Category)!!
            model.setCategory(category)
            model.notes.removeObserver(notesObserver)
            model.notesByCategory.observe(viewLifecycleOwner, notesObserver)
            Log.d("NOTE_COMMUNICATION", "FIND_ALL_BY_CATEGORY - $category")
        }

    }

}