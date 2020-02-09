package com.erel.movies.list

import com.erel.movies.base.BaseMapper
import com.erel.movies.domain.model.MovieData
import com.erel.movies.model.Movie

class MovieMapper : BaseMapper<MovieData, Movie>() {

    override fun map(data: MovieData): Movie = Movie(
        "$BASE_IMAGE_URL${data.poster.orEmpty()}",
        data.overview.orEmpty(),
        data.releaseDate.orEmpty(),
        data.id ?: INVALID_ID,
        data.originalTitle.orEmpty(),
        data.title.orEmpty(),
        (data.voteAverage ?: 0f).toString()
    )

    companion object {
        const val INVALID_ID = -1
        const val BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w92"
    }
}