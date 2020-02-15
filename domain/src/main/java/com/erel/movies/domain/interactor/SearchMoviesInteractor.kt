package com.erel.movies.domain.interactor

import com.erel.movies.domain.model.MovieData
import com.erel.movies.domain.repository.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class SearchMoviesInteractor(
    private val repository: MoviesRepository,
    asyncContext: CoroutineContext = Dispatchers.IO
) :
    BaseInteractor<SearchMoviesInteractor.Params, List<MovieData>>(asyncContext) {

    override fun call(params: Params) =
        repository.getBySearch(params.query, params.page)

    data class Params(val query: String, val page: Int)
}

