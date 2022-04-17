package com.krearive.quran.preference

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("session")
class QuranPreference (appContext: Context) {
    private val dataStore = appContext.dataStore

    fun getLastAyah(): Flow<Int> {
        return dataStore.data.map {
            it[LAST_AYAH] ?: 0
        }
    }

    fun getLastSurah(): Flow<Int> {
        return dataStore.data.map {
            it[LAST_SURAH] ?: 0
        }
    }

    suspend fun saveLastAyah(surahIndex: Int, ayahIndex: Int) {
        dataStore.edit { preferences ->
            preferences[LAST_SURAH] = surahIndex
            preferences[LAST_AYAH] = ayahIndex
        }
    }

    companion object {
        private val LAST_SURAH = intPreferencesKey("LAST_SURAH")
        private val LAST_AYAH = intPreferencesKey("LAST_AYAH")
    }
}