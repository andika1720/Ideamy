package com.example.thefinalproject.adapter.DataAdapterDetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.thefinalproject.databinding.ItemmateriContentchapterBinding
import com.example.thefinalproject.databinding.ItemmateriTitlechapterBinding
import com.example.thefinalproject.network.model.course.ChapterById
import com.example.thefinalproject.network.model.course.ModuleById

class AdapterDetail(private val data: List<Any>,private var listener: ((String) -> Unit)? = null): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return when (data[position]) {
            is ChapterById -> ITEM_CHAPTER
            is ModuleById -> ITEM_MODULE
            else -> throw IllegalArgumentException("Undefined view type")
        }
    }

    class ChapterViewHolder(private val binding: ItemmateriTitlechapterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: ChapterById) {
            binding.tvNamaChapter.text = data.title

        }
    }
    class ModuleViewHolder(private val binding: ItemmateriContentchapterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: ModuleById) {
            binding.tvNamaContentChapter.text = data.title

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_CHAPTER -> {
                val binding =
                    ItemmateriTitlechapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ChapterViewHolder(binding)
            }

            ITEM_MODULE -> {
                val binding =
                    ItemmateriContentchapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ModuleViewHolder(binding)
            }

            else -> throw throw IllegalArgumentException("Undefined view type")
        }
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            ITEM_CHAPTER -> {
                val headerHolder = holder as ChapterViewHolder
                headerHolder.onBind(data[position] as ChapterById)
            }

            ITEM_MODULE -> {
                val materialHolder = holder as ModuleViewHolder
                val listenerItem = (data[position] as ModuleById)
                materialHolder.onBind(listenerItem)

                holder.itemView.setOnClickListener {
                    listener?.invoke(listenerItem.video!!)
                }
            }

            else -> throw IllegalArgumentException("Undefined view type")
        }
    }

    companion object {
        private const val ITEM_CHAPTER = 0
        private const val ITEM_MODULE = 1
    }
}