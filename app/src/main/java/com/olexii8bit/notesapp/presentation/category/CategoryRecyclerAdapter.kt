package com.olexii8bit.notesapp.presentation.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.olexii8bit.notesapp.data.repository.model.Category
import com.olexii8bit.notesapp.databinding.CategoryItemBinding

class CategoryRecyclerAdapter(
    private val items: MutableList<Category> = mutableListOf()
) : Adapter<CategoryRecyclerAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryRecyclerAdapter.ViewHolder {
        val binding = CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryRecyclerAdapter.ViewHolder, position: Int) {
        if(items.isNotEmpty()) {
            holder.bind(items[position])
        }
    }
    override fun getItemCount(): Int = items.size

    inner class ViewHolder(private val binding: CategoryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Category) {
            binding.nameTextView.text = item.name
        }
    }

    fun set(elements: List<Category>) {
        this.items.clear()
        this.items.addAll(elements)
        this.notifyItemRangeChanged(0, elements.size)
    }
}