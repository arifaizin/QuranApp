package com.krearive.quran.data

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.krearive.quran.database.LastRead
import com.krearive.quran.database.QuranDatabase
import com.krearive.quran.network.ApiService
import com.krearive.quran.network.AyahResponseItem
import com.krearive.quran.network.SurahResponseItem
import com.krearive.quran.preference.QuranPreference

class QuranRepository(
    private val quranDatabase: QuranDatabase,
    private val apiService: ApiService,
    private val preference: QuranPreference
    ) {
    fun getSurah(): LiveData<PagingData<SurahResponseItem>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 10
            ),
            remoteMediator = SurahRemoteMediator(quranDatabase, apiService),
            pagingSourceFactory = {
//                QuranPagingSource(apiService)
                quranDatabase.quranDao().getAllSurah()
            }
        ).liveData
    }

    fun getAyah(index: Int): LiveData<PagingData<AyahResponseItem>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 10
            ),
            remoteMediator = AyahRemoteMediator(quranDatabase, apiService, index),
            pagingSourceFactory = {
                quranDatabase.quranDao().getAllAyah(index)
            }
        ).liveData
    }

    suspend fun saveLastAyah(lastSurah: Int, lastAyah: Int) {
//        quranDatabase.quranDao().saveLastRead(LastRead(lastSurah, lastAyah))
        preference.saveLastAyah(surahIndex = lastSurah, ayahIndex = lastAyah)
    }

    fun getLastSurah() = preference.getLastSurah()
    fun getLastAyah() = preference.getLastAyah()

    fun getLastRead() = quranDatabase.quranDao().getLastReadWithSurah()


}