package com.example.thefinalproject.adapter.DataAdapterDetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.thefinalproject.R
import com.example.thefinalproject.network.model.course.ChapterById
import com.example.thefinalproject.network.model.course.ModuleById

class CourseDetailAdapter(
    private val chapters: List<ChapterById>,
    private val modules: List<ModuleById>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_ITEM = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_HEADER -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.itemmateri_titlechapter, parent, false)
                HeaderViewHolder(view)
            }
            VIEW_TYPE_ITEM -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.itemmateri_contentchapter, parent, false)
                ModuleViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            VIEW_TYPE_HEADER -> {
                val headerHolder = holder as HeaderViewHolder
                val chapter = chapters[position / 2] // Divide by 2 to account for header every two items
                headerHolder.bind(chapter)
            }
            VIEW_TYPE_ITEM -> {
                val moduleHolder = holder as ModuleViewHolder
                val module = modules[position / 2] // Divide by 2 to account for header every two items
                moduleHolder.bind(module)
            }
        }
    }

    override fun getItemCount(): Int {
        return chapters.size + modules.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position % 2 == 0) {
            VIEW_TYPE_HEADER
        } else {
            VIEW_TYPE_ITEM
        }
    }

    class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(chapter: ChapterById) {
            // Bind header data to the view
            itemView.findViewById<TextView>(R.id.tv_noChapter).text =
                chapter.chapterNumber.toString()
            itemView.findViewById<TextView>(R.id.tv_namaChapter).text = chapter.title
        }
    }

    class ModuleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(module: ModuleById) {
            // Bind module data to the view
            itemView.findViewById<TextView>(R.id.tv_namaContentChapter).text = module.title
            // Handle video click or other actions if needed
        }
    }
}
