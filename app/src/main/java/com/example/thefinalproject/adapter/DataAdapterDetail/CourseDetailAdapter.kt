package com.example.thefinalproject.adapter.DataAdapterDetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.thefinalproject.R
import com.example.thefinalproject.util.CourseDetailItem

class CourseDetailAdapter  : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = mutableListOf<CourseDetailItem>()

    fun setItems(newItems: List<CourseDetailItem>) {

        items.addAll(newItems)
        items.clear()
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is CourseDetailItem.ChapterItem -> VIEW_TYPE_CHAPTER
            is CourseDetailItem.ModuleItem -> VIEW_TYPE_MODULE
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_CHAPTER -> ChapterViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.itemmateri_titlechapter, parent, false)
            )
            VIEW_TYPE_MODULE -> ModuleViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.itemmateri_contentchapter, parent, false)
            )
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ChapterViewHolder -> holder.bind((items[position] as CourseDetailItem.ChapterItem).chapter)
            is ModuleViewHolder -> holder.bind((items[position] as CourseDetailItem.ModuleItem).module)
        }
    }

    companion object {
        private const val VIEW_TYPE_CHAPTER = 1
        private const val VIEW_TYPE_MODULE = 2
    }
}