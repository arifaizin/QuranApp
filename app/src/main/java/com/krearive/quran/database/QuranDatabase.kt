package com.krearive.quran.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.krearive.quran.network.AyahResponseItem
import com.krearive.quran.network.SurahResponseItem

@Database(
    entities = [SurahResponseItem::class, AyahResponseItem::class, LastRead::class, RemoteKeys::class],
    version = 2,
    exportSchema = false
)
abstract class QuranDatabase : RoomDatabase() {

    abstract fun quranDao(): QuranDao
    abstract fun remoteKeysDao(): RemoteKeysDao

    companion object {
        @Volatile
        private var INSTANCE: QuranDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): QuranDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    QuranDatabase::class.java, "quote_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}