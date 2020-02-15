package com.erel.movies.domain.injection

import com.erel.movies.domain.interactor.*
import com.erel.movies.domain.model.MovieData
import org.koin.core.qualifier.named
import org.koin.dsl.module

object DomainModule {
    fun create() = module {
        factory<BaseInteractor<GetPlayingsInteractor.Params, List<MovieData>>>(
            named(QUALIFIER_GET_PLAYINGS)
        ) {
            GetPlayingsInteractor(get())
        }
        factory<BaseInteractor<SearchMoviesInteractor.Params, List<MovieData>>>(
            named(QUALIFIER_SEARCH_MOVIES)
        ) {
            SearchMoviesInteractor(get())
        }
    }

    const val QUALIFIER_GET_PLAYINGS = "GetPlayings"
    const val QUALIFIER_SEARCH_MOVIES = "SearchMovies"
}