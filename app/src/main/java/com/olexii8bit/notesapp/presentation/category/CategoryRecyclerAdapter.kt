package com.olexii8bit.notesapp.presentation.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.olexii8bit.notesapp.data.repository.model.Category
import com.olexii8bit.notesapp.databinding.CategoryItemBinding

class CategoryRecyclerAdapter(
    private val onItemLongClickListener: (Category) -> Unit = { },
) : Adapter<CategoryRecyclerAdapter.ViewHolder>() {

    private val items: MutableList<Category> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CategoryRecyclerAdapter.ViewHolder {
        val binding =
            CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryRecyclerAdapter.ViewHolder, position: Int) {
        if (items.isNotEmpty()) {
            holder.bind(items[position])
        }
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(private val binding: CategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Category) {
            binding.root.setOnLongClickListener {
                onItemLongClickListener(item)
                true
            }
            binding.nameTextView.text = item.name
        }
    }

    fun set(elements: List<Category>) {
        val currentSize = this.items.size
        this.items.clear()
        this.items.addAll(elements)
        this.notifyItemRangeRemoved(0, currentSize)
        this.notifyItemRangeInserted(0, elements.size)
    }
}