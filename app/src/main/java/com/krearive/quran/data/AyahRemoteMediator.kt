package com.krearive.quran.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.krearive.quran.database.QuranDatabase
import com.krearive.quran.database.RemoteKeys
import com.krearive.quran.network.ApiService
import com.krearive.quran.network.AyahResponseItem

@OptIn(ExperimentalPagingApi::class)
class AyahRemoteMediator(
    private val database: QuranDatabase,
    private val apiService: ApiService,
    private val index: Int
) : RemoteMediator<Int, AyahResponseItem>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, AyahResponseItem>
    ): MediatorResult {

        try {
            val responseData = apiService.getAyahList(index)

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.remoteKeysDao().deleteRemoteKeys()
                    database.quranDao().deleteAllAyah()
                }
                val databaseData = responseData.map {
                    it.copy(surahIndex = index)
                }
                database.quranDao().insertAyah(databaseData)
            }
            return MediatorResult.Success(endOfPaginationReached = true)
        } catch (exception: Exception) {
            return MediatorResult.Error(exception)
        }
    }
}