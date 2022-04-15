package com.krearive.quran.data

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.krearive.quran.database.QuranDatabase
import com.krearive.quran.network.ApiService
import com.krearive.quran.network.SurahResponseItem

class QuranRepository(private val quranDatabase: QuranDatabase, private val apiService: ApiService) {
    fun getSurah(): LiveData<PagingData<SurahResponseItem>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = SurahRemoteMediator(quranDatabase, apiService),
            pagingSourceFactory = {
//                QuranPagingSource(apiService)
                quranDatabase.quranDao().getAllSurah()
            }
        ).liveData
    }
}