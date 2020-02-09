package com.erel.movies.domain.repository

import com.erel.movies.domain.model.MovieData
import com.erel.movies.domain.model.Response


interface MoviesRepository {
    suspend fun getPlayings(page: Int): Response<List<MovieData>>
    suspend fun getBySearch(query: String, page: Int): Response<List<MovieData>>
}
