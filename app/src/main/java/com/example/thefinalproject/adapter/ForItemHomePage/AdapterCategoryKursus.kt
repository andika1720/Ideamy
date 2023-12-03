package com.example.thefinalproject.adapter.ForItemHomePage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.thefinalproject.databinding.ListTopicBinding
import com.example.thefinalproject.network.model.DataCategory
import com.example.thefinalproject.util.Utils

class AdapterCategoryKursus: RecyclerView.Adapter<AdapterCategoryKursus.ViewHolder>() {

    private val differ= object: DiffUtil.ItemCallback<DataCategory>(){
        override fun areItemsTheSame(oldItem: DataCategory, newItem: DataCategory): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DataCategory, newItem: DataCategory): Boolean {
            return oldItem.id == newItem.id
        }

    }

    private val dif = AsyncListDiffer(this,differ)

    fun sendList(value: List<DataCategory>) = dif.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
        return ViewHolder(ListTopicBinding.inflate(view,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dif.currentList[position]
        data.let { holder.bind(data) }
    }

    override fun getItemCount(): Int {
        return dif.currentList.size
    }

    inner class ViewHolder(private var binding: ListTopicBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data: DataCategory){
            binding.apply {
                Glide.with(this.ivImageDefault)
                    .load(data.image)
                    .fitCenter()
                    .into(ivImageDefault)
                tvNamaKategory.text = data.category
                tvTitleCourse.text = data.title
                tvPublisher.text = data.creator
                tvLevel.text = "${data.level} Level"
                tvModul.text= "${data.totalModule} Modul"
                btnBuy.text = "Beli  ${Utils.formatCurrency(data.price)}"
                timerCourse.text = "${data.totalDuration} Menit"

            }
        }
    }
}