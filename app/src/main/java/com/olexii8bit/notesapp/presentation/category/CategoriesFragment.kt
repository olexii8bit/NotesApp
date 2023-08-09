package com.olexii8bit.notesapp.presentation.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.olexii8bit.notesapp.data.repository.model.Category
import com.olexii8bit.notesapp.databinding.FragmentCategoriesBinding
import com.olexii8bit.notesapp.presentation.navigator

class CategoriesFragment : Fragment() {

    private val model by lazy { ViewModelProvider(requireActivity())[CategoriesViewModel::class.java] }
    private lateinit var binding: FragmentCategoriesBinding

    companion object {
        const val FIND_ALL_BY_CATEGORY_RESULT_KEY = "FIND_ALL_BY_CATEGORY_KEY"
        const val FIND_ALL_RESULT_KEY = "FIND_ALL_KEY"
        const val CATEGORY_ARG = "ARG_NOTE"
        fun newInstance() = CategoriesFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val onNewCategory: (Category) -> Unit = { model.addCategory(it) }
        val onUpdateCategory: (Category) -> Unit = { model.updateCategory(it) }
        val onDeleteCategory: (Category) -> Unit = { model.deleteCategory(it) }

        val onItemClick: (Category) -> Unit = { category: Category ->
            setFragmentResult(FIND_ALL_BY_CATEGORY_RESULT_KEY , bundleOf(CATEGORY_ARG to category))
        }
        val onItemLongClick: (Category) -> Unit = { category: Category ->
            navigator().showCategoryEditDialog(
                category,
                viewLifecycleOwner,
                onUpdateCategory,
                onDeleteCategory
            )
        }

        val adapter = CategoryRecyclerAdapter(onItemLongClick, onItemClick)
        binding.categoriesRecycler.adapter = adapter
        binding.categoriesRecycler.layoutManager =
            object : LinearLayoutManager(
                this.requireContext(),
                HORIZONTAL,
                false
            ) {
                override fun canScrollHorizontally() = false
            }


        binding.addCategoryButton.setOnClickListener {
            navigator().showNewCategoryDialog(viewLifecycleOwner, onNewCategory)
        }

        binding.findAllNotesTextView.setOnClickListener {
            setFragmentResult(FIND_ALL_RESULT_KEY, bundleOf())
        }

        model.categories.observe(viewLifecycleOwner) { items: List<Category> ->
            adapter.set(items)
        }
    }

}