package com.krearive.quran.ui

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.krearive.quran.adapter.LoadingStateAdapter
import com.krearive.quran.adapter.SurahListAdapter
import com.krearive.quran.databinding.ActivityMainBinding
import com.krearive.quran.network.SurahResponseItem

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val surahViewModel: SurahViewModel by viewModels {
        SurahViewModel.ViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.rvSurah.layoutManager = LinearLayoutManager(this)
        binding.cvLastRead.setOnClickListener {
            surahViewModel.requestLastRead()
        }
        surahViewModel.lastRead.observe(this) { lastRead ->
            val (surah, ayah) = lastRead
            val data = SurahResponseItem(index = surah)
            val intent = Intent(this, DetailSurahActivity::class.java)
            intent.putExtra("surah", data)
            intent.putExtra("ayah", ayah)
            startActivity(intent)
        }

//        surahViewModel.lastReads.observe(this) { lastRead ->
//            val intent = Intent(this, DetailSurahActivity::class.java)
//            intent.putExtra("surah", lastRead.surah)
//            intent.putExtra("ayah", lastRead.lastRead.ayahPosition)
//            startActivity(intent)
//        }
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