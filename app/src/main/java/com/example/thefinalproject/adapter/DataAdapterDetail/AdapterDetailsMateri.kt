package com.example.thefinalproject.adapter.DataAdapterDetail

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.thefinalproject.databinding.ItemmateriTitlechapterBinding
import com.example.thefinalproject.network.model.chapter.DataChapterById
import com.example.thefinalproject.network.model.course.ChapterById

class AdapterDetailsMateri : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class ChaptersViewHolder(private val binding: ItemmateriTitlechapterBinding):RecyclerView.ViewHolder(binding.root) {
        fun bindBannerView(banner: ChapterById) {
            binding.tvChapter1.text = banner.title
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}