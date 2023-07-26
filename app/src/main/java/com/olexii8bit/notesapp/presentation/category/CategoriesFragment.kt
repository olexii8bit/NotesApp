package com.olexii8bit.notesapp.presentation.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
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

        val adapter = CategoryRecyclerAdapter()
        binding.categoriesRecycler.layoutManager =
            LinearLayoutManager(this.requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.categoriesRecycler.adapter = adapter

        model.categories.observe(viewLifecycleOwner) {
            adapter.set(it)
        }
//
//        adapter.set(
//            listOf(
//                Category("1"),
//                Category("12"),
//                Category("123"),
//                Category("4"),
//                Category("5"),
//                Category("56"),
//                Category("567"),
//                Category("5678"),
//                Category("9"))
//        )

        binding.addCategoryButton.setOnClickListener {
            navigator().showCategoryEditDialog(null)
        }
    }

}