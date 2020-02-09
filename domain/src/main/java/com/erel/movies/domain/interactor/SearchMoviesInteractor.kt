package com.erel.movies.domain.interactor

import com.erel.movies.domain.model.MovieData
import com.erel.movies.domain.repository.MoviesRepository

class SearchMoviesInteractor(executor: Executor, private val repository: MoviesRepository) :
    BaseInteractor<SearchMoviesInteractor.Params, List<MovieData>>(executor) {

    override suspend fun call(params: Params) =
        repository.getBySearch(params.query, params.page)

    data class Params(val query: String, val page: Int)
}

