package com.krearive.quran.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.krearive.quran.adapter.LoadingStateAdapter
import com.krearive.quran.adapter.SurahListAdapter
import com.krearive.quran.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val surahViewModel: SurahViewModel by viewModels {
        ViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.rvSurah.layoutManager = LinearLayoutManager(this)

        getData()
    }

    private fun getData() {
        val adapter = SurahListAdapter()
        binding.rvSurah.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )
        surahViewModel.surah.observe(this) {
            adapter.submitData(lifecycle, it)
        }
    }
}