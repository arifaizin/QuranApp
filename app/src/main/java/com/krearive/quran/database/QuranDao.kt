package com.krearive.quran.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.krearive.quran.network.SurahResponseItem

@Dao
interface QuranDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSurah(surah: List<SurahResponseItem>)

    @Query("SELECT * FROM surah")
    fun getAllSurah(): PagingSource<Int, SurahResponseItem>

    @Query("DELETE FROM surah")
    suspend fun deleteAll()
}