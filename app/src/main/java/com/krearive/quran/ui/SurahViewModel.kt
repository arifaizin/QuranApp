package com.krearive.quran.ui

import android.content.Context
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.krearive.quran.data.QuranRepository
import com.krearive.quran.di.Injection
import com.krearive.quran.network.AyahResponseItem
import com.krearive.quran.network.SurahResponseItem

class SurahViewModel(quranRepository: QuranRepository) : ViewModel() {

    private val index = MutableLiveData<Int>()

    fun setIndex(index: Int) {
        this.index.value = index
    }

    val surah: LiveData<PagingData<SurahResponseItem>> =
        quranRepository.getSurah().cachedIn(viewModelScope)

    val ayah: LiveData<PagingData<AyahResponseItem>> = index.switchMap {
        quranRepository.getAyah(it).cachedIn(viewModelScope)
    }
}

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SurahViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SurahViewModel(Injection.provideRepository(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}