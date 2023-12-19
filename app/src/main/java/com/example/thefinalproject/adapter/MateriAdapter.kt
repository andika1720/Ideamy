package com.example.thefinalproject.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.thefinalproject.R
import com.example.thefinalproject.databinding.ItemmateriContentchapterBinding
import com.example.thefinalproject.databinding.ItemmateriTitlechapterBinding
import com.example.thefinalproject.network.model.course.ChapterById
import com.example.thefinalproject.network.model.course.ModuleById
import com.example.thefinalproject.ui.fragment.DetailCourse
import com.example.thefinalproject.ui.fragment.itemPage.detail.MateriKelas

class MateriAdapter(
    private val item: List<Any>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val COURSE_VIEW_TYPE = 1
        const val MODULE_VIEW_TYPE = 2
    }

    inner class ItemCourseVH(private val binding: ItemmateriTitlechapterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindCourseItem(courseItem: ChapterById) {
            binding.tvNamaChapter.text = courseItem.title
            binding.tvDurasi.text = courseItem.duration.toString()
        }
    }

    inner class ItemModulVH(private val binding: ItemmateriContentchapterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindModulItem(modulItem: ModuleById) {
            binding.tvNamaContentChapter.text = modulItem.chapterId?: ""
            binding.tvNamaContentChapter.text = modulItem.title
            modulItem.video?.let {
                Glide.with(binding.root.context)
                    .load(it)
                    .into(binding.icPlayContentChapter)
            }
        }

        init {
            binding.icPlayContentChapter.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val modulItem = item[position] as? ModuleById
                    modulItem?.let {
                        openVideoInWebView(it.video)
                    }
                }
            }
        }

        private fun openVideoInWebView(video: String?) {
            video?.let {
                val action = bundleOf("videoUrl" to video)
                binding.root.findNavController().navigate(R.id.action_detailCourse_to_webViewFragment, bundleOf())
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (item[position]) {
            is ChapterById -> COURSE_VIEW_TYPE
            is ModuleById -> MODULE_VIEW_TYPE
            else -> throw IllegalArgumentException("Invalid item type")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            COURSE_VIEW_TYPE -> {
                val view = ItemmateriTitlechapterBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                ItemCourseVH(view)
            }
            MODULE_VIEW_TYPE -> {
                val view = ItemmateriContentchapterBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                ItemModulVH(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemCount(): Int {
        return item.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            COURSE_VIEW_TYPE -> {
                val courseItem = item[position] as? ChapterById
                (holder as? ItemCourseVH)?.bindCourseItem(courseItem!!)
            }
            MODULE_VIEW_TYPE -> {
                val modulItem = item[position] as? ModuleById
                (holder as? ItemModulVH)?.bindModulItem(modulItem!!)
            }
        }
    }
}

