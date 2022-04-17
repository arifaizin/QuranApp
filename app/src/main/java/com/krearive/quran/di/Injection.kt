package com.krearive.quran.di

import android.content.Context
import com.krearive.quran.data.QuranRepository
import com.krearive.quran.database.QuranDatabase
import com.krearive.quran.network.ApiConfig
import com.krearive.quran.preference.QuranPreference

object Injection {
    fun provideRepository(context: Context): QuranRepository {
        val database = QuranDatabase.getDatabase(context)
        val apiService = ApiConfig.getApiService()
        val preference = QuranPreference(context)
        return QuranRepository(database, apiService, preference)
    }
}