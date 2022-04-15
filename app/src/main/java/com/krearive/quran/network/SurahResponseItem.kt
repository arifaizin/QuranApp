package com.krearive.quran.network

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "surah")
data class SurahResponseItem(

    @PrimaryKey
    @field:SerializedName("nomor")
    val nomor: String,

    @field:SerializedName("arti")
    val arti: String? = null,

    @field:SerializedName("asma")
    val asma: String? = null,

    @field:SerializedName("ayat")
    val ayat: String? = null,

    @field:SerializedName("nama")
    val nama: String? = null,

    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("audio")
    val audio: String? = null,

    @field:SerializedName("keterangan")
    val keterangan: String? = null,
)
