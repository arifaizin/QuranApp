package com.krearive.quran.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
   @GET("/99c279bb173a6e28359c/data")
   suspend fun getSurahList(
      @Query("page") page: Int = 0,
      @Query("size") size: Int = 0
   ): List<SurahResponseItem>

   @GET("/99c279bb173a6e28359c/surat/{index}")
   suspend fun getAyahList(
      @Path("index") index: Int,
      @Query("page") page: Int = 0,
      @Query("size") size: Int = 0
   ): List<AyahResponseItem>
}