package com.example.gifs.data.source.remote

import com.example.gifs.data.DataResponse
import com.example.gifs.data.Gif
import retrofit2.http.GET
import retrofit2.http.Query

interface GifsService {
    @GET("gifs/search")
    suspend fun getSearchResult(@Query("q") query: String, @Query("limit") limit: Int?, @Query("offset") offset: Int): DataResponse<List<Gif>>
}
