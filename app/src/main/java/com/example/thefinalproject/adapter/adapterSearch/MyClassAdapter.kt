package com.example.thefinalproject.adapter.adapterSearch

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.thefinalproject.databinding.ListKelasBerjalanBinding
import com.example.thefinalproject.network.model.mycourse.Course

class MyClassAdapter(private val onButtonClick: ClassClick) :
    RecyclerView.Adapter<MyClassAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
        return ViewHolder(ListKelasBerjalanBinding.inflate(view,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val course = dif.currentList[position]
        holder.bind(course)

    }

    override fun getItemCount(): Int {
        return dif.currentList.size
    }

    fun submitList(newCourses: List<Course?>) {
        dif.submitList(newCourses)
    }

    inner class ViewHolder(private var binding: ListKelasBerjalanBinding):RecyclerView.ViewHolder(binding.root){

        init {
            binding.btnPlay.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val data = dif.currentList[position]
                    onButtonClick.onCourseItemClick(data)
                }
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(course: Course) {
            course.let { course1 ->
                binding.apply {
                    Glide.with(this.ivImageDefault)
                        .load(course1.image)
                        .fitCenter()
                        .into(ivImageDefault)
                    tvRating.text = course1.rating.toString()
                    tvNamaKategory.text = course1.category
                    tvTitleCourse.text = course1.title
                    tvPublisher.text = course1.creator
                    tvLevel.text = "${course1.level} Level"
                }
            }
        }
    }

    private val differ = object : DiffUtil.ItemCallback<Course>() {
        override fun areItemsTheSame(oldItem: Course, newItem: Course): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Course, newItem: Course): Boolean {
            return oldItem.id == newItem.id
        }

    }
    interface ClassClick {
        fun onCourseItemClick(data: Course)
    }

    private val dif = AsyncListDiffer(this, differ)
}