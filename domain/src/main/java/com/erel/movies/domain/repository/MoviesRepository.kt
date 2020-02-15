package com.erel.movies.domain.repository

import com.erel.movies.domain.model.MovieData
import com.erel.movies.domain.model.Response
import kotlinx.coroutines.flow.Flow


interface MoviesRepository {
    fun getPlayings(page: Int): Flow<Response<List<MovieData>>>
    fun getBySearch(query: String, page: Int): Flow<Response<List<MovieData>>>
}
