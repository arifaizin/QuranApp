package com.krearive.quran.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.krearive.quran.R
import com.krearive.quran.adapter.AyahListAdapter
import com.krearive.quran.adapter.LoadingStateAdapter
import com.krearive.quran.databinding.ActivityDetailSurahBinding
import com.krearive.quran.databinding.ModalBottomSheetContentBinding
import com.krearive.quran.network.AyahResponseItem
import com.krearive.quran.network.SurahResponseItem

class DetailSurahActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailSurahBinding
    private var surah: SurahResponseItem? = null

    private val surahViewModel: SurahViewModel by viewModels {
        ViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailSurahBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        surah = intent.getParcelableExtra("surah")

        binding.rvAyah.layoutManager = LinearLayoutManager(this)

        val adapter = AyahListAdapter {
            showMenu(it)
        }
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
    private fun showMenu(ayah: AyahResponseItem) {
        val modalBottomSheet = ModalBottomSheet(ayah, surah as SurahResponseItem)
        modalBottomSheet.show(supportFragmentManager, ModalBottomSheet.TAG)
    }

}

class ModalBottomSheet(val ayah: AyahResponseItem, val surah: SurahResponseItem) :
    BottomSheetDialogFragment() {

    private var _binding: ModalBottomSheetContentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ModalBottomSheetContentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvShare.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(
                    Intent.EXTRA_TEXT,
                    "${ayah.arabText} \n ${ayah.translation} (${surah.latinName} : ${ayah.index})"
                )
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }
}