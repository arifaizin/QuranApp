package com.krearive.quran.ui

import android.content.Context
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.krearive.quran.data.QuranRepository
import com.krearive.quran.database.LastRead
import com.krearive.quran.database.LastReadWithSurah
import com.krearive.quran.di.Injection
import com.krearive.quran.network.AyahResponseItem
import com.krearive.quran.network.SurahResponseItem
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SurahViewModel(val quranRepository: QuranRepository) : ViewModel() {

    private val index = MutableLiveData<Int>()
    private val _lastRead = MutableLiveData<Pair<Int, Int>>()
    val lastRead = _lastRead

    private val _lastReads = MutableLiveData<LastReadWithSurah>()
    val lastReads = _lastReads

    fun setIndex(index: Int) {
        this.index.value = index
    }

    val surah: LiveData<PagingData<SurahResponseItem>> =
        quranRepository.getSurah().cachedIn(viewModelScope)

    val ayah: LiveData<PagingData<AyahResponseItem>> = index.switchMap {
        quranRepository.getAyah(it).cachedIn(viewModelScope)
    }

    fun saveLastAyah(lastSurah: Int, lastAyah: Int) {
        viewModelScope.launch {
            quranRepository.saveLastAyah(lastSurah, lastAyah)
        }
    }

    fun requestLastRead() {
        viewModelScope.launch {
            quranRepository.getLastSurah().combine(quranRepository.getLastAyah()) { surah, ayah ->
                Pair(surah, ayah)
            }.collect {
                _lastRead.value = it
            }
        }

//        viewModelScope.launch {
//            quranRepository.getLastRead().switchMap {
//                _lastReads = it
//            }
//        }
        val lastReads = quranRepository.getLastRead()

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
}