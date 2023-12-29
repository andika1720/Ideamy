package com.example.thefinalproject.adapter.adapterhome

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.thefinalproject.adapter.AdapterCategory
import com.example.thefinalproject.databinding.ListCategoryBinding
import com.example.thefinalproject.network.model.course.DataCategory

class AdapterCategoryHome (private val onItemClick: OnClickListener?): RecyclerView.Adapter<AdapterCategoryHome.ViewHolder>() {
    private val showAllItems: Boolean = false
    private val differ= object: DiffUtil.ItemCallback<DataCategory>(){
        override fun areItemsTheSame(oldItem: DataCategory, newItem: DataCategory): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DataCategory, newItem: DataCategory): Boolean {
            return oldItem.id == newItem.id
        }

    }

    interface OnClickListener {
        fun itemClick(data: DataCategory)
    }
    private val dif = AsyncListDiffer(this,differ)

    fun sendCategory(value: List<DataCategory>) = dif.submitList(value)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterCategoryHome.ViewHolder {
        val view = LayoutInflater.from(parent.context)
        return ViewHolder(ListCategoryBinding.inflate(view,parent,false))
    }

    override fun onBindViewHolder(holder: AdapterCategoryHome.ViewHolder, position: Int) {
        val data = dif.currentList[position]
        data.let { holder.bind(data) }

        holder.itemView.setOnClickListener{
            onItemClick?.itemClick(data)
        }
    }

    override fun getItemCount(): Int {
        if (showAllItems || dif.currentList.size <= 4) {
            return dif.currentList.size
        } else {
            // Jika showAllItems adalah false dan jumlah item lebih dari 4, kembalikan 4
            return 4
        }
    }

    inner class ViewHolder(private var binding: ListCategoryBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data: DataCategory){
            binding.apply {
                Glide.with(this.iv1)
                    .load(data.image)
                    .fitCenter()
                    .into(iv1)
                tvCategory.text = data.category
            }
        }
    }
}