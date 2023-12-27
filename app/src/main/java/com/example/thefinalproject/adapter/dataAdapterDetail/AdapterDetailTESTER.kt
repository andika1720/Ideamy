package com.example.thefinalproject.adapter.dataAdapterDetail

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.thefinalproject.databinding.ItemmateriContentchapterBinding
import com.example.thefinalproject.databinding.ItemmateriTitlechapterBinding
import com.example.thefinalproject.network.model.chapters.DataChapters1
import com.example.thefinalproject.network.model.modules.DataModules1

class AdapterDetailTESTER (private val data: List<Any>,private var clickListener: ((String) -> Unit)? = null): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
            return when (data[position]) {
                is DataChapters1 -> ITEM_CHAPTER
                is DataModules1 -> ITEM_MODULE
                else -> {
                    Log.e("AdapterDetail", "Undefined view type at position $position: ${data[position].javaClass.simpleName}")
                    throw IllegalArgumentException("Undefined view type")
                }
            }

    }

    class ChapterViewHolder(private val binding: ItemmateriTitlechapterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: DataChapters1) {
            binding.tvNoChapter.text = "Chapter - ${data.chapterNumber}"
            binding.tvNamaChapter.text = data.title
            binding.tvDurasi.text = "${data.duration} Menit"

        }

    }
    class ModuleViewHolder(private val binding: ItemmateriContentchapterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: DataModules1) {
            binding.tvNamaContentChapter.text = data.title

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_CHAPTER -> {
                val binding =
                    ItemmateriTitlechapterBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                ChapterViewHolder(binding)
            }

            ITEM_MODULE -> {
                val binding =
                    ItemmateriContentchapterBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                ModuleViewHolder(binding)
            }


            else -> {
                Log.e("AdapterDetail", "Undefined view type at onCreateViewHolder: $viewType")
                throw throw IllegalArgumentException("Undefined view type")
            }
        }
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            ITEM_CHAPTER -> {
                val headerHolder = holder as ChapterViewHolder
                headerHolder.onBind(data[position] as DataChapters1)
            }

            ITEM_MODULE -> {
                val moduleHolder = holder as ModuleViewHolder
                val itemClickListener = (data[position] as DataModules1)
                moduleHolder.onBind(itemClickListener)

                holder.itemView.setOnClickListener {
                    if (itemClickListener.video != null) {
                        clickListener?.invoke(itemClickListener.video)
                    } else {
                        Log.e("AdapterDetail", "Video is null at position: $position")
                    }
                }
            }

            else -> {
                Log.e("AdapterDetail", "Undefined view type at onBindViewHolder: ${holder.itemViewType}")
                throw IllegalArgumentException("Undefined view type")
            }
        }
    }


    companion object {
        private const val ITEM_CHAPTER = 0
        private const val ITEM_MODULE = 1
    }
}
