package com.example.thefinalproject.adapter.DataAdapterDetail

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.thefinalproject.R
import com.example.thefinalproject.network.model.course.ModuleById

class ModuleViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(module: ModuleById) {
        itemView.findViewById<TextView>(R.id.tv_namaContentChapter).text = module.title
        // Handle ImageViewPlay or ImageViewLock based on your premium logic
    }
}