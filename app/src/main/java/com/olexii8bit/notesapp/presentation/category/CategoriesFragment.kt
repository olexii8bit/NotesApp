package com.olexii8bit.notesapp.presentation.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.olexii8bit.notesapp.R
import com.olexii8bit.notesapp.data.repository.model.Category
import com.olexii8bit.notesapp.databinding.FragmentCategoriesBinding
import com.olexii8bit.notesapp.presentation.navigator

class CategoriesFragment : Fragment() {

    private val model by lazy { ViewModelProvider(requireActivity())[CategoriesViewModel::class.java] }
    private lateinit var binding: FragmentCategoriesBinding

    companion object {
        private const val POPUPMENU_EDIT_ID = 1
        private const val POPUPMENU_DELETE_ID = 2

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
        val onItemLongClick: (Category, View) -> Unit = { category: Category, v: View ->
            showPopupMenu(category, v, onUpdateCategory, onDeleteCategory)
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

    private fun showPopupMenu(
        category: Category,
        view: View,
        onUpdateCategory: (Category) -> Unit,
        onDeleteCategory: (Category) -> Unit
    ) {
        val popupMenu = PopupMenu(requireContext(), view)

        popupMenu.menu.add(0, POPUPMENU_EDIT_ID, Menu.NONE, requireContext().getString(R.string.categoryPopupEdit))
        popupMenu.menu.add(0, POPUPMENU_DELETE_ID, Menu.NONE, requireContext().getString(R.string.categoryPopupDelete))

        popupMenu.setOnMenuItemClickListener {
            if(it.itemId == POPUPMENU_EDIT_ID) {
                navigator().showCategoryEditDialog(
                    category,
                    viewLifecycleOwner,
                    onUpdateCategory
                )
            }
            if(it.itemId == POPUPMENU_DELETE_ID) {
                onDeleteCategory.invoke(category)
            }
            return@setOnMenuItemClickListener true
        }
        popupMenu.show()
    }

}