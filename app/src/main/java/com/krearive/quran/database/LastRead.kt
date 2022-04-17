package com.krearive.quran.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.google.gson.annotations.SerializedName
import com.krearive.quran.network.SurahResponseItem

@Entity(tableName = "lastRead")
data class LastRead(
    @PrimaryKey
    val id: Int,
    val surah: Int? = null,
    val ayahPosition: Int? = null,
)

data class LastReadWithSurah(
    @Embedded
    val surah: SurahResponseItem,

    @Relation(
        parentColumn = "index",
        entityColumn = "id"
    )
    val lastRead: LastRead,
)

