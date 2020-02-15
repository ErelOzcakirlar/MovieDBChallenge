package com.erel.movies.data

import com.erel.movies.domain.model.MovieData
import com.erel.movies.domain.model.Response
import com.erel.movies.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MoviesRepositoryImpl(private val service: MoviesService): MoviesRepository {
    override fun getPlayings(page: Int): Flow<Response<List<MovieData>>> = flow {
        emit(service.getPlayings(page))
    }
    override fun getBySearch(query: String, page: Int): Flow<Response<List<MovieData>>> = flow {
        emit(service.getBySearch(query, page))
    }
}