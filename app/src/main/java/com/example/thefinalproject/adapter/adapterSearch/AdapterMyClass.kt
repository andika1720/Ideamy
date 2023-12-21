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
import com.example.thefinalproject.network.model.mycourse.DataMyCourse

class AdapterMyClass(private val onItemClick: OnClickListener?): RecyclerView.Adapter<AdapterMyClass.ViewHolder>() {

    private val differ= object: DiffUtil.ItemCallback<DataMyCourse>(){
        override fun areItemsTheSame(oldItem: DataMyCourse, newItem: DataMyCourse): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DataMyCourse, newItem: DataMyCourse): Boolean {
            return oldItem.id == newItem.id
        }

    }

    interface OnClickListener {
        fun itemClick(data: DataMyCourse)
    }
    private val dif = AsyncListDiffer(this,differ)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
    : AdapterMyClass.ViewHolder {
        val view = LayoutInflater.from(parent.context)
        return ViewHolder(ListKelasBerjalanBinding.inflate(view,parent,false))
    }

    fun sendItem(data: DataMyCourse?) {
        dif.submitList(data?.let { listOf(it) } ?: emptyList())
    }
    override fun onBindViewHolder(holder: AdapterMyClass.ViewHolder, position: Int) {
        val data = dif.currentList[position]
        data.let { holder.bind(data) }

        holder.itemView.setOnClickListener{
            onItemClick?.itemClick(data)
        }


    }

    override fun getItemCount(): Int {
        return dif.currentList.size
    }

    inner class ViewHolder(private var binding: ListKelasBerjalanBinding):RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bind(data: DataMyCourse) {
            val courses: List<Course?>? = data.courses
            val firstCourse: Course? = courses?.firstOrNull()

            firstCourse?.let { course ->
                binding.apply {
                    Glide.with(this.ivImageDefault)
                        .load(course.image)
                        .fitCenter()
                        .into(ivImageDefault)
                    tvRating.text = firstCourse.rating.toString()
                    tvNamaKategory.text = firstCourse.category
                    tvTitleCourse.text = firstCourse.title
                    tvPublisher.text = firstCourse.creator
                    tvLevel.text = "${firstCourse.level} Level"
                }
            }
        }
    }
}