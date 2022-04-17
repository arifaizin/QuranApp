package com.krearive.quran.database

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.*
import com.krearive.quran.network.AyahResponseItem
import com.krearive.quran.network.SurahResponseItem

@Dao
interface QuranDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSurah(surah: List<SurahResponseItem>)

    @Query("SELECT * FROM surah")
    fun getAllSurah(): PagingSource<Int, SurahResponseItem>

    @Query("DELETE FROM surah")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAyah(ayah: List<AyahResponseItem>)

    @Query("SELECT * FROM ayah WHERE `surahIndex` = :index")
    fun getAllAyah(index: Int): PagingSource<Int, AyahResponseItem>

    @Query("DELETE FROM ayah")
    suspend fun deleteAllAyah()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveLastRead(lastRead: LastRead)

    @Transaction
    @Query("SELECT * from surah")
    fun getLastReadWithSurah(): LiveData<LastReadWithSurah>
}