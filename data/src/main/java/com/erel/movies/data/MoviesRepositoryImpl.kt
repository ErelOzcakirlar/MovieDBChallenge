package com.erel.movies.data

import com.erel.movies.domain.repository.MoviesRepository

class MoviesRepositoryImpl(private val service: MoviesService): MoviesRepository {
    override suspend fun getPlayings(page: Int) = service.getPlayings(page)
    override suspend fun getBySearch(query: String, page: Int) = service.getBySearch(query, page)
}