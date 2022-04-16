package com.krearive.quran.network

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "surah")
@Parcelize
data class SurahResponseItem(

    @PrimaryKey
    @field:SerializedName("nomor")
    val index: Int,

    @field:SerializedName("nama")
    val latinName: String? = null,

    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("ayat")
    val totalAyah: String? = null,

    @field:SerializedName("asma")
    val arabName: String? = null,

    @field:SerializedName("arti")
    val meaning: String? = null,
) : Parcelable
