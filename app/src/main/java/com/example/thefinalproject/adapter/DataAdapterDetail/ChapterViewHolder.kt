package com.example.thefinalproject.adapter.DataAdapterDetail

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.thefinalproject.R
import com.example.thefinalproject.network.model.course.ChapterById

class ChapterViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(chapter: ChapterById) {
        itemView.findViewById<TextView>(R.id.tv_namaChapter).text = "Chapter${chapter.chapterNumber} - ${chapter.title}"
        itemView.findViewById<TextView>(R.id.tv_durasi).text = "${chapter.duration} Menit"
    }
}