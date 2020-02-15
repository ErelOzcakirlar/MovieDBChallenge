package com.erel.movies.injection

import com.erel.movies.domain.injection.DomainModule.QUALIFIER_GET_PLAYINGS
import com.erel.movies.domain.injection.DomainModule.QUALIFIER_SEARCH_MOVIES
import com.erel.movies.movielist.MovieListAdapter
import com.erel.movies.movielist.MovieListViewModel
import com.erel.movies.movielist.MovieMapper
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

object PresentationModule {
    fun create() = module {
        viewModel {
            MovieListViewModel(
                get(named(QUALIFIER_GET_PLAYINGS)),
                get(named(QUALIFIER_SEARCH_MOVIES)),
                get()
            )
        }
        factory { MovieListAdapter() }
        factory { MovieMapper() }
    }
}