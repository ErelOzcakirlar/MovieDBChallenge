package com.erel.movies.domain.interactor

import com.erel.movies.domain.model.MovieData
import com.erel.movies.domain.repository.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class GetPlayingsInteractor(
    private val repository: MoviesRepository,
    asyncContext: CoroutineContext = Dispatchers.IO
) :
    BaseInteractor<GetPlayingsInteractor.Params, List<MovieData>>(asyncContext) {

    override fun call(params: Params) = repository.getPlayings(params.page)

    data class Params(val page: Int)
}

