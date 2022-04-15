package com.krearive.quran.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.krearive.quran.databinding.ItemSurahBinding
import com.krearive.quran.network.SurahResponseItem

class SurahListAdapter :
    PagingDataAdapter<SurahResponseItem, SurahListAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemSurahBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    class MyViewHolder(private val binding: ItemSurahBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: SurahResponseItem) {
            binding.tvNumber.text = data.nomor
            binding.tvName.text = data.asma
            binding.tvSurah.text = data.nama
            binding.tvInfo.text = "${data.type} - ${data.ayat} Ayat"
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<SurahResponseItem>() {
            override fun areItemsTheSame(oldItem: SurahResponseItem, newItem: SurahResponseItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: SurahResponseItem, newItem: SurahResponseItem): Boolean {
                return oldItem.nomor == newItem.nomor
            }
        }
    }
}