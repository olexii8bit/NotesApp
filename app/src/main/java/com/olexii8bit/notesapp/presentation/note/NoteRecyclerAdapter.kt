package com.olexii8bit.notesapp.presentation.note

import android.view.LayoutInflater
import android.view.View.GONE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.olexii8bit.notesapp.data.repository.model.Note
import com.olexii8bit.notesapp.databinding.NoteItemBinding
import java.time.format.DateTimeFormatter

class NoteRecyclerAdapter(
    private val onItemClickListener: (Note) -> Unit = {  }
) : RecyclerView.Adapter<NoteRecyclerAdapter.ViewHolder>() {

    private val items: MutableList<Note> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteRecyclerAdapter.ViewHolder {
        val binding = NoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(items.isNotEmpty()) {
            holder.bind(items[position])
        }
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(private val binding: NoteItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Note) {
            binding.root.setOnClickListener { onItemClickListener(item) }

            if (item.title.isNotEmpty()) binding.titleTextView.text = item.title
            else binding.titleTextView.visibility = GONE

            if (item.content.isNotEmpty()) binding.contentTextView.text = item.content
            else binding.contentTextView.visibility = GONE

            binding.createdAtTextView.text = item.createdAt!!.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")).toString()
        }
    }

    fun set(elements: List<Note>) {
        this.items.clear()
        this.items.addAll(elements)
        this.notifyItemRangeChanged(0, elements.size)
    }
}