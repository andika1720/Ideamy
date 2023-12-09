package com.example.thefinalproject.adapter.foritemhomepage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.thefinalproject.databinding.ItemFreePremiumClassBinding
import com.example.thefinalproject.network.model.course.DataCategory

class AdapterCourse(): RecyclerView.Adapter<AdapterCourse.ViewHolder>() {

    private val differ = object : DiffUtil.ItemCallback<DataCategory>() {
        override fun areItemsTheSame(oldItem: DataCategory, newItem: DataCategory): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DataCategory, newItem: DataCategory): Boolean {
            return oldItem.id == newItem.id
        }
    }

    private val dif = AsyncListDiffer(this, differ)

    fun sendList(value: List<DataCategory>) = dif.submitList(value)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterCourse.ViewHolder {
        val view = LayoutInflater.from(parent.context)
        return ViewHolder(ItemFreePremiumClassBinding.inflate(view,parent,false))
    }

    override fun onBindViewHolder(holder: AdapterCourse.ViewHolder, position: Int) {
        val data = dif.currentList[position]
        data.let { holder.bind(data) }
    }

    override fun getItemCount(): Int {
        return dif.currentList.size
    }

    inner class ViewHolder(private var binding: ItemFreePremiumClassBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(data: DataCategory) {
            binding.apply {
                Glide.with(this.ivFreeVid)
                    .load(data.image)
                    .fitCenter()
                    .into(ivFreeVid)

                tvCourse.text = data.category
                tvJudul.text = data.title
                tvCreator.text = "by ${data.creator}"
                tvFreeLvlCourse.text = data.level
                tvFreeModuls.text = "${data.totalModule} Modul"
                tvFreeDurasi.text = "${data.totalDuration} Menit"

            }
        }

    }
}