package com.erel.movies.data

import com.erel.movies.domain.model.MovieData
import com.erel.movies.domain.model.Response

class MockMoviesService : MoviesService {
    override suspend fun getPlayings(page: Int): Response<List<MovieData>> {
        return if (page == 1) {
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
        } else {
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
        }
    }

    override suspend fun getBySearch(query: String, page: Int): Response<List<MovieData>> {
        return if (query.contains("1")) {
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
        } else {
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
        }
    }

}