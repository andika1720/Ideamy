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

class MyClassAdapter(private val onItemClickListener: (Course) -> Unit) :
    RecyclerView.Adapter<MyClassAdapter.ViewHolder>() {

    private val asyncDiffer: AsyncListDiffer<Course> = AsyncListDiffer(this, CourseDiffCallback())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
        return ViewHolder(ListKelasBerjalanBinding.inflate(view,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val course = asyncDiffer.currentList[position]
        holder.bind(course)
        holder.itemView.setOnClickListener { onItemClickListener.invoke(course) }
    }

    override fun getItemCount(): Int {
        return asyncDiffer.currentList.size
    }

    fun submitList(newCourses: List<Course?>) {
        asyncDiffer.submitList(newCourses)
    }

    inner class ViewHolder(private var binding: ListKelasBerjalanBinding):RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bind(course: Course) {


            course?.let { course ->
                binding.apply {
                    Glide.with(this.ivImageDefault)
                        .load(course.image)
                        .fitCenter()
                        .into(ivImageDefault)
                    tvRating.text = course.rating.toString()
                    tvNamaKategory.text = course.category
                    tvTitleCourse.text = course.title
                    tvPublisher.text = course.creator
                    tvLevel.text = "${course.level} Level"
                }
            }
        }
    }

    private class CourseDiffCallback : DiffUtil.ItemCallback<Course>() {
        override fun areItemsTheSame(oldItem: Course, newItem: Course): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Course, newItem: Course): Boolean {
            return oldItem == newItem
        }
    }
}