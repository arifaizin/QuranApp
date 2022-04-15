package com.krearive.quran.network

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
   @GET("/99c279bb173a6e28359c/data")
   suspend fun getSurahList(
      @Query("page") start: Int = 0,
      @Query("size") count: Int = 0
   ): List<SurahResponseItem>
}