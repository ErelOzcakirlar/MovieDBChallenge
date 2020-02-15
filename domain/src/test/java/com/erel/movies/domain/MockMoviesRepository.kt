package com.erel.movies.domain

import com.erel.movies.domain.model.MovieData
import com.erel.movies.domain.model.Response
import com.erel.movies.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MockMoviesRepository : MoviesRepository {
    override fun getPlayings(page: Int): Flow<Response<List<MovieData>>> = flow {
        emit(
            if (page == 1) {
                Response(
                    listOf(
                        MovieData(
                            poster = null,
                            overview = null,
                            releaseDate = null,
                            id = 1,
                            originalTitle = null,
                            title = "title1",
                            voteAverage = null
                        )
                    ),
                    page = 1,
                    totalPages = 2
                )
            } else if (page == 2) {
                Response(
                    listOf(
                        MovieData(
                            poster = null,
                            overview = null,
                            releaseDate = null,
                            id = 2,
                            originalTitle = null,
                            title = "title2",
                            voteAverage = null
                        )
                    ),
                    page = 2,
                    totalPages = 2
                )
            } else {
                throw Throwable(ERROR_MESSAGE)
            }
        )
    }

    override fun getBySearch(query: String, page: Int): Flow<Response<List<MovieData>>> = flow {
        emit(
            if (query.contains("1")) {
                Response(
                    listOf(
                        MovieData(
                            poster = null,
                            overview = null,
                            releaseDate = null,
                            id = 1,
                            originalTitle = null,
                            title = "title1",
                            voteAverage = null
                        )
                    ),
                    page = 1,
                    totalPages = 2
                )
            } else if (query.contains("2")) {
                Response(
                    listOf(
                        MovieData(
                            poster = null,
                            overview = null,
                            releaseDate = null,
                            id = 2,
                            originalTitle = null,
                            title = "title2",
                            voteAverage = null
                        )
                    ),
                    page = 2,
                    totalPages = 2
                )
            } else {
                throw Throwable(ERROR_MESSAGE)
            }
        )
    }

    companion object {
        const val ERROR_MESSAGE = "Page not found"
    }
}