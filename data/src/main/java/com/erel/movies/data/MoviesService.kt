package com.erel.movies.data

import com.erel.movies.domain.model.MovieData
import com.erel.movies.domain.model.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesService {
    @GET("movie/now_playing")
    suspend fun getPlayings(@Query("page") page: Int): Response<List<MovieData>>

    @GET("search/movie")
    suspend fun getBySearch(
        @Query("query") query: String,
        @Query("page") page: Int
    ): Response<List<MovieData>>
}