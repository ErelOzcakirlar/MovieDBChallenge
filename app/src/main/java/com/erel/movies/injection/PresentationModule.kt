package com.erel.movies.injection

import com.erel.movies.movielist.MovieListAdapter
import com.erel.movies.movielist.MovieListViewModel
import com.erel.movies.movielist.MovieMapper
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

object PresentationModule {
    fun create() = module {
        viewModel { MovieListViewModel(get(), get(), get()) }
        factory { MovieListAdapter() }
        factory { MovieMapper() }
    }
}