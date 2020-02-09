package com.erel.movies.domain.interactor

import com.erel.movies.domain.model.MovieData
import com.erel.movies.domain.repository.MoviesRepository

class GetPlayingsInteractor(executor: Executor, private val repository: MoviesRepository) :
    BaseInteractor<GetPlayingsInteractor.Params, List<MovieData>>(executor) {

    override suspend fun call(params: Params) = repository.getPlayings(params.page)

    data class Params(val page: Int)
}

