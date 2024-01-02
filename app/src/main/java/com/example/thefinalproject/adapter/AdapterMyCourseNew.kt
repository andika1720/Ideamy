package com.example.thefinalproject.adapter

import android.annotation.SuppressLint
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

class AdapterMyCourseNew (private val onButtonClick: CourseClick): RecyclerView.Adapter<AdapterMyCourseNew.ViewHolder>() {
    private var dataset: List<DataCategory> = emptyList()

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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
        return ViewHolder(ItemFreePremiumClassBinding.inflate(view, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dif.currentList[position]
        data.let { holder.bind(data) }
    }

    override fun getItemCount(): Int {
        return dif.currentList.size
    }


    interface CourseClick {
        fun onCourseItemClick(data: DataCategory)
    }

    inner class ViewHolder(private var binding: ItemFreePremiumClassBinding) :
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
        fun bind(data: DataCategory) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(data.image)
                    .fitCenter()
                    .into(ivFreeVid)

                tvCourse.text = data.category
                tvJudul.text = data.title
                tvRate.text = data.rating.toString()
                tvCreator.text = "by ${data.creator}"
                tvFreeLvlCourse.text = data.level
                tvFreeModuls.text = "${data.totalModule} Modul"
                tvFreeDurasi.text = "${data.totalDuration} Menit"
                if (data.statusPayment) {
                    // Jika status pembayaran true, mulai kelas
                    btnMulaiKelas.text = "Mulai Kelas"
                    val playcircle = ContextCompat.getDrawable(itemView.context, R.drawable.playcircle)
                    playcircle?.setBounds(
                        0,
                        0,
                        playcircle.intrinsicWidth / 2,
                        playcircle.intrinsicHeight / 2
                    )
                    btnMulaiKelas.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.navyblue))
                    btnMulaiKelas.setCompoundDrawablesWithIntrinsicBounds(playcircle, null, null, null)
                } else {
                    // Jika status pembayaran false, sesuaikan dengan tipe data
                    if (data.type == "premium") {
                        // Jika tipe data premium, tampilkan teks dan gambar premium
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
                        // Jika tipe data free, tampilkan teks "Free" dan hapus gambar
                        btnMulaiKelas.text = "Free"
                        btnMulaiKelas.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
                    }
                }
            }
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    fun clearAndAddData(data: List<DataCategory>) {
        dif.submitList(data)
        dataset = data
        notifyDataSetChanged()
    }
}