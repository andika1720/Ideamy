package com.example.thefinalproject.adapter.DataAdapterDetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.thefinalproject.databinding.ItemmateriContentchapterBinding
import com.example.thefinalproject.databinding.ItemmateriTitlechapterBinding
import com.example.thefinalproject.network.model.course.ChapterById
import com.example.thefinalproject.network.model.course.ModuleById

class CourseMaterialAdapter(private val data: List<Any>, private var listener: ((String) -> Unit)? = null)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val ITEM_HEADER = 0
        private const val ITEM_MATERIAL = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (data[position]) {
            is ChapterById -> ITEM_HEADER
            is ModuleById -> ITEM_MATERIAL
            else -> throw IllegalArgumentException("Undefined view type")
        }
    }

    class MaterialViewHolder(private val binding: ItemmateriContentchapterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: ModuleById) {
            binding.tvNamaContentChapter.text = data.title
        }
    }

    class HeaderViewHolder(private val binding: ItemmateriTitlechapterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: ChapterById) {
            binding.tvNamaChapter.text = data.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_HEADER -> {
                val binding =
                    ItemmateriTitlechapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                HeaderViewHolder(binding)
            }

            ITEM_MATERIAL -> {
                val binding =
                    ItemmateriContentchapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                MaterialViewHolder(binding)
            }

            else -> throw IllegalArgumentException("Undefined view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            ITEM_HEADER -> {
                val headerHolder = holder as HeaderViewHolder
                headerHolder.onBind(data[position] as ChapterById)
            }

            ITEM_MATERIAL -> {
                val materialHolder = holder as MaterialViewHolder
                val listenerItem = data[position] as ModuleById
                materialHolder.onBind(listenerItem)

                holder.itemView.setOnClickListener {
                    listener?.invoke(listenerItem.video ?: "")
                }
            }

            else -> throw IllegalArgumentException("Undefined view type")
        }
    }

    override fun getItemCount(): Int = data.size
}