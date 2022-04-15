package com.krearive.quran.ui

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.krearive.quran.data.QuranRepository
import com.krearive.quran.di.Injection
import com.krearive.quran.network.SurahResponseItem

class MainViewModel(quranRepository: QuranRepository) : ViewModel() {

    val surah: LiveData<PagingData<SurahResponseItem>> =
        quranRepository.getSurah().cachedIn(viewModelScope)
}

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(Injection.provideRepository(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}