package com.krearive.quran.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.krearive.quran.R
import com.krearive.quran.databinding.ItemSurahBinding
import com.krearive.quran.network.SurahResponseItem
import com.krearive.quran.ui.DetailSurahActivity

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
            binding.tvIndex.text = data.index.toString()
            binding.tvSurah.text = data.latinName
            binding.tvInfo.text = binding.root.resources.getString(R.string.surah_info, data.type, data.totalAyah)
            binding.tvName.text = data.arabName
            binding.root.setOnClickListener {
                val intent = Intent(it.context, DetailSurahActivity::class.java)
                intent.putExtra("surah", data)
                it.context.startActivity(intent)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<SurahResponseItem>() {
            override fun areItemsTheSame(oldItem: SurahResponseItem, newItem: SurahResponseItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: SurahResponseItem, newItem: SurahResponseItem): Boolean {
                return oldItem.index == newItem.index
            }
        }
    }
}