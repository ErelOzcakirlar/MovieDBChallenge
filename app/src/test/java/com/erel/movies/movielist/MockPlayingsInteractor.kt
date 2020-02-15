package com.erel.movies.movielist

import com.erel.movies.domain.interactor.BaseInteractor
import com.erel.movies.domain.interactor.GetPlayingsInteractor
import com.erel.movies.domain.model.MovieData
import com.erel.movies.domain.model.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.coroutines.CoroutineContext

class MockPlayingsInteractor(asyncContext: CoroutineContext) :
    BaseInteractor<GetPlayingsInteractor.Params, List<MovieData>>(asyncContext) {
    override fun call(params: GetPlayingsInteractor.Params): Flow<Response<List<MovieData>>> =
        flow {
            emit(
                if (params.page == 1) {
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
            )
        }
}