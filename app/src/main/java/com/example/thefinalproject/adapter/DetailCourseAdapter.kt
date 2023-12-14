package com.example.thefinalproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.thefinalproject.R
import com.example.thefinalproject.databinding.ItemFreePremiumClassBinding
import com.example.thefinalproject.network.model.course.DataCategory

class DetailCourseAdapter: RecyclerView.Adapter<DetailCourseAdapter.ViewHolder>() {

    private val differ = object : DiffUtil.ItemCallback<DataCategory>() {
        override fun areItemsTheSame(oldItem: DataCategory, newItem: DataCategory): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DataCategory, newItem: DataCategory): Boolean {
            return oldItem.id == newItem.id
        }
    }

    private val dif = AsyncListDiffer(this, differ)

    fun sendList(value: List<DataCategory>) {
        dif.submitList(value)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFreePremiumClassBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dif.currentList[position]
        data.let { holder.bind(data) }
    }

    override fun getItemCount(): Int {
        return dif.currentList.size
    }


    // Method to set CourseItemClickListener

    inner class ViewHolder(private var binding: ItemFreePremiumClassBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: DataCategory) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(data.image)
                    .fitCenter()
                    .into(ivFreeVid)

                tvCourse.text = data.category
                tvJudul.text = data.title
                tvCreator.text = "by ${data.creator}"
                tvFreeLvlCourse.text = data.level
                tvFreeModuls.text = "${data.totalModule} Modul"
                tvFreeDurasi.text = "${data.totalDuration} Menit"
                if (data.type == "premium") {
                    val premiumDrawable = ContextCompat.getDrawable(itemView.context, R.drawable.diamond)
                    premiumDrawable?.setBounds(
                        0,
                        0,
                        premiumDrawable.intrinsicWidth / 2,
                        premiumDrawable.intrinsicHeight / 2
                    )
                    btnMulaiKelas.text = "Premium"
                    btnMulaiKelas.setCompoundDrawablesWithIntrinsicBounds(premiumDrawable, null, null, null)
                } else {
                    // Hapus gambar drawable start jika tidak "premium"
                    btnMulaiKelas.text = "Mulai Kelas"
                    btnMulaiKelas.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
                }
            }
        }
    }
}