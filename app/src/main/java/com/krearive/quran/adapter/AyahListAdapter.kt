package com.krearive.quran.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.krearive.quran.databinding.ItemAyahBinding
import com.krearive.quran.databinding.ItemSurahBinding
import com.krearive.quran.network.AyahResponseItem

class AyahListAdapter :
    PagingDataAdapter<AyahResponseItem, AyahListAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemAyahBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    class MyViewHolder(private val binding: ItemAyahBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: AyahResponseItem) {
            binding.tvIndex.text = data.index.toString()
            binding.tvAyah.text = data.arabText
            binding.tvTranslation.text = data.translation
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<AyahResponseItem>() {
            override fun areItemsTheSame(oldItem: AyahResponseItem, newItem: AyahResponseItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: AyahResponseItem, newItem: AyahResponseItem): Boolean {
                return oldItem.index == newItem.index
            }
        }
    }
}