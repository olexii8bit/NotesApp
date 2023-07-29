package com.olexii8bit.notesapp.presentation.category

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.olexii8bit.notesapp.data.repository.model.Category
import com.olexii8bit.notesapp.databinding.FragmentCategoriesBinding
import com.olexii8bit.notesapp.presentation.navigator

class CategoriesFragment : Fragment() {

    private val model: CategoriesViewModel by viewModels()
    private lateinit var binding: FragmentCategoriesBinding

    companion object {
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

        val adapter = CategoryRecyclerAdapter { category: Category ->
            navigator().showCategoryEditDialog(
                category,
                viewLifecycleOwner,
                onUpdateCategory,
                onDeleteCategory
            )
        }

        binding.addCategoryButton.setOnClickListener {
            navigator().showCategoryEditDialog(null, viewLifecycleOwner, onNewCategory)
        }

        binding.categoriesRecycler.layoutManager =
            LinearLayoutManager(this.requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.categoriesRecycler.adapter = adapter

        model.categories.observe(viewLifecycleOwner) {
            adapter.set(it)
            Log.d("ddd", "Observed categories")
            it.forEach { element: Category ->
                Log.d("ddd", element.toString())
            }
        }
    }

}