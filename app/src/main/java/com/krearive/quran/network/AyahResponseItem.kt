package com.krearive.quran.network

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "ayah")
data class AyahResponseItem(

    @PrimaryKey
    @field:SerializedName("nomor")
    val index: Int,

    @field:SerializedName("ar")
    val arabText: String? = null,

    @field:SerializedName("id")
    val translation: String? = null,

    val surahIndex: Int,
)
