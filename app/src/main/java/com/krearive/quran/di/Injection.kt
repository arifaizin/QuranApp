package com.krearive.quran.di

import android.content.Context
import com.krearive.quran.data.QuranRepository
import com.krearive.quran.database.QuranDatabase
import com.krearive.quran.network.ApiConfig

object Injection {
    fun provideRepository(context: Context): QuranRepository {
        val database = QuranDatabase.getDatabase(context)
        val apiService = ApiConfig.getApiService()
        return QuranRepository(database, apiService)
    }
}