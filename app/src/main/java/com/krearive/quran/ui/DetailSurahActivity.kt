package com.krearive.quran.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.krearive.quran.R
import com.krearive.quran.adapter.AyahListAdapter
import com.krearive.quran.adapter.LoadingStateAdapter
import com.krearive.quran.adapter.SurahListAdapter
import com.krearive.quran.databinding.ActivityDetailSurahBinding
import com.krearive.quran.databinding.ActivityMainBinding
import com.krearive.quran.network.SurahResponseItem

class DetailSurahActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailSurahBinding
    private val surahViewModel: SurahViewModel by viewModels {
        ViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailSurahBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val surah = intent.getParcelableExtra<SurahResponseItem>("surah")

        binding.rvAyah.layoutManager = LinearLayoutManager(this)

        val adapter = AyahListAdapter()
        binding.rvAyah.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )

        surah?.let { data ->
            surahViewModel.setIndex(data.index)
            binding.tvName.text = data.latinName
            binding.tvMeaning.text = data.meaning
            binding.tvInfo.text = resources.getString(R.string.surah_info, data.type, data.totalAyah)
        }

        surahViewModel.ayah.observe(this) {
            adapter.submitData(lifecycle, it)
        }
    }

}