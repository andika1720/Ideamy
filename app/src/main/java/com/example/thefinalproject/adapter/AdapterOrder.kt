package com.example.thefinalproject.adapter

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.thefinalproject.R
import com.example.thefinalproject.databinding.ItemOrderBinding
import com.example.thefinalproject.network.model.order.DataPost
import com.example.thefinalproject.util.Utils

class AdapterOrder (private val onButtonClick: CourseClick): RecyclerView.Adapter<AdapterOrder.ViewHolder>() {

    private val differ = object : DiffUtil.ItemCallback<DataPost>() {
        override fun areItemsTheSame(oldItem: DataPost, newItem: DataPost): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DataPost, newItem: DataPost): Boolean {
            return oldItem.id == newItem.id
        }

    }

    interface CourseClick {
        fun onCourseItemClick(data: DataPost)
    }

    private val dif = AsyncListDiffer(this, differ)

    fun sendList(value: List<DataPost>) = dif.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
        return ViewHolder(ItemOrderBinding.inflate(view, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dif.currentList[position]
        data.let { holder.bind(data) }
    }

    override fun getItemCount(): Int {
        return dif.currentList.size
    }

    inner class ViewHolder(private var binding: ItemOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btnMulaiKelas.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val data = dif.currentList[position]
                    onButtonClick.onCourseItemClick(data)
                }
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(data: DataPost) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(data.course?.image)
                    .fitCenter()
                    .into(ivFreeVid)

                tvCourse.text = data.course?.category
                tvJudul.text = data.course?.title
                tvFreeLvlCourse.text = data.course?.level

                if (data.status == "PENDING") {
                    btnMulaiKelas.text="Waiting Payment"
                } else {
                    btnMulaiKelas.text="PAID"
                    btnMulaiKelas.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(itemView.context, R.color.succes))
                }
                }
            }
        }
    }
